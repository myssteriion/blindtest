package com.myssteriion.blindtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class ProfilService {

	@Autowired
	private ProfilDAO profilDao;
	
	@Autowired
	private ProfilStatService profilStatService;
	
	
	
	public ProfilDTO save(ProfilDTO profilDto) throws SqlException, NotFoundException, AlreadyExistsException  {
		
		Tool.verifyValue("profilDto", profilDto);
		
		profilDto.setId(null);
		ProfilDTO foundProfilDto = profilDao.find(profilDto);
		
		if ( !Tool.isNullOrEmpty(foundProfilDto) )
			throw new AlreadyExistsException("profilDto already exists.");
		
		
		foundProfilDto = profilDao.save(profilDto);
		
		profilStatService.save( new ProfilStatDTO(foundProfilDto.getId()) );
		
		return foundProfilDto;
	}
	
	public ProfilDTO update(ProfilDTO profilDto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("profilDto", profilDto);
		
		ProfilDTO foundProfilDto = profilDao.find(profilDto);
		
		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilDto not found.");

		foundProfilDto.setName( profilDto.getName() );
		foundProfilDto.setAvatar( profilDto.getAvatar() );
		
		return profilDao.update(foundProfilDto);
	}

	public ProfilDTO find(ProfilDTO profilDto) throws SqlException {
		
		Tool.verifyValue("profilDto", profilDto);
		return profilDao.find(profilDto);
	}
	
	public List<ProfilDTO> findAll() throws SqlException {
		return profilDao.findAll();
	}
	
}
