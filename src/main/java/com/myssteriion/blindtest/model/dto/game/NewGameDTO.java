package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.tools.Tool;

import java.util.Set;

public class NewGameDTO {

    private Set<String> playersNames;

    private Duration duration;



    public NewGameDTO(Set<String> playersNames, Duration duration) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);

        this.playersNames = playersNames;
        this.duration = duration;
    }



    public Set<String> getPlayersNames() {
        return playersNames;
    }

    public Duration getDuration() {
        return duration;
    }



    @Override
    public String toString() {
        return "playersNames=" + playersNames +
                ", duration=" + duration;
    }

}
