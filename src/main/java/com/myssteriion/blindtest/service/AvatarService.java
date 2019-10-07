package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	 * The Sort.
	 */
	private static final Sort SORT = Sort.by(Sort.Direction.ASC, "name");

	/**
	 * Number of elements per page.
	 */
	private static int ELEMENTS_PER_PAGE = 15;

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
	 * Gets avatars page.
	 *
	 * @return the avatars list
	 */
	public Page<Avatar> getAll(int page) {

		if ( Tool.isNullOrEmpty(avatars) )
			return Page.empty();

		page = Math.max(page, 0);
		page = Math.min( page, calculateMaxPage()-1 );

		int start = ELEMENTS_PER_PAGE * page;
		int end = start + ELEMENTS_PER_PAGE;

		end = Math.min( end, avatars.size() );

		PageRequest pageRequest = PageRequest.of(page, ELEMENTS_PER_PAGE, SORT);
		return new PageImpl<>(avatars.subList(start, end), pageRequest, avatars.size());
	}

	/**
	 * Calculate the max page.
	 *
	 * @return the max page
	 */
	private int calculateMaxPage() {

		int maxPage = avatars.size() / ELEMENTS_PER_PAGE;

		if ( (avatars.size() % ELEMENTS_PER_PAGE) > 0 )
			maxPage++;

		return maxPage;
	}
	
}
