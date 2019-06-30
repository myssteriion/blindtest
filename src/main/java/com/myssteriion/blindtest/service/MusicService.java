package com.myssteriion.blindtest.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class MusicService {

	@Autowired
	private MusicDAO dao;
	
	
	
	@PostConstruct
	private void init() throws SqlException, AlreadyExistsException {
		
		for ( Theme theme : Theme.values() ) {
			
			String themeFolder = theme.getFolderName();
			Path path = Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER, themeFolder);
			
			File themeDirectory = path.toFile();
			if ( !Tool.isNullOrEmpty(themeDirectory) && themeDirectory.isDirectory() ) {

				for ( File music : themeDirectory.listFiles() ) {
					save(new MusicDTO(music.getName(), theme), false);
				}
			}
		}
	}
	
	public void refresh() throws SqlException, AlreadyExistsException {
		init();
	}
	
	
	
	public MusicDTO save(MusicDTO dto, boolean throwIfExists) throws SqlException, AlreadyExistsException  {
		
		Tool.verifyValue("dto", dto);
		
		dto.setId(null);
		MusicDTO foundDTO = dao.find(dto);
		
		if (!Tool.isNullOrEmpty(foundDTO) && throwIfExists)
			throw new AlreadyExistsException("DTO already exists.");
		
		
		if ( Tool.isNullOrEmpty(foundDTO) )
			foundDTO = dao.save(dto);
		
		return foundDTO;
	}
	
	public MusicDTO musicWasPlayed(MusicDTO dto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("dto", dto);
		
		MusicDTO foundDTO = dao.find(dto);
		
		if ( Tool.isNullOrEmpty(foundDTO) )
			throw new NotFoundException("DTO not found.");
		
		
		foundDTO.incrementPlayed();
		
		return dao.update(foundDTO);
	}
	
	
	
	public MusicDTO next() throws SqlException {
	
		List<MusicDTO> allMusics = dao.findAll();
		
		List<Double> coefs = calculateCoefList(allMusics);
		double ratio = 100 / (coefs.stream().mapToDouble(f -> f.doubleValue()).sum());
		List<Double> cumulatifPercent = calculateCumulatifPercent(coefs, ratio);
		Theme foundedTheme = foundTheme(cumulatifPercent);
		
		return foundMusic(allMusics, foundedTheme);
	}

	private List<Double> calculateCoefList(List<MusicDTO> allMusics) {
		
		List<Double> coefs = new ArrayList<>();
		
		for ( Theme theme : Theme.getSortedTheme() ) {
			
			List<MusicDTO> allMusicsInTheme = allMusics.stream()
													.filter(music -> music.getTheme() == theme)
													.collect( Collectors.toList() );
			
			double nbMusics = allMusicsInTheme.size();
			double nbPlayedSum = allMusicsInTheme.stream().mapToDouble( music -> music.getPlayed() ).sum();
			nbPlayedSum = (nbPlayedSum == 0) ? 1 : nbPlayedSum;
			coefs.add(nbMusics / nbPlayedSum);
		}
		
		return coefs;
	}
	
	private List<Double> calculateCumulatifPercent(List<Double> coefs, double ratio) {
		
		List<Double> cumulatifPercent = new ArrayList<>();
		
		cumulatifPercent.add( coefs.get(0) * ratio );
		for (int i = 1; i < coefs.size(); i++)
			cumulatifPercent.add( cumulatifPercent.get(i-1) + (coefs.get(i) * ratio) );
		
		return cumulatifPercent;
	}
	
	private Theme foundTheme(List<Double> cumulatifPercent) {
		
		Theme foundedTheme = null;
		
		double random = Tool.RANDOM.nextDouble() * 100;
		int index = 0;
		
		while (foundedTheme == null) {
			
			double cumul = cumulatifPercent.get(index);
			if (random < cumul)
				foundedTheme = Theme.getSortedTheme().get(index);
			else
				index++;
		}
		
		return foundedTheme;
	}
	
	private MusicDTO foundMusic(List<MusicDTO> allMusics, Theme theme) {
	
		List<MusicDTO> allMusicsTheme = allMusics.stream()
												.filter(music -> music.getTheme() == theme)
												.collect( Collectors.toList() );
		
		int min = allMusicsTheme.stream()
								.mapToInt(music -> music.getPlayed())
								.min()
								.getAsInt();
		
		List<MusicDTO> potentialMusics = allMusicsTheme.stream()
												.filter(music -> music.getPlayed() == min)
												.collect( Collectors.toList() );
		
		int random = Tool.RANDOM.nextInt( potentialMusics.size() );
		return potentialMusics.get(random);
	}
	
}
