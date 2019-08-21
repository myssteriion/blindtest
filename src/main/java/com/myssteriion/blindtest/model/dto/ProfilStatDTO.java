package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfilStatDTO extends AbstractDTO {

	private Integer profilId;
	
	private int playedGames;
	
	private Map<Theme, Integer> listenedMusics;
	
	private Map<Theme, Integer> foundMusics;

	private Map<Duration, Integer> bestScores;

	
	
	public ProfilStatDTO(Integer profilId) {
		this(profilId, 0, new HashMap<>(), new HashMap<>(), new HashMap<>());
	}
	
	public ProfilStatDTO(Integer profilId, int playedGames, Map<Theme, Integer> listenedMusics, Map<Theme, Integer> foundMusics, Map<Duration, Integer> bestScores) {
		
		Tool.verifyValue("profilId", profilId);
		
		this.profilId = profilId;
		this.playedGames = (playedGames < 0) ? 0 : playedGames;
		this.listenedMusics = (listenedMusics == null) ? new HashMap<>() : listenedMusics;
		this.foundMusics = (foundMusics == null) ? new HashMap<>() : foundMusics;
		this.bestScores = (bestScores == null) ? new HashMap<>() : bestScores;
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
	
	public Map<Theme, Integer> getListenedMusics() {
		return listenedMusics;
	}

	public void incrementListenedMusics(Theme theme) {

		Tool.verifyValue("theme", theme);

		if ( !listenedMusics.containsKey(theme) )
			listenedMusics.put(theme, 0);

		listenedMusics.put(theme, listenedMusics.get(theme) + 1);
	}
	
	public Map<Theme, Integer> getFoundMusics() {
		return foundMusics;
	}
	
	public void incrementFoundMusics(Theme theme) {

		Tool.verifyValue("theme", theme);

		if ( !foundMusics.containsKey(theme) )
			foundMusics.put(theme, 0);

		foundMusics.put(theme, foundMusics.get(theme) + 1);
	}

	public Map<Duration, Integer> getBestScores() {
		return bestScores;
	}

	public void addBestScoreIfBetter(Duration duration, int scores) {

		Tool.verifyValue("duration", duration);

		if ( !bestScores.containsKey(duration) )
			bestScores.put(duration, 0);

		if ( scores > bestScores.get(duration) )
			bestScores.put(duration, scores);
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
				", foundMusics=" + foundMusics +
				", bestScores=" + bestScores;
	}
	
}
