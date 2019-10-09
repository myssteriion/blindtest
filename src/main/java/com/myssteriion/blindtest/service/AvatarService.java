package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.AvatarDAO;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.dto.AvatarDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service for Avatar.
 */
@Service
public class AvatarService extends AbstractCRUDService<AvatarDTO, AvatarDAO> {

	/**
	 * The avatar folder path.
	 */
	public static final String AVATAR_FOLDER_PATH = Paths.get(Constant.BASE_DIR, Constant.AVATAR_FOLDER).toFile().getAbsolutePath();

	/**
	 * Number of elements per page.
	 */
	protected static final int ELEMENTS_PER_PAGE = 6;



	/**
	 * Instantiates a new Abstract service.
	 *
	 * @param avatarDAO the dao
	 */
	@Autowired
	public AvatarService(AvatarDAO avatarDAO) {
		super(avatarDAO);
	}



	@PostConstruct
	private void init() {

		Path path = Paths.get(AVATAR_FOLDER_PATH);

		File avatarDirectory = path.toFile();
		for ( File avatar : Tool.getChildren(avatarDirectory) ) {

			AvatarDTO avatarDTO = new AvatarDTO( avatar.getName() );
			if ( avatar.isFile() && !dao.findByName(avatar.getName()).isPresent() )
				dao.save(avatarDTO);
		}

		for ( AvatarDTO avatar : dao.findAll() ) {
			if ( !avatarFileExists(avatar) )
				dao.deleteById( avatar.getId() );
		}
	}

	/**
	 * Refresh avatarDTOS list (cache).
	 */
	public void refresh() {
		init();
	}


	@Override
	public AvatarDTO save(AvatarDTO dto) throws ConflictException {

		AvatarDTO avatar = super.save(dto);
		createAvatarFlux(avatar);
		return avatar;
	}

	@Override
	public AvatarDTO update(AvatarDTO dto) throws NotFoundException {

		AvatarDTO avatar = super.update(dto);
		createAvatarFlux(avatar);
		return avatar;
	}

	@Override
	public AvatarDTO find(AvatarDTO dto) {

		Tool.verifyValue("dto", dto);

		AvatarDTO avatar;
		if ( Tool.isNullOrEmpty(dto.getId()) )
			avatar = dao.findByName(dto.getName()).orElse(null);
		else
			avatar = super.find(dto);

		if (avatar != null)
			createAvatarFlux(avatar);

		return avatar;
	}

	/**
	 * Find a page of Avatar filtered by a prefix name.
	 *
	 * @param namePrefix the name prefix
	 * @param pageNumber the page number
	 * @return the page of avatars
	 */
	public Page<AvatarDTO> findAllByNameStartingWith(String namePrefix, int pageNumber) {

		if (namePrefix == null)
			namePrefix = "";

		Sort sort = Sort.by(Sort.Direction.ASC, "name");
		Pageable pageable = PageRequest.of(pageNumber, ELEMENTS_PER_PAGE, sort);

		Page<AvatarDTO> page = dao.findAllByNameStartingWithIgnoreCase(namePrefix, pageable);
		page.forEach(this::createAvatarFlux);

		return page;
	}


	/**
	 * Test if the avatar match with an existing file.
	 *
	 * @param avatar the avatar
	 * @return TRUE if the avatar match with an existing file, FALSE otherwise
	 */
	private boolean avatarFileExists(AvatarDTO avatar) {
		return avatar != null && Paths.get(AVATAR_FOLDER_PATH, avatar.getName()).toFile().exists();
	}

	/**
	 * Create avatar flux on avatar.
	 *
	 * @param dto the dto
	 */
	public void createAvatarFlux(AvatarDTO dto) {

		try {

			Path path = Paths.get( AVATAR_FOLDER_PATH, dto.getName() );
			dto.setFlux( new Flux(path.toFile()) );
		}
		catch (IOException e) {
			throw new CustomRuntimeException("Can't create avatar flux.", e);
		}
	}

}
