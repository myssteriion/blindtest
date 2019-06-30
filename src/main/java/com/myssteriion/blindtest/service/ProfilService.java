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
	private ProfilDAO dao;
	
	
	
	public ProfilDTO save(ProfilDTO dto, boolean throwIfExsits) throws SqlException, AlreadyExistsException  {
		
		Tool.verifyValue("dto", dto);
		
		dto.setId(null);
		ProfilDTO foundDTO = dao.find(dto);
		
		if (!Tool.isNullOrEmpty(foundDTO) && throwIfExsits)
			throw new AlreadyExistsException("DTO already exists.");
		
		
		if ( Tool.isNullOrEmpty(foundDTO) )
			foundDTO = dao.save(dto);
		
		return foundDTO;
	}
	
	public ProfilDTO profilWasUpdated(ProfilDTO dto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("dto", dto);
		
		ProfilDTO foundDTO = dao.find(dto);
		
		if ( Tool.isNullOrEmpty(foundDTO) )
			throw new NotFoundException("DTO not found.");

		
		foundDTO.setName( dto.getName() );
		foundDTO.setAvatar( dto.getAvatar() );
		
		return dao.update(foundDTO);
	}
	
	public ProfilDTO profilWasPlayed(ProfilDTO dto, boolean beginGame, boolean foundMusic) throws SqlException, NotFoundException {
		
		Tool.verifyValue("dto", dto);
		
		ProfilDTO foundDTO = dao.find(dto);
		
		if ( Tool.isNullOrEmpty(foundDTO) )
			throw new NotFoundException("DTO not found.");
		
		
		if (beginGame)
			foundDTO.incrementPlayedGames();
		
		if (foundMusic)
			foundDTO.incrementFoundMusics();
		
		foundDTO.incrementListenedMusics();
		
		return dao.update(foundDTO);
	}

	public List<ProfilDTO> findAll() throws SqlException {
		return dao.findAll();
	}
	
}
