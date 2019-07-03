package com.myssteriion.blindtest.service;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.GameResultDTO;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;

public class GameResultServiceTest extends AbstractTest {

	@Mock
	private MusicService musicService;
	
	@Mock
	private ProfilService profilService;
	
	@Mock
	private ProfilStatService profilStatService;
	
	@InjectMocks
	private GameResultService gameResultService;
	
	
	
	@Test
	public void apply() throws SqlException, NotFoundException {
		
		MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_60, 0);
		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		profilDto.setId("1");
		ProfilStatDTO profilStatDto = new ProfilStatDTO("1");
		
		Mockito.when(musicService.musicWasPlayed( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(profilService.find( Mockito.any(ProfilDTO.class) )).thenReturn(null, profilDto);
		Mockito.when(profilStatService.find( Mockito.any(ProfilStatDTO.class) )).thenReturn(null, profilStatDto);


		try {
			gameResultService.apply(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'gameResultDto' est obligatoire."), e);
		}
		
		
		GameResultDTO gameResultDto = new GameResultDTO( false, musicDTO, Arrays.asList(profilDto), Arrays.asList(profilDto) );
		
		try {
			gameResultService.apply(gameResultDto);
			Assert.fail("Doit lever une IllegalArgumentException car le mock return null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profilDto not found."), e);
		}
		
		try {
			gameResultService.apply(gameResultDto);
			Assert.fail("Doit lever une IllegalArgumentException car le mock return null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profilStatDto not found."), e);
		}
		
		gameResultService.apply(gameResultDto);
	}

}
