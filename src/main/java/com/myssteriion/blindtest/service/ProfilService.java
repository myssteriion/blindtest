package com.myssteriion.blindtest.service;

import java.util.List;

import com.myssteriion.blindtest.db.common.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	
	public ProfilDTO save(ProfilDTO profilDto) throws SqlException, NotFoundException, ConflictException {
		
		Tool.verifyValue("profilDto", profilDto);
		
		profilDto.setId(null);
		ProfilDTO foundProfilDto = profilDao.find(profilDto);
		
		if ( !Tool.isNullOrEmpty(foundProfilDto) )
			throw new ConflictException("The profilDto name is already used.");
		
		
		foundProfilDto = profilDao.save(profilDto);
		
		profilStatService.save( new ProfilStatDTO(foundProfilDto.getId()) );
		
		return foundProfilDto;
	}
	
	public ProfilDTO update(ProfilDTO profilDto) throws SqlException, NotFoundException, ConflictException {
		
		Tool.verifyValue("profilDto", profilDto);
		Tool.verifyValue("profilDto -> id", profilDto.getId());
		
		ProfilDTO foundProfilDto = profilDao.find(profilDto);
		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilDto not found.");
		
		
		Integer id = profilDto.getId();
		profilDto.setId(null);
		
		foundProfilDto = profilDao.find(profilDto);
		if ( foundProfilDto != null  && !foundProfilDto.getId().equals(id) )
			throw new ConflictException("The profilDto name is already used.");
		
		profilDto.setId(id);
		return profilDao.update(profilDto);
	}

	public ProfilDTO find(ProfilDTO profilDto) throws SqlException {
		
		Tool.verifyValue("profilDto", profilDto);
		return profilDao.find(profilDto);
	}
	
	public List<ProfilDTO> findAll() throws SqlException {
		return profilDao.findAll();
	}
	
}
