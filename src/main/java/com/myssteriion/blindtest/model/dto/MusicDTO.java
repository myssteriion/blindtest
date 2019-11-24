package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.spotify.dto.SpotifyMusic;
import com.myssteriion.blindtest.tools.Tool;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * The MusicDTO.
 */
@Entity
@Table(name = "music", uniqueConstraints={ @UniqueConstraint(name = "name_theme_spotify_track_id_unique", columnNames={"name", "theme", "spotify_track_id"}) })
public class MusicDTO extends AbstractDTO {

	/**
	 * The DB id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "music_sequence")
	@SequenceGenerator(name = "music_sequence", sequenceName = "music_sequence", allocationSize = 1)
	@Column(name = "id", nullable = false)
	protected Integer id;

	/**
	 * The name.
	 */
	@Column(name = "name", nullable = false)
	@NotEmpty(message = "Name can't be empty.")
	private String name;

	/**
	 * The theme.
	 */
	@Column(name = "theme", nullable = false)
	private Theme theme;

	/**
	 * The number of played.
	 */
	@Column(name = "played", nullable = false)
	private int played;

	/**
	 * The connection mode.
	 */
	@Column(name = "connection_mode", nullable = false)
	private ConnectionMode connectionMode;

	/**
	 * The spotify track id.
	 */
	@Column(name = "spotify_track_id")
	private String spotifyTrackId;

	/**
	 * The spotify track id.
	 */
	@Column(name = "spotify_preview_url")
	private String spotifyPreviewUrl;

	/**
	 * The spotify track id.
	 */
	@Column(name = "spotify_track_url")
	private String spotifyTrackUrl;

	/**
	 * Audio flux.
	 */
	@Transient
	private Flux flux;

	/**
	 * The effect.
	 */
	@Transient
	private Effect effect;


	/**
	 * Instantiates a new Music dto.
	 */
	public MusicDTO() {
		this("", null);
	}

	/**
	 * Instantiates a new Music dto.
	 *
	 * @param name  the name
	 * @param theme the theme
	 */
	public MusicDTO(String name, Theme theme) {
		this(name, theme, 0);
	}

	/**
	 * Instantiates a new Music dto.
	 *
	 * @param name   the name
	 * @param theme  the theme
	 * @param played the played
	 */
	public MusicDTO(String name, Theme theme, int played) {

		this.name = Tool.isNullOrEmpty(name) ? "" : name;
		this.theme = theme;
		this.played = Math.max(played, 0);
		this.connectionMode = ConnectionMode.OFFLINE;
	}

	/**
	 * Instantiates a new Music dto.
	 *
	 * @param spotifyMusic the spotifyMusic
	 * @param theme        the theme
	 */
	public MusicDTO(SpotifyMusic spotifyMusic, Theme theme) {
		this(spotifyMusic, theme, 0);
	}

