package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Tool;
import com.myssteriion.blindtest.tools.converter.DurationConverter;
import com.myssteriion.blindtest.tools.converter.RankConverter;
import com.myssteriion.blindtest.tools.converter.ThemeConverter;

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
@Table(name = "profile_stat", uniqueConstraints={ @UniqueConstraint(name = "profile_id_unique", columnNames={"profile_id"}) })
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
	@Column(name = "played_games", nullable = false)
	private int playedGames;

	/**
	 * The number of game won.
	 */
	@Column(name = "won_games", nullable = false)
	@Convert(converter = RankConverter.class)
	private Map<Rank, Integer> wonGames;

	/**
	 * The number of listened musics by themes.
	 */
	@Column(name = "listened_musics", nullable = false)
	@Convert(converter = ThemeConverter.class)
	private Map<Theme, Integer> listenedMusics;

	/**
	 * The number of found musics by themes.
	 */
	@Column(name = "found_musics", nullable = false)
	@Convert(converter = ThemeConverter.class)
	private Map<Theme, Integer> foundMusics;

	/**
	 * The bests scores by durations.
	 */
	@Column(name = "best_scores", nullable = false)
	@Convert(converter = DurationConverter.class)
	private Map<Duration, Integer> bestScores;



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
		this.playedGames = 0;
		this.wonGames = new HashMap<>();
		this.listenedMusics = new HashMap<>();
		this.foundMusics = new HashMap<>();
		this.bestScores = new HashMap<>();
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
	public int getPlayedGames() {
		return playedGames;
	}

	/**
	 * Sets played games.
	 *
	 * @param playedGames the played games
	 * @return this
	 */
	public ProfileStatDTO setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
		return this;
	}

	/**
	 * Gets wonGames.
	 *
	 * @return The wonGames
	 */
	public Map<Rank, Integer> getWonGames() {
		return wonGames;
	}

	/**
	 * Set wonGames.
	 *
	 * @param wonGames this
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
	public Map<Theme, Integer> getFoundMusics() {
		return foundMusics;
	}

	/**
	 * Sets found musics.
	 *
	 * @param foundMusics the found musics
	 * @return this
	 */
	public ProfileStatDTO setFoundMusics(Map<Theme, Integer> foundMusics) {
		this.foundMusics = foundMusics;
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
	 * Increment playedGames.
	 */
	public void incrementPlayedGames() {
		this.playedGames++;
	}

	/**
	 * Increment wonGames.
	 *
	 * @param rank the rank
	 */
	public void incrementWonGames(Rank rank) {

		Tool.verifyValue("rank", rank);

		if (wonGames == null)
			wonGames = new HashMap<>();

		if ( !wonGames.containsKey(rank) )
			wonGames.put(rank, 0);

		wonGames.put(rank, listenedMusics.get(rank) + 1);
	}

	/**
	 * Increment listenedMusics.
	 *
	 * @param theme the theme
	 */
	public void incrementListenedMusics(Theme theme) {

		Tool.verifyValue("theme", theme);

		if (listenedMusics == null)
			listenedMusics = new HashMap<>();

		if ( !listenedMusics.containsKey(theme) )
			listenedMusics.put(theme, 0);

		listenedMusics.put(theme, listenedMusics.get(theme) + 1);
	}

	/**
	 * Increment foundMusics.
	 *
	 * @param theme the theme
	 */
	public void incrementFoundMusics(Theme theme) {

		Tool.verifyValue("theme", theme);

		if (foundMusics == null)
			foundMusics = new HashMap<>();

		if ( !foundMusics.containsKey(theme) )
			foundMusics.put(theme, 0);

		foundMusics.put(theme, foundMusics.get(theme) + 1);
	}

	/**
	 * Add best score if its better.
	 *
	 * @param duration the duration
	 * @param scores   the scores
	 */
	public void addBestScoreIfBetter(Duration duration, int scores) {

		Tool.verifyValue("duration", duration);

		if (bestScores == null)
			bestScores = new HashMap<>();

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
		return "id=" + id +
				", profileId=" + profileId +
				", playedGames=" + playedGames +
				", wonGames=" + wonGames +
				", listenedMusics=" + listenedMusics +
				", foundMusics=" + foundMusics +
				", bestScores=" + bestScores;
	}
	
}
