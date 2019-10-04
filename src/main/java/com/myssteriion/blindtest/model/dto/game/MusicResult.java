package com.myssteriion.blindtest.model.dto.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.tools.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a result of music.
 */
public class MusicResult {

	/**
	 * The game id.
	 */
	private Integer gameId;

	/**
	 * The music.
	 */
	private MusicDTO musicDTO;

	/**
	 * The winners list.
	 */
	private List<String> winners;

	/**
	 * The loosers list.
	 */
	private List<String> loosers;


	/**
	 * Instantiates a new Music result.
	 *
	 * @param gameId   the game id
	 * @param musicDTO the music dto
	 * @param winners  the winners
	 * @param loosers  the losers
	 */
	@JsonCreator
	public MusicResult(Integer gameId, MusicDTO musicDTO, List<String> winners, List<String> loosers) {

		Tool.verifyValue("gameId", gameId);
		Tool.verifyValue("musicDto", musicDTO);

		this.gameId = gameId;
		this.musicDTO = musicDTO;
		
		this.winners = (winners == null) ? new ArrayList<>() : winners;
		this.loosers = (loosers == null) ? new ArrayList<>() : loosers;
	}


	/**
	 * Gets game id.
	 *
	 * @return the game id
	 */
	public Integer getGameId() {
		return gameId;
	}

	/**
	 * Gets music dto.
	 *
	 * @return the music dto
	 */
	public MusicDTO getMusicDTO() {
		return musicDTO;
	}

	/**
	 * Gets winners.
	 *
	 * @return the winners
	 */
	public List<String> getWinners() {
		return winners;
	}

	/**
	 * Gets loosers.
	 *
	 * @return the loosers
	 */
	public List<String> getLoosers() {
		return loosers;
	}


	/**
	 * Test if "name" is a winner.
	 *
	 * @param name the name
	 * @return TRUE if "name" is a winner, FALSE otherwise
	 */
	public boolean isWinner(String name) {

		Tool.verifyValue("name", name);

		return winners.stream().anyMatch(winner -> winner.equals(name));
	}

	/**
	 * Test if "name" is a looser.
	 *
	 * @param name the name
	 * @return TRUE if "name" is a looser, FALSE otherwise
	 */
	public boolean isLooser(String name) {

		Tool.verifyValue("name", name);

		return loosers.stream().anyMatch(winner -> winner.equals(name));
	}



	@Override
	public String toString() {
		return "gameId=" + gameId +
				", musicDTO={" + musicDTO + "}" +
				", winners=" + winners +
				", loosers=" + loosers;
	}

}
