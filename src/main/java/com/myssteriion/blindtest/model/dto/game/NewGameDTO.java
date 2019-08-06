package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;

public class NewGameDTO {

    private List<String> playersNames;

    private Duration duration;



    public NewGameDTO(List<String> playersNames, Duration duration) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);

        this.playersNames = playersNames;
        this.duration = duration;
    }



    public List<String> getPlayersNames() {
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
