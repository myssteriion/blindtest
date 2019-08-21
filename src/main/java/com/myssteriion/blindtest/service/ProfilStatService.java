package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.db.dao.ProfilStatDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilStatService {

	@Autowired
	private ProfilStatDAO profilStatDao;
	
	@Autowired
	private ProfilService profilService;
	
	
	
	public ProfilStatDTO save(ProfilStatDTO profilStatDto) throws DaoException, NotFoundException, ConflictException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		checkProfilDto(profilStatDto);

		profilStatDto.setId(null);
		ProfilStatDTO foundProfilDto = profilStatDao.find(profilStatDto);
		
		if ( !Tool.isNullOrEmpty(foundProfilDto) )
			throw new ConflictException("profilStatDto already exists.");
		
		
		foundProfilDto = profilStatDao.save(profilStatDto);
		
		return foundProfilDto;
	}

	public ProfilStatDTO update(ProfilStatDTO profilStatDTO) throws DaoException, NotFoundException {

		Tool.verifyValue("profilStatDTO", profilStatDTO);
		Tool.verifyValue("profilStatDTO -> id", profilStatDTO.getId());

		ProfilStatDTO foundProfilStatDto = profilStatDao.find(profilStatDTO);
		if ( Tool.isNullOrEmpty(foundProfilStatDto) )
			throw new NotFoundException("profilStatDTO not found.");

		return profilStatDao.update(profilStatDTO);
	}

	public ProfilStatDTO find(ProfilStatDTO profilStatDto) throws DaoException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		return profilStatDao.find(profilStatDto);
	}

	public ProfilStatDTO findByProfil(ProfilDTO profilDto) throws DaoException, NotFoundException {

		Tool.verifyValue("profilDto", profilDto);

		ProfilDTO foundProfilDto = profilService.find(profilDto);

		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilDto not found.");

		ProfilStatDTO profilStatDto = new ProfilStatDTO( foundProfilDto.getId() );
		ProfilStatDTO foundProfilStatDTO = profilStatDao.find(profilStatDto);

		if ( Tool.isNullOrEmpty(foundProfilStatDTO) )
			throw new NotFoundException("profilStatDto not found.");

		return foundProfilStatDTO;
	}

	public List<ProfilStatDTO> findAll() throws DaoException {
		return profilStatDao.findAll();
	}

	private void checkProfilDto(ProfilStatDTO profilStatDto) throws DaoException, NotFoundException {
	
		ProfilDTO profilDto = new ProfilDTO("ANY");
		profilDto.setId( profilStatDto.getProfilId() );
		profilDto = profilService.find(profilDto);
		if ( Tool.isNullOrEmpty(profilDto) )
			throw new NotFoundException("profilDto not found.");
	}
	
}
