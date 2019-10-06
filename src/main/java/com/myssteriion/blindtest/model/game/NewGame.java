package com.myssteriion.blindtest.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.tools.Tool;

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
     * Instantiates a new New game.
     *
     * @param playersNames the players names
     * @param duration     the duration
     */
    @JsonCreator
    public NewGame(Set<String> playersNames, Duration duration) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);

        this.playersNames = playersNames;
        this.duration = duration;
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



    @Override
    public String toString() {
        return "playersNames=" + playersNames +
                ", duration=" + duration;
    }

}
