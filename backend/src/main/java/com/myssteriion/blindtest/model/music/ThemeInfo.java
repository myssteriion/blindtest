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
     * Nb musics for OFFLINE mode.
     */
    private Integer offlineNbMusics;
    
    /**
     * Nb musics for ONLINE mode.
     */
    private Integer onlineNbMusics;
    
    
    
    /**
     * Instantiates a new Theme info.
     */
    public ThemeInfo() {
    }
    
    /**
     * Instantiate a new ThemeInfo.
     *
     * @param theme           the theme
     * @param offlineNbMusics offline nb musics
     * @param onlineNbMusics  online nb musics
     */
    public ThemeInfo(Theme theme, Integer offlineNbMusics, Integer onlineNbMusics) {
        
        this.theme = theme;
        this.offlineNbMusics = offlineNbMusics;
        this.onlineNbMusics = onlineNbMusics;
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
    public Integer getOfflineNbMusics() {
        return offlineNbMusics;
    }
    
    /**
     * Set offlineNbMusics.
     *
     * @param offlineNbMusics the offlineNbMusics
     * @return this
     */
    public ThemeInfo setOfflineNbMusics(Integer offlineNbMusics) {
        this.offlineNbMusics = offlineNbMusics;
        return this;
    }
    
    /**
     * Get onlineNbMusics.
     *
     * @return the onlineNbMusics
     */
    public Integer getOnlineNbMusics() {
        return onlineNbMusics;
    }
    
    /**
     * Set onlineNbMusics.
     *
     * @param onlineNbMusics the onlineNbMusics
     * @return this
     */
    public ThemeInfo setOnlineNbMusics(Integer onlineNbMusics) {
        this.onlineNbMusics = onlineNbMusics;
        return this;
    }
    
    
    @Override
    public boolean equals(Object o) {
        
        if (this == o)
            return true;
        
        if (o == null || getClass() != o.getClass())
            return false;
        
        ThemeInfo themeInfo = (ThemeInfo) o;
        return theme == themeInfo.theme && Objects.equals(offlineNbMusics, themeInfo.offlineNbMusics) && Objects.equals(onlineNbMusics, themeInfo.onlineNbMusics);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(theme, offlineNbMusics, onlineNbMusics);
    }
    
    @Override
    public String toString() {
        return "theme=" + theme +
                ", offlineNbMusics=" + offlineNbMusics +
                ", onlineNbMusics=" + onlineNbMusics;
    }
    
}
