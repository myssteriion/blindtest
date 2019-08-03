package com.myssteriion.blindtest.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.gameresult.GameResultDTO;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.service.GameResultService;

public class GameResultControllerTest extends AbstractTest {

	@Mock
	private GameResultService gameResultService;
	
	@InjectMocks
	private GameResultController gameResultController;
	
	
	
	@Test
	public void testApply() throws SqlException, NotFoundException {

		Mockito.doNothing().when(gameResultService).apply( Mockito.any(GameResultDTO.class) );
		
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60);
		GameResultDTO gameResultDto = new GameResultDTO(false, musicDto, null, null);
		
		ResponseEntity<Empty> re = gameResultController.apply(gameResultDto);
		Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
	}

}
