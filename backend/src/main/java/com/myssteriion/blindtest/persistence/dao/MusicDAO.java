package com.myssteriion.blindtest.persistence.dao;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.utils.persistence.dao.IDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO for music.
 */
@Repository
public interface MusicDAO extends IDAO<MusicEntity> {
    
    /**
     * Find by name, theme and connection mode.
     *
     * @param name           the name
     * @param theme          the theme
     * @param connectionMode the connection mode
     * @return the optional
     */
    Optional<MusicEntity> findByNameAndThemeAndConnectionMode(String name, Theme theme, ConnectionMode connectionMode);
    
    /**
     * Find by themes and connection modes.
     *
     * @param themes          the themes
     * @param connectionModes the connection modes
     * @return the musics list
     */
    List<MusicEntity> findByThemeInAndConnectionModeIn(List<Theme> themes, List<ConnectionMode> connectionModes);
    
    /**
     * Count by theme and connection mode.
     *
     * @param theme          the theme
     * @param connectionMode the connection mode
     * @return the number of music
     */
    int countByThemeAndConnectionMode(Theme theme, ConnectionMode connectionMode);
    
}
