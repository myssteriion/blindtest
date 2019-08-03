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

	private NumMusic numMusic;

	private Round round;

	private MusicDTO musicDTO;

	private List<ProfilDTO> winners;
	
	private List<ProfilDTO> loosers;
	
	
	
	@JsonCreator
	public GameResultDTO(NumMusic numMusic, Round round, MusicDTO musicDTO, List<ProfilDTO> winners, List<ProfilDTO> loosers) {

		Tool.verifyValue("numMusic", numMusic);
		Tool.verifyValue("round", round);
		Tool.verifyValue("musicDto", musicDTO);

		this.numMusic = numMusic;
		this.round = round;
		this.musicDTO = musicDTO;
		
		this.winners = (winners == null) ? new ArrayList<>() : winners;
		this.loosers = (loosers == null) ? new ArrayList<>() : loosers;
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
	
	public List<ProfilDTO> getWinners() {
		return winners;
	}
	
	public List<ProfilDTO> getLoosers() {
		return loosers;
	}
	
}
