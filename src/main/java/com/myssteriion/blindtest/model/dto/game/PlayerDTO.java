package com.myssteriion.blindtest.model.dto.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.tools.Tool;

import java.util.Comparator;
import java.util.Objects;

public class PlayerDTO {

	public static final Comparator<PlayerDTO> COMPARATOR = new Comparator<PlayerDTO>() {

		@Override
		public int compare(PlayerDTO o1, PlayerDTO o2) {

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

	private String name;

	private int score;

	private boolean turnToChoose;



	@JsonCreator
	public PlayerDTO(String name) {

		Tool.verifyValue("name", name);

		this.name = name.trim();
		this.score = 0;
		this.turnToChoose = false;
	}



	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public boolean isTurnToChoose() {
		return turnToChoose;
	}

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
		
		PlayerDTO other = (PlayerDTO) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public String toString() {
		return "name=" + name +
				", score=" + score +
				", turnToChoose=" + turnToChoose;
	}
	
}
