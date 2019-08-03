package com.myssteriion.blindtest.model.dto.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.common.NumMusic;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class GameResultDTO {

	private Integer gameId;

	private NumMusic numMusic;

	private Round round;

	private MusicDTO musicDTO;

	private List<String> winners;
	
	private List<String> loosers;
	
	
	
	@JsonCreator
	public GameResultDTO(Integer gameId, NumMusic numMusic, Round round, MusicDTO musicDTO, List<String> winners, List<String> loosers) {

		Tool.verifyValue("gameId", gameId);
		Tool.verifyValue("numMusic", numMusic);
		Tool.verifyValue("round", round);
		Tool.verifyValue("musicDto", musicDTO);

		this.gameId = gameId;
		this.numMusic = numMusic;
		this.round = round;
		this.musicDTO = musicDTO;
		
		this.winners = (winners == null) ? new ArrayList<>() : winners;
		this.loosers = (loosers == null) ? new ArrayList<>() : loosers;
	}



	public Integer getGameId() {
		return gameId;
	}

	public NumMusic getNumMusic() {
		return numMusic;
	}

	public Round getRound() {
		return round;
	}

	public MusicDTO getMusicDTO() {
		return musicDTO;
	}
	
	public List<String> getWinners() {
		return winners;
	}
	
	public List<String> getLoosers() {
		return loosers;
	}
	
}
