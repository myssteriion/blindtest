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

/**
 * Service for Avatar.
 */
@Service
public class AvatarService {

	/**
	 * The avatar folder path.
	 */
	private static final Path AVATAR_FOLDER = Paths.get(Constant.BASE_DIR, Constant.AVATAR_FOLDER);

	/**
	 * The avatars list (cache).
	 */
	private List<Avatar> avatars;
	
	
	
	@PostConstruct
	private void init() {
		
		avatars = new ArrayList<>();

		File avatarDirectory = AVATAR_FOLDER.toFile();
		for ( File avatar : Tool.getChildren(avatarDirectory) ) {
			if ( avatar.isFile() )
				avatars.add( new Avatar(avatar.getName()) );
		}

		avatars.sort(Avatar.COMPARATOR);
	}

	/**
	 * Refresh avatars list (cache).
	 */
	public void refresh() {
		init();
	}

	/**
	 * Gets all avatars.
	 *
	 * @return the avatars list
	 */
	public List<Avatar> getAll() {

		if (avatars == null)
			avatars = new ArrayList<>();

		return avatars;
	}
	
}
