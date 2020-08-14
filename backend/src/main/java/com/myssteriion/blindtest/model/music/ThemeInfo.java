package com.myssteriion.blindtest.model.music;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonUtils;
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
     * Instantiate a new ThemeInfo.
     *
     * @param theme           the theme
     * @param offlineNbMusics offline nb musics
     * @param onlineNbMusics  online nb musics
     */
    public ThemeInfo(Theme theme, Integer offlineNbMusics, Integer onlineNbMusics) {
        
        CommonUtils.verifyValue("theme", theme);
        
        this.theme = theme;
        this.offlineNbMusics = Math.max(offlineNbMusics, 0);
        this.onlineNbMusics = Math.max(onlineNbMusics, 0);
    }
    
    
    
    /**
     * Gets theme.
     *
     * @return The theme.
     */
    public Theme getTheme() {
        return theme;
    }
    
    /**
     * Gets offlineNbMusics.
     *
     * @return The offlineNbMusics.
     */
    public Integer getOfflineNbMusics() {
        return offlineNbMusics;
    }
    
    /**
     * Gets onlineNbMusics.
     *
     * @return The onlineNbMusics.
     */
    public Integer getOnlineNbMusics() {
        return onlineNbMusics;
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
