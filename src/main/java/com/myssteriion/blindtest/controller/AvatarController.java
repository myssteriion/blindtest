package com.myssteriion.blindtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.AvatarService;

@CrossOrigin
@RestController
@RequestMapping(path = "avatars")
public class AvatarController {

	@Autowired
	private AvatarService avatarService;
	
	
	
	@GetMapping(path = "/refresh")
	public ResponseEntity<Empty> refresh() {
		
		avatarService.refresh();
		return ResponseBuilder.create204();
	}
	
	@GetMapping
	public ResponseEntity< ListDTO<Avatar> > getAll() throws DaoException {
		
		List<Avatar> list = avatarService.getAll();
		return ResponseBuilder.create200(list);
	}
	
}
