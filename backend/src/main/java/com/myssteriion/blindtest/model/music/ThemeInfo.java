package com.myssteriion.blindtest.model.music;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.model.IModel;

import java.util.Objects;

/**
 * Theme info (nb musics by ConnectionMode).
 */
public class ThemeInfo implements IModel {
    
    /**
     * The theme.
     */
    private Theme theme;
    
    /**
     * Nb musics.
     */
    private Integer nbMusics;
    
    
    
    /**
     * Instantiates a new Theme info.
     */
    public ThemeInfo() {
    }
    
    /**
     * Instantiate a new ThemeInfo.
     *
     * @param theme    the theme
     * @param nbMusics the nb musics
     */
    public ThemeInfo(Theme theme, Integer nbMusics) {
        
        this.theme = theme;
        this.nbMusics = nbMusics;
    }
    
    
    
    /**
     * Get theme.
     *
     * @return the theme
     */
    public Theme getTheme() {
        return theme;
    }
    
    /**
     * Set theme.
     *
     * @param theme the theme
     * @return this
     */
    public ThemeInfo setTheme(Theme theme) {
        this.theme = theme;
        return this;
    }
    
    /**
     * Get offlineNbMusics.
     *
     * @return the offlineNbMusics
     */
    public Integer getNbMusics() {
        return nbMusics;
    }
    
    /**
     * Set offlineNbMusics.
     *
     * @param nbMusics the offlineNbMusics
     * @return this
     */
    public ThemeInfo setNbMusics(Integer nbMusics) {
        this.nbMusics = nbMusics;
        return this;
    }
    
    
    @Override
    public boolean equals(Object o) {
        
        if (this == o)
            return true;
        
        if (o == null || getClass() != o.getClass())
            return false;
        
        ThemeInfo themeInfo = (ThemeInfo) o;
        return theme == themeInfo.theme && Objects.equals(nbMusics, themeInfo.nbMusics);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(theme, nbMusics);
    }
    
    @Override
    public String toString() {
        return "theme=" + theme +
                ", offlineNbMusics=" + nbMusics;
    }
    
}
