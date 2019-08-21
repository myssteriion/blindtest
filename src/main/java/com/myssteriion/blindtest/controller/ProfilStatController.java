package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.ProfilStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "profilstats")
public class ProfilStatController {

	@Autowired
	private ProfilStatService profilStatService;
	
	
	
	@GetMapping
	public ResponseEntity< ListDTO<ProfilStatDTO> > findAll() throws DaoException {
		
		List<ProfilStatDTO> list = profilStatService.findAll();
		return ResponseBuilder.create200(list);
	}
	
}
