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

public class GameResultServiceTest extends AbstractTest {

	@Mock
	private MusicService musicService;
	
	@Mock
	private ProfilService profilService;
	
	@InjectMocks
	private GameResultService gameResultService;
	
	
	
	@Test
	public void testApply() throws SqlException, NotFoundException {
		
		ProfilDTO profilDto = new ProfilDTO("name", "avatar", 0, 0, 0);
		MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_60, 0);
		
		Mockito.when(musicService.musicWasPlayed( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(profilService.profilWasPlayed( Mockito.any(ProfilDTO.class), Mockito.anyBoolean(), Mockito.anyBoolean() )).thenReturn(profilDto);
		
		
		try {
			gameResultService.apply(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'gameResultDto' est obligatoire."), e);
		}
		
		GameResultDTO gameResultDto = new GameResultDTO( false, musicDTO, Arrays.asList(profilDto), Arrays.asList(profilDto) );
		gameResultService.apply(gameResultDto);
	}

}
