package com.myssteriion.blindtest.model.dto.gameresult;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

public class GameResultDTO {
	
	private boolean isFirstMusic;

	private MusicDTO musicDTO;
	
	private List<ProfilDTO> winners;
	
	private List<ProfilDTO> loosers;
	
	
	
	@JsonCreator
	public GameResultDTO(boolean isFirstMusic, MusicDTO musicDTO, List<ProfilDTO> winners, List<ProfilDTO> loosers) {
		
		Tool.verifyValue("musicDto", musicDTO);
		
		this.isFirstMusic = isFirstMusic;
		this.musicDTO = musicDTO;
		
		this.winners = (winners == null) ? new ArrayList<>() : winners;
		this.loosers = (loosers == null) ? new ArrayList<>() : loosers;
	}
	
	
	
	public boolean isFirstMusic() {
		return isFirstMusic;
	}
	
	public MusicDTO getMusicDTO() {
		return musicDTO;
	}
	
	public List<ProfilDTO> getWinners() {
		return winners;
	}
	
	public List<ProfilDTO> getLoosers() {
		return loosers;
	}
	
}
