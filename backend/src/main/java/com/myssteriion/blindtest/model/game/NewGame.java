package com.myssteriion.blindtest.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonUtils;

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
     *
     * @param profilesId     the profiles id
     * @param duration       the duration
     * @param themes         the themes
     * @param connectionMode the connectionMode mode
     */
    @JsonCreator
    public NewGame(Set<Integer> profilesId, Duration duration, List<Theme> themes, List<Effect> effects, ConnectionMode connectionMode) {
        
        CommonUtils.verifyValue("profilesId", profilesId);
        CommonUtils.verifyValue("duration", duration);
        CommonUtils.verifyValue("gameMode", connectionMode);
        
        this.profilesId = profilesId;
        this.duration = duration;
        this.themes = CommonUtils.isNullOrEmpty(themes) ? Theme.getSortedTheme() : CommonUtils.removeDuplicate(themes);
        this.effects = CommonUtils.isNullOrEmpty(effects) ? Effect.getSortedEffect() : CommonUtils.removeDuplicate(effects);
        this.connectionMode = connectionMode;
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
    
    /**
     * Gets game mode.
     *
     * @return the game mode
     */
    public ConnectionMode getConnectionMode() {
        return connectionMode;
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
