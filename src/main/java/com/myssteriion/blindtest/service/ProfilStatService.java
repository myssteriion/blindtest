package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.common.ConflictException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
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
	
	
	
	public ProfilStatDTO save(ProfilStatDTO profilStatDto) throws SqlException, NotFoundException, ConflictException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		checkProfilDto(profilStatDto);

		profilStatDto.setId(null);
		ProfilStatDTO foundProfilDto = profilStatDao.find(profilStatDto);
		
		if ( !Tool.isNullOrEmpty(foundProfilDto) )
			throw new ConflictException("profilStatDto already exists.");
		
		
		foundProfilDto = profilStatDao.save(profilStatDto);
		
		return foundProfilDto;
	}

	public ProfilStatDTO update(ProfilStatDTO profilStatDTO) throws SqlException, NotFoundException {

		Tool.verifyValue("profilStatDTO", profilStatDTO);
		Tool.verifyValue("profilStatDTO -> id", profilStatDTO.getId());

		ProfilStatDTO foundProfilStatDto = profilStatDao.find(profilStatDTO);
		if ( Tool.isNullOrEmpty(foundProfilStatDto) )
			throw new NotFoundException("profilStatDTO not found.");

		return profilStatDao.update(profilStatDTO);
	}

	public ProfilStatDTO find(ProfilStatDTO profilStatDto) throws SqlException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		return profilStatDao.find(profilStatDto);
	}
	
	public List<ProfilStatDTO> findAll() throws SqlException {
		return profilStatDao.findAll();
	}
	
	private void checkProfilDto(ProfilStatDTO profilStatDto) throws SqlException, NotFoundException {
	
		ProfilDTO profilDto = new ProfilDTO("ANY", "ANY");
		profilDto.setId( profilStatDto.getProfilId() );
		profilDto = profilService.find(profilDto);
		if ( Tool.isNullOrEmpty(profilDto) )
			throw new NotFoundException("profilDto not found.");
	}
	
}
