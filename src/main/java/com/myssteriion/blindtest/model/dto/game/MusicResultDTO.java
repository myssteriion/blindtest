package com.myssteriion.blindtest.model.dto.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class MusicResultDTO {

	private Integer gameId;

	private MusicDTO musicDTO;

	private List<String> winners;

	private List<String> neutral;

	private List<String> loosers;
	
	
	
	@JsonCreator
	public MusicResultDTO(Integer gameId, MusicDTO musicDTO, List<String> winners, List<String> neutral, List<String> loosers) {

		Tool.verifyValue("gameId", gameId);
		Tool.verifyValue("musicDto", musicDTO);

		this.gameId = gameId;
		this.musicDTO = musicDTO;
		
		this.winners = (winners == null) ? new ArrayList<>() : winners;
		this.neutral = (neutral == null) ? new ArrayList<>() : neutral;
		this.loosers = (loosers == null) ? new ArrayList<>() : loosers;
	}



	public Integer getGameId() {
		return gameId;
	}

	public MusicDTO getMusicDTO() {
		return musicDTO;
	}
	
	public List<String> getWinners() {
		return winners;
	}

	public List<String> getNeutral() {
		return neutral;
	}

	public List<String> getLoosers() {
		return loosers;
	}

	@Override
	public String toString() {
		return "gameId=" + gameId +
				", musicDTO={" + musicDTO + "}" +
				", winners=" + winners +
				", neutral=" + neutral +
				", loosers=" + loosers;
	}

}
