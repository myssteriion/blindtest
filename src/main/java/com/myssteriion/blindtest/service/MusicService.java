package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for MusicDTO.
 */
@Service
public class MusicService {

	@Autowired
	private MusicDAO musicDao;



	/**
	 * Scan musics folder and insert musics in DB.
	 *
	 * @throws DaoException		 DB exception
	 * @throws ConflictException Conflict exception
	 */
	@PostConstruct
	private void init() throws DaoException, ConflictException {
		
		for ( Theme theme : Theme.values() ) {
			
			String themeFolder = theme.getFolderName();
			Path path = Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER, themeFolder);

			File themeDirectory = path.toFile();
			for ( File music : Tool.getChildren(themeDirectory) ) {
				if ( music.isFile() )
					save(new MusicDTO(music.getName(), theme), false);
			}
		}
	}

	/**
	 * Scan music folder and refresh the DB.
	 *
	 * @throws DaoException      the dao exception
	 * @throws ConflictException the conflict exception
	 */
	public void refresh() throws DaoException, ConflictException {
		init();
	}


	/**
	 * Save music dto.
	 *
	 * @param musicDto      the music dto
	 * @param throwIfExists the throw if exists
	 * @return the music dto
	 * @throws DaoException      the dao exception
	 * @throws ConflictException the conflict exception
	 */
	public MusicDTO save(MusicDTO musicDto, boolean throwIfExists) throws DaoException, ConflictException {
		
		Tool.verifyValue("musicDto", musicDto);
		
		musicDto.setId(null);
		MusicDTO foundMusicDto = musicDao.find(musicDto);
		
		if (!Tool.isNullOrEmpty(foundMusicDto) && throwIfExists)
			throw new ConflictException("musicDto already exists.");
		
		
		if ( Tool.isNullOrEmpty(foundMusicDto) )
			foundMusicDto = musicDao.save(musicDto);
		
		return foundMusicDto;
	}

	/**
	 * Update music dto.
	 *
	 * @param musicDto the music dto
	 * @return the music dto
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 */
	public MusicDTO update(MusicDTO musicDto) throws DaoException, NotFoundException {

		Tool.verifyValue("musicDto", musicDto);
		Tool.verifyValue("musicDto -> id", musicDto.getId());

		MusicDTO foundMusicDto = musicDao.find(musicDto);
		if ( Tool.isNullOrEmpty(foundMusicDto) )
			throw new NotFoundException("musicDto not found.");

		return musicDao.update(musicDto);
	}

	/**
	 * Find music dto.
	 *
	 * @param musicDto the music dto
	 * @return the music dto
	 * @throws DaoException the dao exception
	 */
	public MusicDTO find(MusicDTO musicDto) throws DaoException {

		Tool.verifyValue("musicDto", musicDto);
		return musicDao.find(musicDto);
	}


	/**
	 * Randomly choose a music.
	 *
	 * @return the music dto
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 */
	public MusicDTO random() throws DaoException, NotFoundException {
	
		List<MusicDTO> allMusics = musicDao.findAll();

		if ( Tool.isNullOrEmpty(allMusics) )
			throw new NotFoundException("No music found.");


		List<Double> coefs = calculateCoefList(allMusics);
		double ratio = 100 / (coefs.stream().mapToDouble(Double::doubleValue).sum());
		List<Double> cumulatifPercent = calculateCumulativePercent(coefs, ratio);
		Theme foundTheme = foundTheme(cumulatifPercent);
		
		return foundMusic(allMusics, foundTheme);
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
	
}
