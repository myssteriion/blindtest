package com.myssteriion.blindtest.model.game;

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
     * Instantiates a new NewGame.
     */
    public NewGame() {
    }
    
    
    
    /**
     * Gets profiles id.
     *
     * @return the players names
     */
    public Set<Integer> getProfilesId() {
        return profilesId;
    }
    
    
    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Duration getDuration() {
        return duration;
    }
    
    /**
     * Gets themes.
     *
     * @return The themes.
     */
    public List<Theme> getThemes() {
        return themes;
    }
    
    /**
     * Gets effects.
     *
     * @return The effects.
     */
    public List<Effect> getEffects() {
        return effects;
    }
    
    public NewGame setProfilesId(Set<Integer> profilesId) {
        this.profilesId = profilesId;
        return this;
    }
    
    public NewGame setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }
    
    public NewGame setThemes(List<Theme> themes) {
        this.themes = themes;
        return this;
    }
    
    public NewGame setEffects(List<Effect> effects) {
        this.effects = effects;
        return this;
    }
    
    
    @Override
    public String toString() {
        return "profilesId=" + profilesId +
                ", duration=" + duration +
                ", themes=" + themes +
                ", effects=" + effects;
    }
    
}
