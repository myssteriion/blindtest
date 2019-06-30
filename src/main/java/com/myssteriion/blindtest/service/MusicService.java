package com.myssteriion.blindtest.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	
}
