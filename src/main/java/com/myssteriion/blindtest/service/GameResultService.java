package com.myssteriion.blindtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.GameResultDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.tools.Tool;

@Service
public class GameResultService {
	
	@Autowired
	private MusicService musicService;
	
	@Autowired
	private ProfilService profilService;
	
	@Autowired
	private ProfilStatService profilStatService;
	
	
	
	public void apply(GameResultDTO gameResultDto) throws SqlException, NotFoundException {
		
		Tool.verifyValue("gameResultDto", gameResultDto);
		
		musicService.updatePlayed( gameResultDto.getMusicDTO() );
		
		for (ProfilDTO winner : gameResultDto.getWinners() ) {
			
			ProfilStatDTO profilStatDTO = findProfilStatDto(winner);
			profilStatService.updateListenedMusics(profilStatDTO);
			profilStatService.updateFoundMusics(profilStatDTO);
		}
		
		for (ProfilDTO looser : gameResultDto.getLoosers() ) {
			
			ProfilStatDTO profilStatDTO = findProfilStatDto(looser);
			profilStatService.updateListenedMusics(profilStatDTO);
		}
	}
	
	private ProfilStatDTO findProfilStatDto(ProfilDTO profilDto) throws SqlException, NotFoundException {
		
		ProfilDTO foundProfilDto = profilService.find(profilDto);
		
		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilDto not found.");
		
		ProfilStatDTO profilStatDTO = new ProfilStatDTO(foundProfilDto.getId());
		ProfilStatDTO foundProfilStatDTO = profilStatService.find(profilStatDTO);
		
		if ( Tool.isNullOrEmpty(foundProfilStatDTO) )
			throw new NotFoundException("profilStatDto not found.");
		
		return foundProfilStatDTO;
	}
	
}
