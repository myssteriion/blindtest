package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;
import java.util.stream.Collectors;

public class GameDTO extends AbstractDTO {

    private static final int INIT = 0;

    public static final int FIRST_MUSIC = INIT + 1;

    private List<PlayerDTO> players;

    private Duration duration;

    private int nbMusicsPlayed;

    private int nbMusicsPlayedInRound;

    private Round current;



    public GameDTO(List<String> playersNames, Duration duration) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);

        this.players = playersNames.stream().map(PlayerDTO::new).collect(Collectors.toList());
        this.duration = duration;
        this.nbMusicsPlayed = INIT;
        this.nbMusicsPlayedInRound = INIT;
        this.current = Round.getFirst();
    }



    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getNbMusicsPlayed() {
        return nbMusicsPlayed;
    }

    public int getNbMusicsPlayedInRound() {
        return nbMusicsPlayedInRound;
    }

    public Round getCurrent() {
        return current;
    }


    public void next() {

        nbMusicsPlayed++;
        nbMusicsPlayedInRound++;

        if ( nbMusicsPlayedInRound == Math.round(current.getNbMusics() * duration.getRatio()) ) {
            current = current.next();
            nbMusicsPlayedInRound = INIT;
        }
    }

    public boolean isFinished() {
        return Tool.isNullOrEmpty(current);
    }


    @Override
    public String toString() {
        return "players=" + players +
                ", duration=" + duration +
                ", nbMusicsPlayed=" + nbMusicsPlayed +
                ", nbMusicsPlayedInRound=" + nbMusicsPlayedInRound +
                ", current=" + current;
    }

}
