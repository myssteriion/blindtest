package com.myssteriion.blindtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.GameResultDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class GameResultService {
	
	@Autowired
	private MusicService musicService;
	
	@Autowired
	private ProfilService profilService;
	
	
	
	public void apply(GameResultDTO gameResultDto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("gameResultDto", gameResultDto);
		
		musicService.musicWasPlayed( gameResultDto.getMusicDTO() );
		
		for (ProfilDTO winner : gameResultDto.getWinners() )
			profilService.profilWasPlayed(winner, gameResultDto.isFirstMusic(), true);
		
		for (ProfilDTO looser : gameResultDto.getLoosers() )
			profilService.profilWasPlayed(looser, gameResultDto.isFirstMusic(), false);
	}
	
}
