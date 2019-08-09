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

	private List<String> winnersBonus;

	private List<String> neutral;

	private List<String> loosers;

	private List<String> loosersMalus;
	
	
	@JsonCreator
	public MusicResultDTO(Integer gameId, MusicDTO musicDTO, List<String> winners,  List<String> winnersBonus, List<String> neutral, List<String> loosers, List<String> loosersMalus) {

		Tool.verifyValue("gameId", gameId);
		Tool.verifyValue("musicDto", musicDTO);

		this.gameId = gameId;
		this.musicDTO = musicDTO;
		
		this.winners = (winners == null) ? new ArrayList<>() : winners;
		this.winnersBonus = (winnersBonus == null) ? new ArrayList<>() : winnersBonus;
		this.neutral = (neutral == null) ? new ArrayList<>() : neutral;
		this.loosers = (loosers == null) ? new ArrayList<>() : loosers;
		this.loosersMalus = (loosersMalus == null) ? new ArrayList<>() : loosersMalus;
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

	public List<String> getWinnersBonus() {
		return winnersBonus;
	}

	public List<String> getNeutral() {
		return neutral;
	}

	public List<String> getLoosers() {
		return loosers;
	}

	public List<String> getLoosersMalus() {
		return loosersMalus;
	}


	@Override
	public String toString() {
		return "gameId=" + gameId +
				", musicDTO={" + musicDTO + "}" +
				", winners=" + winners +
				", winnersBonus=" + winnersBonus +
				", neutral=" + neutral +
				", loosers=" + loosers +
				", loosersMalus=" + loosersMalus;
	}

}
