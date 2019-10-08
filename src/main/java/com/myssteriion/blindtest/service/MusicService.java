package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
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

	private static final int NB_MUSICS_NOT_FOUND_LIMIT = 10;



	@Autowired
	public MusicService(MusicDAO musicDao) {
		super(musicDao);
	}



	/**
	 * Scan musics folder and insert musics in DB.
	 */
	@PostConstruct
	private void init() {
		
		for ( Theme theme : Theme.values() ) {
			
			String themeFolder = theme.getFolderName();
			Path path = Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER, themeFolder);

			File themeDirectory = path.toFile();
			for ( File music : Tool.getChildren(themeDirectory) ) {

				MusicDTO musicDto = new MusicDTO(music.getName(), theme);
				if ( music.isFile() && !dao.findByNameAndTheme(musicDto.getName(), musicDto.getTheme()).isPresent() )
					dao.save(musicDto);
			}
		}

		for ( MusicDTO music : dao.findAll() ) {
			if ( !musicFileExists(music) )
				dao.deleteById( music.getId() );
		}
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

		int cpt = 0;
		MusicDTO music = null;
		while ( !musicFileExists(music) ) {

			Theme foundTheme = foundTheme(cumulativePercent);
			music = foundMusic(allMusics, foundTheme);

			cpt++;
			if (cpt == NB_MUSICS_NOT_FOUND_LIMIT)
				throw new NotFoundException(NB_MUSICS_NOT_FOUND_LIMIT + " musics are not found, please refresh musics folder.");
		}

		Path path = Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER, music.getTheme().getFolderName(), music.getName());
		music.setFlux( new Flux(path.toFile()) );

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

	private boolean musicFileExists(MusicDTO music) {
		return music != null && Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER, music.getTheme().getFolderName(), music.getName()).toFile().exists();
	}

}
