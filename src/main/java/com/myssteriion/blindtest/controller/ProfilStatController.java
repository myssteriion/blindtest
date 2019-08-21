package com.myssteriion.blindtest.controller;

import java.util.List;

import com.myssteriion.blindtest.db.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.ProfilStatService;

@CrossOrigin
@RestController
@RequestMapping(
	path = "profilstats"
)
public class ProfilStatController {

	@Autowired
	private ProfilStatService profilStatService;
	
	
	
	@RequestMapping(
		method = RequestMethod.GET
	)
	public ResponseEntity< ListDTO<ProfilStatDTO> > findAll() throws DaoException {
		
		List<ProfilStatDTO> list = profilStatService.findAll();
		return ResponseBuilder.create200(list);
	}
	
}
