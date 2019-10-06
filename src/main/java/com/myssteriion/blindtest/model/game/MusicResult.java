package com.myssteriion.blindtest.model.game;

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
	 * The losers list.
	 */
	private List<String> losers;


	/**
	 * Instantiates a new Music result.
	 *
	 * @param gameId   the game id
	 * @param musicDTO the music dto
	 * @param winners  the winners
	 * @param losers  the losers
	 */
	@JsonCreator
	public MusicResult(Integer gameId, MusicDTO musicDTO, List<String> winners, List<String> losers) {

		Tool.verifyValue("gameId", gameId);
		Tool.verifyValue("musicDto", musicDTO);

		this.gameId = gameId;
		this.musicDTO = musicDTO;
		
		this.winners = (winners == null) ? new ArrayList<>() : winners;
		this.losers = (losers == null) ? new ArrayList<>() : losers;
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
	 * Gets losers.
	 *
	 * @return the losers
	 */
	public List<String> getLosers() {
		return losers;
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

		return losers.stream().anyMatch(winner -> winner.equals(name));
	}



	@Override
	public String toString() {
		return "gameId=" + gameId +
				", musicDTO={" + musicDTO + "}" +
				", winners=" + winners +
				", losers=" + losers;
	}

}
