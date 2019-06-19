package com.myssteriion.blindtest.model.dto;

import java.util.Objects;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Tool;

public class ProfilDTO extends AbstractDTO {

	private String name;
	
	private String avatar;
	
	private int playedGames;
	
	private int listenedMusics;
	
	private int foundMusics;

	
	
	public ProfilDTO(String name, String avatar) {
		this(name, avatar, 0, 0, 0);
	}

	public ProfilDTO(String name, String avatar, int playedGames, int listenedMusics, int foundMusics) {

		Tool.verifyValue("name", name);
		Tool.verifyValue("avatar", avatar);
		
		this.name = name.trim();
		this.avatar = avatar;
		
		this.playedGames = (playedGames < 0) ? 0 : playedGames;
		this.listenedMusics = (listenedMusics < 0) ? 0 : listenedMusics;
		this.foundMusics = (foundMusics < 0) ? 0 : foundMusics;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		
		Tool.verifyValue("name", name);
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		
		Tool.verifyValue("avatar", avatar);
		this.avatar = avatar;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public void incrementPlayedGames() {
		this.playedGames++;
	}
	
	public int getListenedMusics() {
		return listenedMusics;
	}

	public void incrementListenedMusics() {
		this.listenedMusics++;
	}
	
	public int getFoundMusics() {
		return foundMusics;
	}
	
	public void incrementFoundMusics() {
		this.foundMusics++;
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
		
		ProfilDTO other = (ProfilDTO) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public String toString() {
		return super.toString() + 
				", name=" + name +
				", avatar=" + avatar +
				", playedGames=" + playedGames +
				", listenedMusics=" + listenedMusics +
				", foundMusics=" + foundMusics;
	}
	
}
