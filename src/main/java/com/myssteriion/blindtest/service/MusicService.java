package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for MusicDTO.
 */
@Service
public class MusicService extends AbstractCRUDService<MusicDTO, MusicDAO> {

	/**
	 * The music folder path.
	 */
	private static final String MUSIC_FOLDER_PATH = Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER).toFile().getAbsolutePath();



	@Autowired
	public MusicService(MusicDAO musicDao, ConfigProperties configProperties) {
		super(musicDao, configProperties);
	}



	/**
	 * Scan musics folder and insert musics in DB.
	 */
	@PostConstruct
	private void init() {
		
		for ( Theme theme : Theme.values() ) {
			
			String themeFolder = theme.getFolderName();
			Path path = Paths.get(MUSIC_FOLDER_PATH, themeFolder);

			File themeDirectory = path.toFile();
			for ( File music : Tool.getChildren(themeDirectory) ) {

				MusicDTO musicDto = new MusicDTO(music.getName(), theme);
				if ( music.isFile() && Tool.hadAudioExtension(music.getName()) && !dao.findByNameAndTheme(musicDto.getName(), musicDto.getTheme()).isPresent() )
					dao.save(musicDto);
			}
		}

		for ( MusicDTO music : dao.findAll() ) {
			if ( !musicFileExists(music) )
				dao.deleteById( music.getId() );
		}
	}

	/**
	 * Test if the music matching with an existing file.
	 *
	 * @param music the dto
	 * @return TRUE if the music matching with an existing file, FALSE otherwise
	 */
	private boolean musicFileExists(MusicDTO music) {
		return music != null && Paths.get(MUSIC_FOLDER_PATH, music.getTheme().getFolderName(), music.getName()).toFile().exists();
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
			return dao.findByNameAndTheme(dto.getName(), dto.getTheme()).orElse(null);
		else
			return super.find(dto);
	}


	/**
	 * Randomly choose a music.
	 *
	 * @return the music dto
	 * @throws NotFoundException the not found exception
	 */
	public MusicDTO random() throws NotFoundException, IOException {
	
		List<MusicDTO> allMusics = new ArrayList<>();
		dao.findAll().forEach(allMusics::add);

		if ( Tool.isNullOrEmpty(allMusics) )
			throw new NotFoundException("No music found.");


		List<Double> coefs = calculateCoefList(allMusics);
		double ratio = 100 / (coefs.stream().mapToDouble(Double::doubleValue).sum());
		List<Double> cumulativePercent = calculateCumulativePercent(coefs, ratio);
		Theme foundTheme = foundTheme(cumulativePercent);
		MusicDTO music = foundMusic(allMusics, foundTheme);

		Path path = Paths.get(MUSIC_FOLDER_PATH, music.getTheme().getFolderName(), music.getName());
		music.setFlux( new Flux(path.toFile()) );
		music.setEffect( findNextEffect() );

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

		if (r >= 50 && r < 67) return Effect.SLOW;
		if (r >= 68 && r < 84) return Effect.SPEED;
		if (r >= 85 && r < 100) return Effect.REVERSE;

		return Effect.NONE;
	}

}
