package com.myssteriion.blindtest.model.dto;

import java.util.Objects;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

public class MusicDTO extends AbstractDTO {

	private String name;
	
	private Theme theme;
	
	private int played;
	
	
	
	public MusicDTO(String name, Theme theme) {
		this(name, theme, 0);
	}
	
	public MusicDTO(String name, Theme theme, int played) {
		
		Tool.verifyValue("name", name);
		Tool.verifyValue("theme", theme);
		
		this.name = name.replace(Constant.QUOTE, Constant.DOUBLE_QUOTE);
		this.theme = theme;
		this.played = (played < 0) ? 0 : played;
	}
	
	
	
	public String getName() {
		return name;
	}

	public Theme getTheme() {
		return theme;
	}

	public int getPlayed() {
		return played;
	}

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
