package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.blindtest.spotify.SpotifyMusic;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.Tools;
import com.myssteriion.utils.rest.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
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
	private static final String MUSIC_FOLDER_PATH = Paths.get(CommonConstant.BASE_DIR, Constant.MUSICS_FOLDER).toFile().getAbsolutePath();

	/**
	 * The spotify service.
	 */
	private SpotifyService spotifyService;



	/**
	 * Instantiates a new Music service.
	 *
	 * @param musicDao         the music dao
	 * @param spotifyService   the spotify service
	 */
	@Autowired
	public MusicService(MusicDAO musicDao, SpotifyService spotifyService) {
		super(musicDao);
		this.spotifyService = spotifyService;
	}



	/**
	 * Scan offline and online musics and insert musics in DB.
	 */
	@PostConstruct
	private void init() {

		boolean onlineMode = spotifyService.isConnected();
		if (!onlineMode)
			LOGGER.warn("Can't load online musics.");

		for ( Theme theme : Theme.values() ) {

			offlineInit(theme);
			if (onlineMode)
				onlineInit(theme);
		}

		for ( MusicDTO music : dao.findAll() ) {

			if ( (onlineMode && music.getConnectionMode() == ConnectionMode.ONLINE && !onlineMusicExists(music))
				|| (music.getConnectionMode() == ConnectionMode.OFFLINE && !offlineMusicExists(music)) ) {

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
		for ( File file : Tools.getChildren(themeDirectory) ) {

			MusicDTO musicDto = new MusicDTO(file.getName(), theme, ConnectionMode.OFFLINE);
			Optional<MusicDTO> optionalMusic = dao.findByNameAndThemeAndConnectionMode( musicDto.getName(), musicDto.getTheme(), musicDto.getConnectionMode() );
			if ( file.isFile() && Tools.hadAudioExtension(file.getName()) && !optionalMusic.isPresent() )
				dao.save(musicDto);
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
	 * Scan online musics and insert musics in DB.
	 *
	 * @param theme the theme
	 */
	private void onlineInit(Theme theme) {

		try {
			List<SpotifyMusic> spotifyMusics = spotifyService.getMusicsByTheme(theme);
			for (SpotifyMusic spotifyMusic : spotifyMusics) {

					MusicDTO musicDto = new MusicDTO( spotifyMusic.getName(), theme, ConnectionMode.ONLINE, spotifyMusic.getTrackId(), spotifyMusic.getPreviewUrl(), spotifyMusic.getTrackUrl() );
					Optional<MusicDTO> optionalMusic = dao.findByNameAndThemeAndConnectionMode(musicDto.getName(), musicDto.getTheme(), ConnectionMode.ONLINE);
					if (!optionalMusic.isPresent())
						dao.save(musicDto);
				}
		}
		catch (SpotifyException e) {
			LOGGER.warn("Can't load online musics.", e);
		}
	}

	/**
	 * Test if the music match with an online track.
	 *
	 * @param music the music
	 * @return TRUE if the music match with an existing file OR the connection is KO, OR the test is KO, FALSE otherwise
	 */
	private boolean onlineMusicExists(MusicDTO music) {

		boolean exists;

		try {
			exists = spotifyService.trackExists( music.getSpotifyTrackId() );
		}
		catch (SpotifyException e) {
			exists = true;
			LOGGER.warn("Can't test if the track exists.", e);
		}

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

		Tools.verifyValue("entity", dto);

		if ( Tools.isNullOrEmpty(dto.getId()) )
			return dao.findByNameAndThemeAndConnectionMode(dto.getName(), dto.getTheme(), dto.getConnectionMode()).orElse(null);
		else
			return super.find(dto);
	}



	/**
	 * Count by theme and connection mode
	 *
	 * @param theme          the theme
	 * @param connectionMode the connection mode
	 * @return the number of music
	 */
	public Integer getMusicNumber(Theme theme, ConnectionMode connectionMode) {

		Tools.verifyValue("theme", theme);
		Tools.verifyValue("connectionMode", connectionMode);

		Integer nbMusic = 0;
		for (ConnectionMode mode : connectionMode.transformForSearchMusic() )
			nbMusic += dao.countByThemeAndConnectionMode(theme, mode);

		return nbMusic;
	}

	/**
	 * Get nb musics by themes by connection mode.
	 *
	 * @return the themes info list
	 */
	public List<ThemeInfo> computeThemesInfo() {

		List<ThemeInfo> themesInfo = new ArrayList<>();

		for ( Theme theme : Theme.getSortedTheme() ) {

			Integer offlineNbMusics = getMusicNumber(theme, ConnectionMode.OFFLINE);
			Integer onlineNbMusics = getMusicNumber(theme, ConnectionMode.ONLINE);

			themesInfo.add( new ThemeInfo(theme, offlineNbMusics, onlineNbMusics) );
		}

		return themesInfo;
	}

	/**
	 * Randomly choose a music.
	 *
	 * @param themes     the themes filter (optional)
	 * @param connectionMode the online mode
	 * @return the music dto
	 * @throws NotFoundException the not found exception
	 * @throws IOException       the io exception
	 */
	public MusicDTO random(List<Theme> themes, List<Effect> effects, ConnectionMode connectionMode) throws NotFoundException, IOException, SpotifyException {

		Tools.verifyValue("connectionMode", connectionMode);

		List<Theme> searchThemes = (Tools.isNullOrEmpty(themes)) ? Theme.getSortedTheme() : themes;
		List<ConnectionMode> connectionModes = connectionMode.transformForSearchMusic();

		List<MusicDTO> allMusics = new ArrayList<>();
		dao.findByThemeInAndConnectionModeIn(searchThemes, connectionModes).forEach(allMusics::add);

		if ( Tools.isNullOrEmpty(allMusics) )
			throw new NotFoundException("No music found for themes (" + searchThemes.toString() + ").");


		List<Double> coefs = calculateCoefList(allMusics);
		double ratio = 100 / (coefs.stream().mapToDouble(Double::doubleValue).sum());
		List<Double> cumulativePercent = calculateCumulativePercent(coefs, ratio);
		Theme foundTheme = foundTheme(cumulativePercent);
		MusicDTO music = foundMusic(allMusics, foundTheme);

		if ( music.getConnectionMode().isNeedConnection() ) {
			spotifyService.testConnection();
		}
		else {

			Path path = Paths.get(MUSIC_FOLDER_PATH, music.getTheme().getFolderName(), music.getName());
			music.setFlux( new Flux(path.toFile()) );

			List<Effect> searchEffects = (Tools.isNullOrEmpty(effects)) ? Effect.getSortedEffect() : effects;
			music.setEffect( foundEffect(searchEffects) );
		}

		return music;
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

		int random = Tools.RANDOM.nextInt( potentialMusics.size() );
		return potentialMusics.get(random);
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
		
		double random = Tools.RANDOM.nextDouble() * 100;
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

	private Effect foundEffect(List<Effect> effects) {
		return effects.get( Tools.RANDOM.nextInt(effects.size()) );
	}

}
