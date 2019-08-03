package com.myssteriion.blindtest.model.dto.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.tools.Tool;

import java.util.Objects;

public class PlayerDTO {

	private String name;

	private int score;



	@JsonCreator
	public PlayerDTO(String name, int score) {

		Tool.verifyValue("name", name);

		this.name = name.trim();
		this.score = (score < 0) ? 0 : score;
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



	@Override
	public int hashCode() {
		return Objects.hash(name, score);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		PlayerDTO other = (PlayerDTO) obj;
		return Objects.equals(this.name, other.name) && Objects.equals(this.score, other.score);
	}

	@Override
	public String toString() {
		return "name=" + name +
				", score=" + score;
	}
	
}
