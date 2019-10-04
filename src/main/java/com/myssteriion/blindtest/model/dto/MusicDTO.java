package com.myssteriion.blindtest.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Tool;

import java.util.Objects;

/**
 * The MusicDTO.
 */
public class MusicDTO extends AbstractDTO {

	/**
	 * The name.
	 */
	private String name;

	/**
	 * The theme.
	 */
	private Theme theme;

	/**
	 * The number of played.
	 */
	private int played;



	/**
	 * Instantiates a new MusicDto.
	 *
	 * @param name  the name
	 * @param theme the theme
	 */
	@JsonCreator
	public MusicDTO(String name, Theme theme) {
		this(name, theme, 0);
	}

	/**
	 * Instantiates a new MusicDto.
	 *
	 * @param name   the name
	 * @param theme  the theme
	 * @param played the played
	 */
	public MusicDTO(String name, Theme theme, int played) {

		Tool.verifyValue("name", name);
		Tool.verifyValue("theme", theme);

		this.name = name;
		this.theme = theme;
		this.played = Math.max(played, 0);
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
	 * Gets theme.
	 *
	 * @return the theme
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Gets played.
	 *
	 * @return the played
	 */
	public int getPlayed() {
		return played;
	}

	/**
	 * Increment played.
	 */
	public void incrementPlayed() {
		this.played++;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(name, theme);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		MusicDTO other = (MusicDTO) obj;
		return Objects.equals(this.name, other.name) && 
				Objects.equals(this.theme, other.theme);
	}

	@Override
	public String toString() {
		return super.toString() + 
				", name=" + name +
				", theme=" + theme +
				", played=" + played;
	}

}
