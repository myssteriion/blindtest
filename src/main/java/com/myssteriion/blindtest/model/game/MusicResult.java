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
	private MusicDTO music;

	/**
	 * The author winners list.
	 */
	private List<String> authorWinners;

	/**
	 * The title winners list.
	 */
	private List<String> titleWinners;

	/**
	 * The losers list.
	 */
	private List<String> losers;



	/**
	 * Instantiates a new Music result.
	 *
	 * @param gameId   		the game id
	 * @param music 		the music
	 * @param authorWinners	the author winners
	 * @param titleWinners	the title winners
	 * @param losers  		the losers
	 */
	@JsonCreator
	public MusicResult(Integer gameId, MusicDTO music, List<String> authorWinners, List<String> titleWinners, List<String> losers) {

		Tool.verifyValue("gameId", gameId);
		Tool.verifyValue("music", music);

		this.gameId = gameId;
		this.music = music;
		
		this.authorWinners = (authorWinners == null) ? new ArrayList<>() : authorWinners;
		this.titleWinners = (titleWinners == null) ? new ArrayList<>() : titleWinners;
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
	 * Gets music.
	 *
	 * @return the music
	 */
	public MusicDTO getMusic() {
		return music;
	}

	/**
	 * Gets author winners.
	 *
	 * @return the winners
	 */
	public List<String> getAuthorWinners() {
		return authorWinners;
	}

	/**
	 * Gets title winners.
	 *
	 * @return the winners
	 */
	public List<String> getTitleWinners() {
		return titleWinners;
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
	 * Test if "name" is a author winner.
	 *
	 * @param name the name
	 * @return TRUE if "name" is a author winner, FALSE otherwise
	 */
	public boolean isAuthorWinner(String name) {

		Tool.verifyValue("name", name);

		return authorWinners.stream().anyMatch(authorWinner -> authorWinner.equals(name));
	}

	/**
	 * Test if "name" is a title winner.
	 *
	 * @param name the name
	 * @return TRUE if "name" is a title winner, FALSE otherwise
	 */
	public boolean isTitleWinner(String name) {

		Tool.verifyValue("name", name);

		return titleWinners.stream().anyMatch(titleWinner -> titleWinner.equals(name));
	}

	/**
	 * Test if "name" is a author and title winner.
	 *
	 * @param name the name
	 * @return TRUE if "name" is a author and title winner, FALSE otherwise
	 */
	public boolean isAuthorAndTitleWinner(String name) {

		Tool.verifyValue("name", name);

		return isAuthorWinner(name) && isTitleWinner(name);
	}

	/**
	 * Test if "name" is a looser.
	 *
	 * @param name the name
	 * @return TRUE if "name" is a looser, FALSE otherwise
	 */
	public boolean isLoser(String name) {

		Tool.verifyValue("name", name);

		return losers.stream().anyMatch(winner -> winner.equals(name));
	}



	@Override
	public String toString() {
		return "gameId=" + gameId +
				", music={" + music + "}" +
				", authorWinners=" + authorWinners +
				", titleWinners=" + titleWinners +
				", losers=" + losers;
	}

}
