package com.myssteriion.blindtest.model.music;

import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.model.IModel;

import java.util.List;

/**
 * The MusicFilter.
 */
public class MusicFilter implements IModel {
    
    /**
     * The themes.
     */
    private List<Theme> themes;
    
    /**
     * The effects
     */
    private List<Effect> effects;
    
    
    
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
    public MusicFilter setThemes(List<Theme> themes) {
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
    public MusicFilter setEffects(List<Effect> effects) {
        this.effects = effects;
        return this;
    }
    
    
    @Override
    public String toString() {
        return "MusicFilter{" +
                "themes=" + themes +
                ", effects=" + effects +
                '}';
    }
    
}
