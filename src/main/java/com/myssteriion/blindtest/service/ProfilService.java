package com.myssteriion.blindtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class ProfilService {

	@Autowired
	private ProfilDAO profilDao;
	
	
	
	public ProfilDTO save(ProfilDTO profilDto, boolean throwIfExsits) throws SqlException, AlreadyExistsException  {
		
		Tool.verifyValue("profilDto", profilDto);
		
		profilDto.setId(null);
		ProfilDTO foundProfilDto = profilDao.find(profilDto);
		
		if (!Tool.isNullOrEmpty(foundProfilDto) && throwIfExsits)
			throw new AlreadyExistsException("profilDto already exists.");
		
		
		if ( Tool.isNullOrEmpty(foundProfilDto) )
			foundProfilDto = profilDao.save(profilDto);
		
		return foundProfilDto;
	}
	
	public ProfilDTO updated(ProfilDTO profilDto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("profilDto", profilDto);
		
		ProfilDTO foundProfilDto = profilDao.find(profilDto);
		
		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilDto not found.");

		foundProfilDto.setName( profilDto.getName() );
		foundProfilDto.setAvatar( profilDto.getAvatar() );
		
		return profilDao.update(foundProfilDto);
	}

	public List<ProfilDTO> findAll() throws SqlException {
		return profilDao.findAll();
	}
	
}
