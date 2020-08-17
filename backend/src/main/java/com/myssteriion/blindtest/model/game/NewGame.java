package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;

import java.util.List;
import java.util.Set;

/**
 * Represents a new game (for create a game).
 */
public class NewGame {
    
    /**
     * The profiles id.
     */
    private Set<Integer> profilesId;
    
    /**
     * The duration.
     */
    private Duration duration;
    
    /**
     * The themes.
     */
    private List<Theme> themes;
    
    /**
     * The effects.
     */
    private List<Effect> effects;
    
    /**
     * The connection mode.
     */
    private ConnectionMode connectionMode;
    
    
    
    /**
     * Instantiates a new New game.
     */
    public NewGame() {
    }
    
    
    
    /**
     * Get profilesId.
     *
     * @return the profilesId
     */
    public Set<Integer> getProfilesId() {
        return profilesId;
    }
    
    /**
     * Set profilesId.
     *
     * @param profilesId the profilesId
     * @return this
     */
    public NewGame setProfilesId(Set<Integer> profilesId) {
        this.profilesId = profilesId;
        return this;
    }
    
    /**
     * Get duration.
     *
     * @return the duration
     */
    public Duration getDuration() {
        return duration;
    }
    
    /**
     * Set duration.
     *
     * @param duration the duration
     * @return this
     */
    public NewGame setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }
    
    /**
     * Get themes.
     *
     * @return the themes
     */
    public List<Theme> getThemes() {
        return themes;
    }
    
    /**
     * Set themes.
     *
     * @param themes the themes
     * @return this
     */
    public NewGame setThemes(List<Theme> themes) {
        this.themes = themes;
        return this;
    }
    
    /**
     * Get effects.
     *
     * @return the effects
     */
    public List<Effect> getEffects() {
        return effects;
    }
    
    /**
     * Set effects.
     *
     * @param effects the effects
     * @return this
     */
    public NewGame setEffects(List<Effect> effects) {
        this.effects = effects;
        return this;
    }
    
    /**
     * Get connectionMode.
     *
     * @return the connectionMode
     */
    public ConnectionMode getConnectionMode() {
        return connectionMode;
    }
    
    /**
     * Set connectionMode.
     *
     * @param connectionMode the connectionMode
     * @return this
     */
    public NewGame setConnectionMode(ConnectionMode connectionMode) {
        this.connectionMode = connectionMode;
        return this;
    }
    
    
    @Override
    public String toString() {
        return "profilesId=" + profilesId +
                ", duration=" + duration +
                ", themes=" + themes +
                ", effects=" + effects +
                ", connectionMode=" + connectionMode;
    }
    
}
