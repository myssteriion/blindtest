package com.myssteriion.blindtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class ProfilService {

	@Autowired
	private ProfilDAO dao;
	
	
	
	public ProfilDTO save(ProfilDTO dto, boolean throwIfExsits) throws SqlException  {
		
		Tool.verifyValue("dto", dto);
		
		ProfilDTO savedDTO = dto;
		try {
			savedDTO = dao.save(dto);
		}
		catch (SqlException e) {
			if (throwIfExsits)
				throw e;
		}
		
		return savedDTO;
	}
	
	public ProfilDTO update(ProfilDTO dto, boolean throwIfNotExsits) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		ProfilDTO savedDTO = dto;
		try {
			savedDTO = dao.update(dto);
		}
		catch (SqlException e) {
			if (throwIfNotExsits)
				throw e;
		}
		
		return savedDTO;
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
