package com.myssteriion.blindtest.model.entity;

import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.persistence.converter.ThemeConverter;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.model.entity.AbstractEntity;
import com.myssteriion.utils.model.entity.impl.Flux;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * The MusicDTO.
 */
@Entity
@Table(name = "music",
        uniqueConstraints = {
                @UniqueConstraint( name = "music__name__unique", columnNames = {"name", "theme" } )
        })
@SequenceGenerator(name = "sequence_id", sequenceName = "music_sequence", allocationSize = 1)
public class MusicEntity extends AbstractEntity<MusicEntity> {
    
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
    @ColumnDefault(CommonConstant.EMPTY_JSON_WITH_QUOTE)
    @Convert(converter = ThemeConverter.class)
    private Theme theme;
    
    /**
     * The number of played.
     */
    @Column(name = "played", nullable = false)
    @ColumnDefault("0")
    @PositiveOrZero(message = "Played can't be negative.")
    private int played;
    
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
     * Instantiates a new music.
     */
    public MusicEntity() {
    }
    
    /**
     * Instantiates a new music.
     *
     * @param name              the name
     * @param theme             the theme
     */
    public MusicEntity(String name, Theme theme) {
        this.name = name;
        this.theme = theme;
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
    public MusicEntity setName(String name) {
        this.name = name;
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
    public MusicEntity setTheme(Theme theme) {
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
    public MusicEntity setPlayed(int played) {
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
     * @return this flux
     */
    public MusicEntity setFlux(Flux flux) {
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
    public MusicEntity setEffect(Effect effect) {
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
        return Objects.hash(name, theme);
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (this == obj)
            return true;
        
        if(obj == null || obj.getClass()!= this.getClass())
            return false;
        
        MusicEntity other = (MusicEntity) obj;
        return Objects.equals(this.name, other.name) &&
                Objects.equals(this.theme, other.theme);
    }
    
    @Override
    public String toString() {
        return super.toString() +
                ", name=" + name +
                ", theme=" + theme +
                ", played=" + played +
                ", flux={" + flux + "}" +
                ", effect=" + effect;
    }
    
}
