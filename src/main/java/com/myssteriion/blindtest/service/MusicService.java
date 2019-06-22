package com.myssteriion.blindtest.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.db.exception.SqlException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class MusicService {

	@Autowired
	private MusicDAO dao;
	
	
	
	@PostConstruct
	private void init() throws SqlException {
		
		for ( Theme theme : Theme.values() ) {
			
			String themeFolder = theme.getFolderName();
			Path path = Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER, themeFolder);
			
			File themeDirectory = path.toFile();
			if ( !Tool.isNullOrEmpty(themeDirectory) && themeDirectory.isDirectory() ) {

				for ( File music : themeDirectory.listFiles() ) {
					save( new MusicDTO(music.getName(), theme) );
				}
			}
		}
	}
	
	public void refresh() throws SqlException {
		init();
	}
	
	
	
	public MusicDTO save(MusicDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		MusicDTO foundDto = dao.find(dto);
		if (foundDto == null)
			foundDto = dao.save(dto);
		
		return foundDto;
	}
	
}
