package com.myssteriion.blindtest.model.dto.gameresult;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.common.GameResultType;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class GameResultDTO {

	private GameResultType type;

	private MusicDTO musicDTO;
	
	private List<ProfilDTO> winners;
	
	private List<ProfilDTO> loosers;
	
	
	
	@JsonCreator
	public GameResultDTO(GameResultType type, MusicDTO musicDTO, List<ProfilDTO> winners, List<ProfilDTO> loosers) {
		
		Tool.verifyValue("musicDto", musicDTO);
		Tool.verifyValue("type", type);

		this.type = type;
		this.musicDTO = musicDTO;
		
		this.winners = (winners == null) ? new ArrayList<>() : winners;
		this.loosers = (loosers == null) ? new ArrayList<>() : loosers;
	}
	
	
	
	public GameResultType getType() {
		return type;
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
