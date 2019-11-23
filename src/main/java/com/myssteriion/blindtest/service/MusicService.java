package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.blindtest.spotify.dto.SpotifyMusic;
import com.myssteriion.blindtest.spotify.exception.SpotifyException;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for MusicDTO.
 */
@Service
public class MusicService extends AbstractCRUDService<MusicDTO, MusicDAO> {

	/**
	 * The constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MusicService.class);

	/**
	 * The music folder path.
	 */
	private static final String MUSIC_FOLDER_PATH = Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER).toFile().getAbsolutePath();

	/**
	 * The spotify service.
	 */
	private SpotifyService spotifyService;



	/**
	 * Instantiates a new Music service.
	 *
	 * @param musicDao         the music dao
	 * @param configProperties the config properties
	 * @param spotifyService   the spotify service
	 */
	@Autowired
	public MusicService(MusicDAO musicDao, ConfigProperties configProperties, SpotifyService spotifyService) {
		super(musicDao, configProperties);
		this.spotifyService = spotifyService;
	}



	/**
	 * Scan offline and online musics and insert musics in DB.
	 */
	@PostConstruct
	private void init() {
		
		for ( Theme theme : Theme.values() ) {
			offlineInit(theme);
			onlineInit(theme);
		}

		for ( MusicDTO music : dao.findAll() ) {

			if ( (music.isOnlineMode() && !onlineMusicExists(music))
				|| (!music.isOnlineMode() && !offlineMusicExists(music)) ) {

				dao.deleteById( music.getId() );
			}
		}
	}

	/**
	 * Scan offline musics and insert musics in DB.
	 *
	 * @param theme the theme
	 */
	private void offlineInit(Theme theme) {

		String themeFolder = theme.getFolderName();
		Path path = Paths.get(MUSIC_FOLDER_PATH, themeFolder);

		File themeDirectory = path.toFile();
		for ( File music : Tool.getChildren(themeDirectory) ) {

			MusicDTO musicDto = new MusicDTO(music.getName(), theme);
			Optional<MusicDTO> optionalMusic = dao.findByNameAndThemeAndOnlineMode(musicDto.getName(), musicDto.getTheme(), false);
			if ( music.isFile() && Tool.hadAudioExtension(music.getName()) && !optionalMusic.isPresent() )
				dao.save(musicDto);
		}
	}

	/**
	 * Scan online musics and insert musics in DB.
	 *
	 * @param theme the theme
	 */
	private void onlineInit(Theme theme) {

		if ( spotifyService.isConnected() ) {

			try {
				List<SpotifyMusic> spotifyMusics = spotifyService.getMusicsByTheme(theme);
				for (SpotifyMusic spotifyMusic : spotifyMusics) {

					MusicDTO musicDto = new MusicDTO(spotifyMusic, theme);
					Optional<MusicDTO> optionalMusic = dao.findByNameAndThemeAndOnlineMode(musicDto.getName(), musicDto.getTheme(), true);
					if (!optionalMusic.isPresent())
						dao.save(musicDto);
				}
			}
			catch (SpotifyException e) {
				LOGGER.warn("Can't load online musics.", e);
			}
		}
	}


	/**
	 * Test if the music match with an existing file.
	 *
	 * @param music the music
	 * @return TRUE if the music match with an existing file, FALSE otherwise
	 */
	private boolean offlineMusicExists(MusicDTO music) {
		return Paths.get(MUSIC_FOLDER_PATH, music.getTheme().getFolderName(), music.getName()).toFile().exists();
	}

	/**
	 * Test if the music match with an online track.
	 *
	 * @param music the music
	 * @return TRUE if the music match with an existing file OR the connection is KO, OR the test is KO, FALSE otherwise
	 */
	private boolean onlineMusicExists(MusicDTO music) {

		boolean exists;

		if ( spotifyService.isConnected() ) {

			try {
				exists = spotifyService.trackExists( music.getSpotifyTrackId() );
			}
			catch (SpotifyException e) {
				exists = true;
				LOGGER.warn("Can't test if the track exists.", e);
			}
		}
		else
			exists = true;


		return exists;
	}


	/**
	 * Scan music folder and refresh the DB.
	 */
	public void refresh() {
		init();
	}



	@Override
	public MusicDTO find(MusicDTO dto) {

		Tool.verifyValue("dto", dto);

		if ( Tool.isNullOrEmpty(dto.getId()) )
			return dao.findByNameAndThemeAndOnlineMode(dto.getName(), dto.getTheme(), dto.isOnlineMode()).orElse(null);
		else
			return super.find(dto);
	}



	/**
	 * Randomly choose a music.
	 *
	 * @param themes     the themes filter (optional)
	 * @param onlineMode the online mode
	 * @return the music dto
	 * @throws NotFoundException the not found exception
	 * @throws IOException       the io exception
	 */
	public MusicDTO random(List<Theme> themes, boolean onlineMode) throws NotFoundException, IOException {
	
		List<Theme> searchThemes = (Tool.isNullOrEmpty(themes)) ? Theme.getSortedTheme() : themes;

		List<MusicDTO> allMusics = new ArrayList<>();
		dao.findByThemeInAndOnlineMode(searchThemes, onlineMode).forEach(allMusics::add);

		if ( Tool.isNullOrEmpty(allMusics) )
			throw new NotFoundException("No music found for themes (" + searchThemes.toString() + ").");


		List<Double> coefs = calculateCoefList(allMusics);
		double ratio = 100 / (coefs.stream().mapToDouble(Double::doubleValue).sum());
		List<Double> cumulativePercent = calculateCumulativePercent(coefs, ratio);
		Theme foundTheme = foundTheme(cumulativePercent);
		MusicDTO music = foundMusic(allMusics, foundTheme);

		if ( !music.isOnlineMode() ) {

			Path path = Paths.get(MUSIC_FOLDER_PATH, music.getTheme().getFolderName(), music.getName());
			music.setFlux( new Flux(path.toFile()) );
			music.setEffect( findNextEffect() );
		}

		return music;
	}

	private List<Double> calculateCoefList(List<MusicDTO> allMusics) {
		
		List<Double> coefs = new ArrayList<>();
		
		for ( Theme theme : Theme.getSortedTheme() ) {
			
			List<MusicDTO> allMusicsInTheme = allMusics.stream()
													.filter(music -> music.getTheme() == theme)
													.collect( Collectors.toList() );
			
			double nbMusics = allMusicsInTheme.size();
			double nbPlayedSum = allMusicsInTheme.stream().mapToDouble(MusicDTO::getPlayed).sum();
			nbPlayedSum = (nbPlayedSum == 0) ? 1 : nbPlayedSum;
			coefs.add(nbMusics / nbPlayedSum);
		}
		
		return coefs;
	}
	
	private List<Double> calculateCumulativePercent(List<Double> coefs, double ratio) {
		
		List<Double> cumulativePercent = new ArrayList<>();
		
		cumulativePercent.add( coefs.get(0) * ratio );
		for (int i = 1; i < coefs.size(); i++)
			cumulativePercent.add( cumulativePercent.get(i-1) + (coefs.get(i) * ratio) );
		
		return cumulativePercent;
	}
	
	private Theme foundTheme(List<Double> cumulativePercent) {
		
		Theme foundTheme = null;
		
		double random = Tool.RANDOM.nextDouble() * 100;
		int index = 0;
		
		while (foundTheme == null) {
			
			double cumul = cumulativePercent.get(index);
			if (random < cumul)
				foundTheme = Theme.getSortedTheme().get(index);
			else
				index++;
		}
		
		return foundTheme;
	}
	
	private MusicDTO foundMusic(List<MusicDTO> allMusics, Theme theme) {
	
		List<MusicDTO> allMusicsTheme = allMusics.stream()
												.filter(music -> music.getTheme() == theme)
												.collect( Collectors.toList() );
		
		int min = allMusicsTheme.stream()
								.mapToInt(MusicDTO::getPlayed)
								.min()
								.getAsInt();
		
		List<MusicDTO> potentialMusics = allMusicsTheme.stream()
												.filter(music -> music.getPlayed() == min)
												.collect( Collectors.toList() );
		
		int random = Tool.RANDOM.nextInt( potentialMusics.size() );
		return potentialMusics.get(random);
	}

	/**
	 * Randomly choose an effect.
	 *
	 * @return an effect
	 */
	private Effect findNextEffect() {

		int r = Tool.RANDOM.nextInt(100);

		if (r >= 40 && r < 60) return Effect.SLOW;
		if (r >= 60 && r < 80) return Effect.SPEED;
		if (r >= 80 && r < 100) return Effect.REVERSE;

		return Effect.NONE;
	}

}
