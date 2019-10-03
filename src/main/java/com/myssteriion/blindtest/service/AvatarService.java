package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvatarService {

	private static final Path AVATAR_FOLDER = Paths.get(Constant.BASE_DIR, Constant.AVATAR_FOLDER);
	
	private List<Avatar> avatars;
	
	
	
	@PostConstruct
	private void init() {
		
		avatars = new ArrayList<>();

		File avatarDirectory = AVATAR_FOLDER.toFile();
		for ( File avatar : Tool.getChildren(avatarDirectory) )
			if ( avatar.isFile() )
				avatars.add( new Avatar(avatar.getName()) );
	}
	
	public void refresh() {
		init();
	}

	public List<Avatar> getAll() {

		if (avatars == null)
			avatars = new ArrayList<>();

		return avatars;
	}
	
}
