package com.myssteriion.blindtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.db.dao.ProfilStatDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class ProfilStatService {

	@Autowired
	private ProfilStatDAO profilStatDao;
	
	@Autowired
	private ProfilService profilService;
	
	
	
	public ProfilStatDTO save(ProfilStatDTO profilStatDto) throws SqlException, NotFoundException, AlreadyExistsException  {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		chekcProfilDto(profilStatDto);

		profilStatDto.setId(null);
		ProfilStatDTO foundProfilDto = profilStatDao.find(profilStatDto);
		
		if ( !Tool.isNullOrEmpty(foundProfilDto) )
			throw new AlreadyExistsException("profilStatDto already exists.");
		
		
		foundProfilDto = profilStatDao.save(profilStatDto);
		
		return foundProfilDto;
	}
	
	public ProfilStatDTO updatePlayedGames(ProfilStatDTO profilStatDto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		
		ProfilStatDTO foundProfilDto = profilStatDao.find(profilStatDto);
		
		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilStatDto not found.");

		
		foundProfilDto.incrementPlayedGames();
		
		return profilStatDao.update(foundProfilDto);
	}
	
	public ProfilStatDTO updateListenedMusics(ProfilStatDTO profilStatDto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		
		ProfilStatDTO foundProfilDto = profilStatDao.find(profilStatDto);
		
		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilStatDto not found.");

		
		foundProfilDto.incrementListenedMusics();
		
		return profilStatDao.update(foundProfilDto);
	}
	
	public ProfilStatDTO updateFoundMusics(ProfilStatDTO profilStatDto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		
		ProfilStatDTO foundProfilDto = profilStatDao.find(profilStatDto);
		
		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilStatDto not found.");

		
		foundProfilDto.incrementFoundMusics();;
		
		return profilStatDao.update(foundProfilDto);
	}

	public ProfilStatDTO find(ProfilStatDTO profilStatDto) throws SqlException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		return profilStatDao.find(profilStatDto);
	}
	
	public List<ProfilStatDTO> findAll() throws SqlException {
		return profilStatDao.findAll();
	}
	
	private void chekcProfilDto(ProfilStatDTO profilStatDto) throws SqlException, NotFoundException {
	
		ProfilDTO profilDto = new ProfilDTO("ANY", "ANY");
		profilDto.setId( profilStatDto.getProfilId() );
		profilDto = profilService.find(profilDto);
		if ( Tool.isNullOrEmpty(profilDto) )
			throw new NotFoundException("profilDto not found.");
	}
	
}
