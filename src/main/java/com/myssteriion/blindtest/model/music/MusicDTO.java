package com.myssteriion.blindtest.model.music;

import java.util.Objects;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

public class MusicDTO extends AbstractDTO {

	private String name;
	
	private Theme theme;
	
	private int nbPlayed;
	
	
	
	public MusicDTO(String name, Theme theme) {
		this(name, theme, 0);
	}
	
	public MusicDTO(String name, Theme theme, int nbPlayed) {
		
		Tool.verifyValue("name", name);
		Tool.verifyValue("theme", theme);
		
		this.name = name.replace(Constant.QUOTE, Constant.DOUBLE_QUOTE);
		this.theme = theme;
		this.nbPlayed = (nbPlayed < 0) ? 0 : nbPlayed;
	}
	
	
	
	public String getName() {
		return name;
	}

	public Theme getTheme() {
		return theme;
	}

	public int getNbPlayed() {
		return nbPlayed;
	}

	public void incrementNbPlayed() {
		this.nbPlayed++;
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
				", nbPlayed=" + nbPlayed;
	}

}
