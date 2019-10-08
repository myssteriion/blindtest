package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
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
@Table(name = "music", uniqueConstraints={ @UniqueConstraint(name = "name_theme_unique", columnNames={"name", "theme"}) })
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
	 * Audio flux.
	 */
	@Transient
	private Flux flux;




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
	 * @param name  the name
	 * @param theme the theme
	 * @param name  the played
	 */
	public MusicDTO(String name, Theme theme, int played) {

		this.name = Tool.isNullOrEmpty(name) ? "" : name;
		this.theme = theme;
		this.played = Math.max(played, 0);
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
	 * @return this
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
	 * @return this
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
	 * @return this
	 */
	public MusicDTO setPlayed(int played) {
		this.played = played;
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
	 */
	public MusicDTO setFlux(Flux flux) {
		this.flux = flux;
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
		return "id=" + id +
				", name=" + name +
				", theme=" + theme +
				", played=" + played +
				", flux={" + flux + "}";
	}

}
