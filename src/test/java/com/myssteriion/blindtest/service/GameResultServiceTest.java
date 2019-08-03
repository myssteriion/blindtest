package com.myssteriion.blindtest.service;

import java.util.Arrays;

import com.myssteriion.blindtest.model.common.GameResultType;
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
		profilDto.setId(1);
		ProfilStatDTO profilStatDto = new ProfilStatDTO(1);
		
		Mockito.when(musicService.update( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(profilService.find( Mockito.any(ProfilDTO.class) )).thenReturn(null, profilDto);
		Mockito.when(profilStatService.find( Mockito.any(ProfilStatDTO.class) )).thenReturn(null, profilStatDto);


		try {
			gameResultService.apply(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'gameResultDto' est obligatoire."), e);
		}
		
		
		GameResultDTO gameResultDto = new GameResultDTO( GameResultType.NORMAL, musicDTO, Arrays.asList(profilDto), Arrays.asList(profilDto) );

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
		Mockito.verify(musicService, Mockito.times(3)).update( Mockito.any(MusicDTO.class) );
		Mockito.verify(profilStatService, Mockito.times(0)).updatePlayedGames( Mockito.any(ProfilStatDTO.class) );
		Mockito.verify(profilStatService, Mockito.times(2)).updateListenedMusics( Mockito.any(ProfilStatDTO.class) );
		Mockito.verify(profilStatService, Mockito.times(1)).updateFoundMusics( Mockito.any(ProfilStatDTO.class) );
		
		gameResultDto = new GameResultDTO( GameResultType.FIRST, musicDTO, Arrays.asList(profilDto), Arrays.asList(profilDto) );
		gameResultService.apply(gameResultDto);
		Mockito.verify(musicService, Mockito.times(4)).update( Mockito.any(MusicDTO.class) );
		Mockito.verify(profilStatService, Mockito.times(2)).updatePlayedGames( Mockito.any(ProfilStatDTO.class) );
		Mockito.verify(profilStatService, Mockito.times(4)).updateListenedMusics( Mockito.any(ProfilStatDTO.class) );
		Mockito.verify(profilStatService, Mockito.times(2)).updateFoundMusics( Mockito.any(ProfilStatDTO.class) );
	}

}
