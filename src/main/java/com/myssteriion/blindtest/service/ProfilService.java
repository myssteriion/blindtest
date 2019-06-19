package com.myssteriion.blindtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class ProfilService {

	@Autowired
	private ProfilDAO dao;
	
	
	
	public List<ProfilDTO> findAll() throws EntityManagerException {
		return dao.findAll();
	}
	
	public ProfilDTO saveOrUpdate(String name, String avatar) throws EntityManagerException {
		
		Tool.verifyValue("name", name);
		Tool.verifyValue("avatar", avatar);
		
		ProfilDTO dto = new ProfilDTO(name, avatar);
		return dao.saveOrUpdate(dto);
	}
	
}
