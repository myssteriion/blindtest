package com.myssteriion.blindtest.persistence.dao;

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
     * Find by name and theme.
     *
     * @param name  the name
     * @param theme the theme
     * @return the optional
     */
    Optional<MusicEntity> findByNameAndTheme(String name, Theme theme);
    
    /**
     * Find by themes.
     *
     * @param themes the themes
     * @return the musics list
     */
    List<MusicEntity> findByThemeIn(List<Theme> themes);
    
    /**
     * Count by theme.
     *
     * @param theme the theme
     * @return the number of music
     */
    int countByTheme(Theme theme);
    
}
