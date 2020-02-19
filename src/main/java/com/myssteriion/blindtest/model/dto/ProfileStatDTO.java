package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.tools.converter.DurationConverter;
import com.myssteriion.blindtest.tools.converter.RankConverter;
import com.myssteriion.blindtest.tools.converter.ThemeConverter;
import com.myssteriion.blindtest.tools.converter.ThemeWinModeConverter;
import com.myssteriion.utils.Tools;
import com.myssteriion.utils.model.dto.AbstractDTO;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The ProfileStatDTO.
 */
@Entity
@Table(name = "profile_stat", uniqueConstraints={ @UniqueConstraint(name = "profile_stat__profile_id__unique", columnNames={"profile_id"}) })
public class ProfileStatDTO extends AbstractDTO {

	/**
	 * The DB id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_stat")
	@SequenceGenerator(name = "profile_stat", sequenceName = "profile_stat_sequence", allocationSize = 1)
	@Column(name = "id", nullable = false)
	protected Integer id;

	/**
	 * The profileId.
	 */
	@Column(name = "profile_id", nullable = false)
	private Integer profileId;

	/**
	 * The number of game played.
	 */
	@Column(name = "played_games", nullable = false, length = 500)
	@Convert(converter = DurationConverter.class)
	private Map<Duration, Integer> playedGames;

	/**
	 * The bests scores by durations.
	 */
	@Column(name = "best_scores", nullable = false, length = 500)
	@Convert(converter = DurationConverter.class)
	private Map<Duration, Integer> bestScores;

	/**
	 * The number of game won.
	 */
	@Column(name = "won_games", nullable = false, length = 500)
	@Convert(converter = RankConverter.class)
	private Map<Rank, Integer> wonGames;

	/**
	 * The number of listened musics by themes.
	 */
	@Column(name = "listened_musics", nullable = false, length = 500)
	@Convert(converter = ThemeConverter.class)
	private Map<Theme, Integer> listenedMusics;

	/**
	 * The number of found musics by themes by WinMode.
	 */
	@Column(name = "found_musics", nullable = false, length = 1000)
	@Convert(converter = ThemeWinModeConverter.class)
	private Map< Theme, Map<GoodAnswer, Integer> > foundMusics;



	/**
	 * Instantiates a new Profile stat dto.
	 */
	public ProfileStatDTO() {
		this(null);
	}

	/**
	 * Instantiates a new Profile stat dto.
	 *
	 * @param profileId the profile id
	 */
	public ProfileStatDTO(Integer profileId) {

		this.profileId = profileId;
		this.playedGames = new HashMap<>();
		this.bestScores = new HashMap<>();
		this.wonGames = new HashMap<>();
		this.listenedMusics = new HashMap<>();
		this.foundMusics = new HashMap<>();
	}



	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public ProfileStatDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets profile id.
	 *
	 * @return the profile id
	 */
	public Integer getProfileId() {
		return profileId;
	}

	/**
	 * Sets profile id.
	 *
	 * @param profileId the profile id
	 * @return this
	 */
	public ProfileStatDTO setProfileId(Integer profileId) {
		this.profileId = profileId;
		return this;
	}

	/**
	 * Gets played games.
	 *
	 * @return the played games
	 */
	public Map<Duration, Integer> getPlayedGames() {
		return playedGames;
	}

	/**
	 * Sets played games.
	 *
	 * @param playedGames the played games
	 * @return this
	 */
	public ProfileStatDTO setPlayedGames(Map<Duration, Integer> playedGames) {
		this.playedGames = playedGames;
		return this;
	}

	/**
	 * Gets best scores.
	 *
	 * @return the best scores
	 */
	public Map<Duration, Integer> getBestScores() {
		return bestScores;
	}

	/**
	 * Sets best scores.
	 *
	 * @param bestScores the best scores
	 * @return this
	 */
	public ProfileStatDTO setBestScores(Map<Duration, Integer> bestScores) {
		this.bestScores = bestScores;
		return this;
	}

