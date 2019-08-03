package com.myssteriion.blindtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.dto.gameresult.GameResultDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.GameResultService;

@CrossOrigin
@RestController
@RequestMapping(
	path = "gameresult"
)
public class GameResultController {

	@Autowired
	private GameResultService gameResultService;
	
	
	
	@RequestMapping(
		method = RequestMethod.POST
	)
	public ResponseEntity<Empty> apply(@RequestBody GameResultDTO gameResultDto) throws SqlException, NotFoundException {
		
		gameResultService.apply(gameResultDto);
		return ResponseBuilder.create204();
	}
	
}
