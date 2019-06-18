package com.myssteriion.blindtest.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.music.MusicDTO;
import com.myssteriion.blindtest.model.music.Theme;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class MusicService {

	@Autowired
	private MusicDAO dao;
	
	
	
	@PostConstruct
	private void init() throws EntityManagerException {
		
		for ( Theme theme : Theme.values() ) {
			
			String themeFolder = theme.getFolderName();
			Path path = Paths.get(Constant.BASE_DIR, Constant.MUSICS_FOLDER, themeFolder);
			
			File themeDirectory = path.toFile();
			if ( !Tool.isNullOrEmpty(themeDirectory) && themeDirectory.isDirectory() ) {

				for ( File music : themeDirectory.listFiles() ) {
					saveOrUpdate(music.getName(), theme);
				}
			}
		}
	}
	
	
	
	public void refresh() throws EntityManagerException {
		init();
	}
	
	public MusicDTO saveOrUpdate(String name, Theme theme) throws EntityManagerException {
		
		Tool.verifyValue("name", name);
		Tool.verifyValue("theme", theme);
		
		MusicDTO dto = new MusicDTO(name, theme);
		return dao.saveOrUpdate(dto);
	}
	
}