	/**
	 * Instantiates a new Music dto.
	 *
	 * @param spotifyMusic the spotifyMusic
	 * @param theme        the theme
	 * @param played       the played
	 */
	public MusicDTO(SpotifyMusic spotifyMusic, Theme theme, int played) {

		Tool.verifyValue("spotifyMusic", spotifyMusic);
		Tool.verifyValue("theme", theme);

		this.name = spotifyMusic.getArtists() + " - " + spotifyMusic.getName();
		this.theme = theme;
		this.played = Math.max(played, 0);
		this.connectionMode = ConnectionMode.ONLINE;
		this.spotifyTrackId = spotifyMusic.getTrackId();
		this.spotifyPreviewUrl = spotifyMusic.getPreviewUrl();
		this.spotifyTrackUrl = spotifyMusic.getTrackUrl();
	}


	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public MusicDTO setId(Integer id) {
		this.id = id;
		return this;
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
	 * Sets name.
	 *
	 * @param name the name
	 * @return this name
	 */
	public MusicDTO setName(String name) {
		this.name = Tool.isNullOrEmpty(name) ? "" : name;
		return this;
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
	 * Sets theme.
	 *
	 * @param theme the theme
	 * @return this theme
	 */
	public MusicDTO setTheme(Theme theme) {
		this.theme = theme;
		return this;
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
	 * Sets played.
	 *
	 * @param played the played
	 * @return this played
	 */
	public MusicDTO setPlayed(int played) {
		this.played = played;
		return this;
	}

	/**
	 * Gets the game mode.
	 *
	 * @return the game mode
	 */
	public ConnectionMode getConnectionMode() {
		return connectionMode;
	}

	/**
	 * Sets the game mode.
	 *
	 * @param connectionMode the game mode
	 * @return this
	 */
	public MusicDTO setConnectionMode(ConnectionMode connectionMode) {
		this.connectionMode = connectionMode;
		return this;
	}

	/**
	 * Gets spotify track id.
	 *
	 * @return the spotify track id
	 */
	public String getSpotifyTrackId() {
		return spotifyTrackId;
	}

	/**
	 * Sets spotify track id.
	 *
	 * @param spotifyTrackId the spotify track id
	 * @return this spotify track id
	 */
	public MusicDTO setSpotifyTrackId(String spotifyTrackId) {
		this.spotifyTrackId = spotifyTrackId;
		return this;
	}

	/**
	 * Gets spotify preview url.
	 *
	 * @return the spotify preview url
	 */
	public String getSpotifyPreviewUrl() {
		return spotifyPreviewUrl;
	}

	/**
	 * Sets spotify preview url.
	 *
	 * @param spotifyPreviewUrl the spotify preview url
	 * @return this spotify preview url
	 */
	public MusicDTO setSpotifyPreviewUrl(String spotifyPreviewUrl) {
		this.spotifyPreviewUrl = spotifyPreviewUrl;
		return this;
	}

	/**
	 * Gets spotify track url.
	 *
	 * @return the spotify track url
	 */
	public String getSpotifyTrackUrl() {
		return spotifyTrackUrl;
	}

	/**
	 * Sets spotify track url.
	 *
	 * @param spotifyTrackUrl the spotify track url
	 * @return this spotify track url
	 */
	public MusicDTO setSpotifyTrackUrl(String spotifyTrackUrl) {
		this.spotifyTrackUrl = spotifyTrackUrl;
		return this;
	}

	/**
	 * Gets flux.
	 *
	 * @return The flux.
	 */
	public Flux getFlux() {
		return flux;
	}

	/**
	 * Set flux.
	 *
	 * @param flux The flux.
	 * @return this flux
	 */
	public MusicDTO setFlux(Flux flux) {
		this.flux = flux;
		return this;
	}

	/**
	 * Gets effect.
	 *
	 * @return The effect.
	 */
	public Effect getEffect() {
		return effect;
	}

	/**
	 * Set effect.
	 *
	 * @param effect The effect.
	 * @return this effect
	 */
	public MusicDTO setEffect(Effect effect) {
		this.effect = effect;
		return this;
	}

	/**
	 * Increment played.
	 */
	public void incrementPlayed() {
		this.played++;
	}


	@Override
	public int hashCode() {
		return Objects.hash(name, theme, connectionMode);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		MusicDTO other = (MusicDTO) obj;
		return Objects.equals(this.name, other.name) && 
				Objects.equals(this.theme, other.theme) &&
				Objects.equals(this.connectionMode, other.connectionMode);
	}

	@Override
	public String toString() {
		return "id=" + id +
				", name=" + name +
				", theme=" + theme +
				", played=" + played +
				", connectionMode=" + connectionMode +
				", spotifyTrackId=" + spotifyTrackId +
				", spotifyPreviewUrl=" + spotifyPreviewUrl +
				", spotifyTrackUrl=" + spotifyTrackUrl +
				", flux={" + flux + "}" +
				", effect=" + effect;
	}

}