	/**
	 * Gets wonGames.
	 *
	 * @return the wonGames
	 */
	public Map<Rank, Integer> getWonGames() {
		return wonGames;
	}

	/**
	 * Set wonGames.
	 *
	 * @param wonGames the wonGames
	 * @return this
	 */
	public ProfileStatDTO setWonGames(Map<Rank, Integer> wonGames) {
		this.wonGames = wonGames;
		return this;
	}

	/**
	 * Gets listened musics.
	 *
	 * @return the listened musics
	 */
	public Map<Theme, Integer> getListenedMusics() {
		return listenedMusics;
	}

	/**
	 * Sets listened musics.
	 *
	 * @param listenedMusics the listened musics
	 * @return this
	 */
	public ProfileStatDTO setListenedMusics(Map<Theme, Integer> listenedMusics) {
		this.listenedMusics = listenedMusics;
		return this;
	}

	/**
	 * Gets found musics.
	 *
	 * @return the found musics
	 */
	public Map< Theme, Map<GoodAnswer, Integer> > getFoundMusics() {
		return foundMusics;
	}

	/**
	 * Sets found musics.
	 *
	 * @param foundMusics the found musics
	 * @return this
	 */
	public ProfileStatDTO setFoundMusics(Map< Theme, Map<GoodAnswer, Integer> > foundMusics) {
		this.foundMusics = foundMusics;
		return this;
	}


	/**
	 * Increment playedGames.
	 */
	public void incrementPlayedGames(Duration duration) {

		Tools.verifyValue("duration", duration);

		if (playedGames == null)
			playedGames = new HashMap<>();

		if ( !playedGames.containsKey(duration) )
			playedGames.put(duration, 0);

		playedGames.put(duration, playedGames.get(duration) + 1);
	}

	/**
	 * Add best score if its better.
	 *
	 * @param duration the duration
	 * @param scores   the scores
	 */
	public void addBestScoreIfBetter(Duration duration, int scores) {

		Tools.verifyValue("duration", duration);

		if (bestScores == null)
			bestScores = new HashMap<>();

		if ( !bestScores.containsKey(duration) )
			bestScores.put(duration, 0);

		if ( scores > bestScores.get(duration) )
			bestScores.put(duration, scores);
	}

	/**
	 * Increment wonGames.
	 *
	 * @param rank the rank
	 */
	public void incrementWonGames(Rank rank) {

		Tools.verifyValue("rank", rank);

		if (wonGames == null)
			wonGames = new HashMap<>();

		if ( !wonGames.containsKey(rank) )
			wonGames.put(rank, 0);

		wonGames.put(rank, wonGames.get(rank) + 1);
	}

	/**
	 * Increment listenedMusics.
	 *
	 * @param theme the theme
	 */
	public void incrementListenedMusics(Theme theme) {

		Tools.verifyValue("theme", theme);

		if (listenedMusics == null)
			listenedMusics = new HashMap<>();

		if ( !listenedMusics.containsKey(theme) )
			listenedMusics.put(theme, 0);

		listenedMusics.put(theme, listenedMusics.get(theme) + 1);
	}

	/**
	 * Increment foundMusics.
	 *
	 * @param theme 	the theme
	 * @param goodAnswer 	the winMode
	 */
	public void incrementFoundMusics(Theme theme, GoodAnswer goodAnswer) {

		Tools.verifyValue("theme", theme);
		Tools.verifyValue("winMode", goodAnswer);

		if (foundMusics == null)
			foundMusics = new HashMap<>();

		if ( !foundMusics.containsKey(theme) )
			foundMusics.put(theme, new HashMap<>());

		if ( !foundMusics.get(theme).containsKey(goodAnswer) )
			foundMusics.get(theme).put(goodAnswer, 0);

		foundMusics.get(theme).put(goodAnswer, foundMusics.get(theme).get(goodAnswer) + 1);
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
		return "id=" + id +
				", profileId=" + profileId +
				", playedGames=" + playedGames +
				", bestScores=" + bestScores +
				", wonGames=" + wonGames +
				", listenedMusics=" + listenedMusics +
				", foundMusics=" + foundMusics;
	}
	
}
