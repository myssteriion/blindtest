package com.myssteriion.blindtest.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.tools.Tool;

import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a player.
 */
public class Player {

	/**
	 * The constant COMPARATOR.
	 */
	public static final Comparator<Player> COMPARATOR = (o1, o2) -> {

		if (o1 != null && o2 == null)
			return 1;
		else if (o1 == null && o2 != null)
			return -1;
		else if (o1 == null && o2 == null)
			return 0;
		else
			return String.CASE_INSENSITIVE_ORDER.compare(o1.profile.getName(), o2.profile.getName());
	};

	/**
	 * The profile.
	 */
	private ProfileDTO profile;

	/**
	 * The current score.
	 */
	private int score;

	/**
	 * If is his turn to play/choose.
	 */
	private boolean turnToChoose;


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
		this.turnToChoose = false;
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



	@Override
	public int hashCode() {
		return Objects.hash(profile.getName());
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		Player other = (Player) obj;
		return Objects.equals(this.profile.getName(), other.profile.getName());
	}

	@Override
	public String toString() {
		return "profile={" + profile + "}" +
				", score=" + score +
				", turnToChoose=" + turnToChoose;
	}
	
}
