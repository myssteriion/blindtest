package com.myssteriion.blindtest.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;
import java.util.Set;

/**
 * Represents a new game (for create a game).
 */
public class NewGame {

    /**
     * The players names list.
     */
    private Set<String> playersNames;

    /**
     * The duration.
     */
    private Duration duration;

    /**
     * The themes.
     */
    private List<Theme> themes;

    /**
     * If online mode is active.
     */
    private boolean onlineMode;



    /**
     * Instantiates a new New game.
     *
     * @param playersNames the players names
     * @param duration     the duration
     * @param themes       the themes
     * @param onlineMode   the online mode
     */
    @JsonCreator
    public NewGame(Set<String> playersNames, Duration duration, List<Theme> themes, boolean onlineMode) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);

        this.playersNames = playersNames;
        this.duration = duration;
        this.themes = Tool.isNullOrEmpty(themes) ? Theme.getSortedTheme() : themes;
        this.onlineMode = onlineMode;
    }



    /**
     * Gets players names.
     *
     * @return the players names
     */
    public Set<String> getPlayersNames() {
        return playersNames;
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
     * Is online mode.
     *
     * @return the online mode
     */
    public boolean isOnlineMode() {
        return onlineMode;
    }


    @Override
    public String toString() {
        return "playersNames=" + playersNames +
                ", duration=" + duration +
                ", themes=" + themes +
                ", onlineMode=" + onlineMode;
    }

}
