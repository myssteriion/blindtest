package com.myssteriion.blindtest.model.dto;

import java.util.Objects;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Tool;

public class ProfilStatDTO extends AbstractDTO {

	private Integer profilId;
	
	private int playedGames;
	
	private int listenedMusics;
	
	private int foundMusics;
	
	
	
	public ProfilStatDTO(Integer profilId) {
		this(profilId, 0, 0, 0);
	}
	
	public ProfilStatDTO(Integer profilId, int playedGames, int listenedMusics, int foundMusics) {
		
		Tool.verifyValue("profilId", profilId);
		
		this.profilId = profilId;
		this.playedGames = (playedGames < 0) ? 0 : playedGames;
		this.listenedMusics = (listenedMusics < 0) ? 0 : listenedMusics;
		this.foundMusics = (foundMusics < 0) ? 0 : foundMusics;
	}
	
	
	
	public Integer getProfilId() {
		return profilId;
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
		return Objects.hash(profilId);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		ProfilStatDTO other = (ProfilStatDTO) obj;
		return Objects.equals(this.profilId, other.profilId);
	}

	@Override
	public String toString() {
		return super.toString() + 
				", profilId=" + profilId +
				", playedGames=" + playedGames +
				", listenedMusics=" + listenedMusics +
				", foundMusics=" + foundMusics;
	}
	
}
