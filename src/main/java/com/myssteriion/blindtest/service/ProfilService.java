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
		
		ProfilDTO foundDTO = dao.find(dto);
		
		if (!Tool.isNullOrEmpty(foundDTO) && throwIfExsits)
			throw new AlreadyExistsException("DTO already exists.");
		
		else if ( Tool.isNullOrEmpty(foundDTO) )
			foundDTO = dao.save(dto);
		
		else
			foundDTO = dto;
		
		return foundDTO;
	}
	
	public ProfilDTO update(ProfilDTO dto, boolean throwIfNotExsits) throws SqlException, NotFoundException {
		
		Tool.verifyValue("dto", dto);
		Tool.verifyValue("dto -> id", dto.getId());
		
		ProfilDTO foundDTO = dao.find(dto);
		
		if (Tool.isNullOrEmpty(foundDTO) && throwIfNotExsits) {
			throw new NotFoundException("DTO not found.");
		}
		else if ( !Tool.isNullOrEmpty(foundDTO) ) {
			dto.setId( foundDTO.getId() );
			foundDTO = dao.update(dto);
		}
		else
			foundDTO = dto;
		
		return foundDTO;
	}
	
	public ProfilDTO saveOrUpdate(ProfilDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		ProfilDTO foundDto = dao.find(dto);
		if (foundDto == null)
			foundDto = dao.save(dto);
		else
			foundDto = dao.update(dto);
		
		return foundDto;
	}
	
	public List<ProfilDTO> findAll() throws SqlException {
		return dao.findAll();
	}
	
}
