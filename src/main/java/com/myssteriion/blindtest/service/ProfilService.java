package com.myssteriion.blindtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.db.exception.SqlException;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class ProfilService {

	@Autowired
	private ProfilDAO dao;
	
	
	
	public ProfilDTO save(ProfilDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		ProfilDTO foundDto = dao.find(dto);
		if (foundDto == null)
			foundDto = dao.save(dto);
		
		return foundDto;
	}
	
	public List<ProfilDTO> findAll() throws SqlException {
		return dao.findAll();
	}
	
}
