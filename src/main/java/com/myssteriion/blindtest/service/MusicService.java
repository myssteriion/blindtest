package com.myssteriion.blindtest.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private void init() throws SqlException {
		
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
	
	public void refresh() throws SqlException {
		init();
	}
	
	
	
	public MusicDTO save(MusicDTO dto, boolean throwIfExsits) throws SqlException  {
		
		Tool.verifyValue("dto", dto);
		
		MusicDTO savedDTO = dto;
		try {
			savedDTO = dao.save(dto);
		}
		catch (SqlException e) {
			if (throwIfExsits)
				throw e;
		}
		
		return savedDTO;
	}
	
	public MusicDTO update(MusicDTO dto, boolean throwIfNotExsits) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		MusicDTO savedDTO = dto;
		try {
			savedDTO = dao.update(dto);
		}
		catch (SqlException e) {
			if (throwIfNotExsits)
				throw e;
		}
		
		return savedDTO;
	}
	
	public MusicDTO saveOrUpdate(MusicDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		MusicDTO foundDto = dao.find(dto);
		if (foundDto == null)
			foundDto = dao.save(dto);
		else
			foundDto = dao.update(dto);
		
		return foundDto;
	}
	
}
