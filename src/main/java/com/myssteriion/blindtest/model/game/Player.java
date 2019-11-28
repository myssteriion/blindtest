package com.myssteriion.blindtest.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.tools.Tool;
import com.myssteriion.blindtest.tools.converter.ThemeWinModeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a player.
 */
public class Player {

	/**
	 * The profile.
	 */
	private ProfileDTO profile;

	/**
	 * The current score.
	 */
	private int score;

	/**
	 * The rank.
	 */
	private Rank rank;

	/**
	 * If is his turn to play/choose.
	 */
	private boolean turnToChoose;

    /**
     * The number of found musics by themes by WinMode.
     */
    private Map<Theme, Map<GoodAnswer, Integer>> foundMusics;



	/**
	 * Instantiates a new Player.
	 *
	 * @param profile the profile
	 */
	@JsonCreator
	public Player(ProfileDTO profile) {

		Tool.verifyValue("profile", profile);

		this.profile = profile;
		this.score = 0;
		this.rank = Rank.FIRST;
		this.turnToChoose = false;
		this.foundMusics = new HashMap<>();
	}



	/**
	 * Gets profile.
	 *
	 * @return the profile
	 */
	public ProfileDTO getProfile() {
		return profile;
	}

	/**
	 * Gets score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Add score.
	 *
	 * @param score the score
	 */
	public void addScore(int score) {
		this.score += score;
	}

	/**
	 * Gets rank.
	 *
	 * @return The rank.
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Set rank.
	 *
	 * @param rank The rank.
	 */
	public Player setRank(Rank rank) {
		this.rank = rank;
		return this;
	}

	/**
	 * Is turn to choose boolean.
	 *
	 * @return the boolean
	 */
	public boolean isTurnToChoose() {
		return turnToChoose;
	}

	/**
	 * Sets turn to choose.
	 *
	 * @param turnToChoose the turn to choose
	 */
	public void setTurnToChoose(boolean turnToChoose) {
		this.turnToChoose = turnToChoose;
	}

    /**
     * Gets found musics.
     *
     * @return the found musics
     */
    public Map< Theme, Map<GoodAnswer, Integer> > getFoundMusics() {
        return foundMusics;
    }

    /**
     * Increment foundMusics.
     *
     * @param theme 	the theme
     * @param goodAnswer 	the winMode
     */
    public void incrementFoundMusics(Theme theme, GoodAnswer goodAnswer) {

        Tool.verifyValue("theme", theme);
        Tool.verifyValue("winMode", goodAnswer);

        if (foundMusics == null)
            foundMusics = new HashMap<>();

        if ( !foundMusics.containsKey(theme) )
            foundMusics.put(theme, new HashMap<>());

        if ( !foundMusics.get(theme).containsKey(goodAnswer) )
            foundMusics.get(theme).put(goodAnswer, 0);

        foundMusics.get(theme).put(goodAnswer, foundMusics.get(theme).get(goodAnswer) + 1);
    }



	@Override
	public int hashCode() {
		return Objects.hash(profile);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		Player other = (Player) obj;
		return Objects.equals(this.profile, other.profile);
	}

	@Override
	public String toString() {
		return "profile={" + profile + "}" +
				", score=" + score +
				", rank=" + rank +
				", turnToChoose=" + turnToChoose +
                ", foundMusics=" + foundMusics;
	}
	
}
