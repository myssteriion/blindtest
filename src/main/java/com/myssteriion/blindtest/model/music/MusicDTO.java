package com.myssteriion.blindtest.model.music;

import java.util.Objects;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Tool;

public class MusicDTO extends AbstractDTO {

	private String name;
	
	private Theme theme;
	
	private int nbPlayed;
	
	
	
	public MusicDTO(String name, Theme theme) {
		
		Tool.verifyValue("name", name);
		Tool.verifyValue("theme", theme);
		
		this.name = name;
		this.theme = theme;
		this.nbPlayed = 0;
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

	public void setNbPlayed(int nbPlayed) {
		this.nbPlayed = nbPlayed;
	}
	
	
	
	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hash(name, theme, nbPlayed);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		MusicDTO other = (MusicDTO) obj;
		return super.equals(obj) && 
				Objects.equals(this.name, other.name) &&
				Objects.equals(this.theme, other.theme) &&
				Objects.equals(this.nbPlayed, other.nbPlayed);
	}

	@Override
	public String toString() {
		return super.toString() + 
				", name=" + name +
				", theme=" + theme +
				", nbPlayed=" + nbPlayed;
	}

}
