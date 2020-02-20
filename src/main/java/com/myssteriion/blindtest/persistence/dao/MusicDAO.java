package com.myssteriion.blindtest.persistence.dao;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.utils.persistence.dao.IDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO for MusicDTO.
 */
@Repository
public interface MusicDAO extends IDAO<MusicDTO> {

    /**
     * Find by name and theme.
     *
     * @param name           the name
     * @param theme          the theme
     * @param connectionMode the connection mode
     * @return the optional
     */
    Optional<MusicDTO> findByNameAndThemeAndConnectionMode(String name, Theme theme, ConnectionMode connectionMode);

    /**
     * Find by themes.
     *
     * @param themes          the themes
     * @param connectionModes the connection modes
     * @return the musics list
     */
    List<MusicDTO> findByThemeInAndConnectionModeIn(List<Theme> themes, List<ConnectionMode> connectionModes);

    /**
     * Count by theme and connection mode
     *
     * @param theme          the theme
     * @param connectionMode the connection mode
     * @return the number of music
     */
    int countByThemeAndConnectionMode(Theme theme, ConnectionMode connectionMode);

}
