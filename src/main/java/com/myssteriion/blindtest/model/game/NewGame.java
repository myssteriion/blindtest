package com.myssteriion.blindtest.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.Tools;

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
     * @param playersNames   the players names
     * @param duration       the duration
     * @param themes         the themes
     * @param connectionMode the connectionMode mode
     */
    @JsonCreator
    public NewGame(Set<String> playersNames, Duration duration, List<Theme> themes, List<Effect> effects, ConnectionMode connectionMode) {

        Tools.verifyValue("playersNames", playersNames);
        Tools.verifyValue("duration", duration);
        Tools.verifyValue("gameMode", connectionMode);

        this.playersNames = playersNames;
        this.duration = duration;
        this.themes = Tools.isNullOrEmpty(themes) ? Theme.getSortedTheme() : themes;
        this.effects = Tools.isNullOrEmpty(effects) ? Effect.getSortedEffect() : effects;
        this.connectionMode = connectionMode;
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
        return "playersNames=" + playersNames +
                ", duration=" + duration +
                ", themes=" + themes +
                ", effects=" + effects +
                ", connectionMode=" + connectionMode;
    }

}
