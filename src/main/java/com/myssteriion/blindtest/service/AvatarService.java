package com.myssteriion.blindtest.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.tools.Constant;

@Service
public class AvatarService {

	private static final Path AVATAR_FOLDER = Paths.get(Constant.BASE_DIR, Constant.AVATAR_FOLDER);
	
	private List<Avatar> avatars;
	
	
	
	@PostConstruct
	private void init() {
		
		avatars = new ArrayList<>();
		for ( File file : AVATAR_FOLDER.toFile().listFiles() )
			avatars.add( new Avatar(file.getName()) );
	}
	
	public void refresh() {
		init();
	}

	public List<Avatar> getAll() {
		return avatars;
	}
	
}
