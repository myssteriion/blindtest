package com.myssteriion.blindtest.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.GameMode;
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
     * Game mode.
     */
    private GameMode gameMode;



    /**
     * Instantiates a new New game.
     *
     * @param playersNames the players names
     * @param duration     the duration
     * @param themes       the themes
     * @param gameMode     the game mode
     */
    @JsonCreator
    public NewGame(Set<String> playersNames, Duration duration, List<Theme> themes, GameMode gameMode) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);
        Tool.verifyValue("gameMode", gameMode);

        this.playersNames = playersNames;
        this.duration = duration;
        this.themes = Tool.isNullOrEmpty(themes) ? Theme.getSortedTheme() : themes;
        this.gameMode = gameMode;
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
     * Gets game mode.
     *
     * @return the game mode
     */
    public GameMode getGameMode() {
        return gameMode;
    }


    @Override
    public String toString() {
        return "playersNames=" + playersNames +
                ", duration=" + duration +
                ", themes=" + themes +
                ", gameMode=" + gameMode;
    }

}
