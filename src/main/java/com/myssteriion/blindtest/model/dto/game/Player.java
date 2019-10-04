package com.myssteriion.blindtest.model.dto.game;

import com.fasterxml.jackson.annotation.JsonCreator;
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
	public static final Comparator<Player> COMPARATOR = new Comparator<Player>() {

		@Override
		public int compare(Player o1, Player o2) {

			if (o1 != null && o2 == null)
				return 1;
			else if (o1 == null && o2 != null)
				return -1;
			else if (o1 == null && o2 == null)
				return 0;
			else
				return String.CASE_INSENSITIVE_ORDER.compare(o1.name, o2.name);
		}
	};

	/**
	 * The name.
	 */
	private String name;

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
	 * @param name the name
	 */
	@JsonCreator
	public Player(String name) {

		Tool.verifyValue("name", name);

		this.name = name.trim();
		this.score = 0;
		this.turnToChoose = false;
	}


	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		Player other = (Player) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public String toString() {
		return "name=" + name +
				", score=" + score +
				", turnToChoose=" + turnToChoose;
	}
	
}
