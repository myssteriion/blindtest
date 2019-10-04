package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The ProfileStatDTO.
 */
public class ProfileStatDTO extends AbstractDTO {

	/**
	 * The profileId.
	 */
	private Integer profileId;

	/**
	 * The number of game played.
	 */
	private int playedGames;

	/**
	 * The number of listened musics by themes.
	 */
	private Map<Theme, Integer> listenedMusics;

	/**
	 * The number of found musics by themes.
	 */
	private Map<Theme, Integer> foundMusics;

	/**
	 * The bests scores by durations.
	 */
	private Map<Duration, Integer> bestScores;


	/**
	 * Instantiates a new ProfileStatDTO.
	 *
	 * @param profileId the profileId
	 */
	public ProfileStatDTO(Integer profileId) {
		this(profileId, 0, new HashMap<>(), new HashMap<>(), new HashMap<>());
	}

	/**
	 * Instantiates a new ProfileStatDTO.
	 *
	 * @param profileId      the profileId
	 * @param playedGames    the playedGames
	 * @param listenedMusics the listenedMusics
	 * @param foundMusics    the foundMusics
	 * @param bestScores     the bestScores
	 */
	public ProfileStatDTO(Integer profileId, int playedGames, Map<Theme, Integer> listenedMusics, Map<Theme, Integer> foundMusics, Map<Duration, Integer> bestScores) {
		
		Tool.verifyValue("profileId", profileId);
		
		this.profileId = profileId;
		this.playedGames = Math.max(playedGames, 0);
		this.listenedMusics = (listenedMusics == null) ? new HashMap<>() : listenedMusics;
		this.foundMusics = (foundMusics == null) ? new HashMap<>() : foundMusics;
		this.bestScores = (bestScores == null) ? new HashMap<>() : bestScores;
	}


	/**
	 * Gets profileId.
	 *
	 * @return the profileId
	 */
	public Integer getProfileId() {
		return profileId;
	}

	/**
	 * Gets playedGames.
	 *
	 * @return the playedGames
	 */
	public int getPlayedGames() {
		return playedGames;
	}

	/**
	 * Increment playedGames.
	 */
	public void incrementPlayedGames() {
		this.playedGames++;
	}

	/**
	 * Gets listenedMusics.
	 *
	 * @return the listenedMusics
	 */
	public Map<Theme, Integer> getListenedMusics() {
		return listenedMusics;
	}

	/**
	 * Increment listenedMusics.
	 *
	 * @param theme the theme
	 */
	public void incrementListenedMusics(Theme theme) {

		Tool.verifyValue("theme", theme);

		if ( !listenedMusics.containsKey(theme) )
			listenedMusics.put(theme, 0);

		listenedMusics.put(theme, listenedMusics.get(theme) + 1);
	}

	/**
	 * Gets foundMusics.
	 *
	 * @return the foundMusics
	 */
	public Map<Theme, Integer> getFoundMusics() {
		return foundMusics;
	}

	/**
	 * Increment foundMusics.
	 *
	 * @param theme the theme
	 */
	public void incrementFoundMusics(Theme theme) {

		Tool.verifyValue("theme", theme);

		if ( !foundMusics.containsKey(theme) )
			foundMusics.put(theme, 0);

		foundMusics.put(theme, foundMusics.get(theme) + 1);
	}

	/**
	 * Gets bestScores.
	 *
	 * @return the bestScores
	 */
	public Map<Duration, Integer> getBestScores() {
		return bestScores;
	}

	/**
	 * Add best score if its better.
	 *
	 * @param duration the duration
	 * @param scores   the scores
	 */
	public void addBestScoreIfBetter(Duration duration, int scores) {

		Tool.verifyValue("duration", duration);

		if ( !bestScores.containsKey(duration) )
			bestScores.put(duration, 0);

		if ( scores > bestScores.get(duration) )
			bestScores.put(duration, scores);
	}


	@Override
	public int hashCode() {
		return Objects.hash(profileId);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		ProfileStatDTO other = (ProfileStatDTO) obj;
		return Objects.equals(this.profileId, other.profileId);
	}

	@Override
	public String toString() {
		return super.toString() + 
				", profileId=" + profileId +
				", playedGames=" + playedGames +
				", listenedMusics=" + listenedMusics +
				", foundMusics=" + foundMusics +
				", bestScores=" + bestScores;
	}
	
}
