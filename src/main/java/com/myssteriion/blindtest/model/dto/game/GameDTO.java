package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;
import java.util.stream.Collectors;

public class GameDTO extends AbstractDTO {

    private static final int INIT = 0;

    public static final int FIRST_MUSIC = INIT + 1;

    private List<PlayerDTO> players;

    private int nbMusicsPlayed;

    private int nbMusicsPlayedInRound;

    private Round current;



    public GameDTO(List<String> playersNames) {

        Tool.verifyValue("playersNames", playersNames);
        this.players = playersNames.stream().map(PlayerDTO::new).collect(Collectors.toList());
        this.nbMusicsPlayed = INIT;
        this.nbMusicsPlayedInRound = INIT;
        this.current = Round.getFirst();
    }



    public List<PlayerDTO> getPlayers() {
        return players;
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

        if ( nbMusicsPlayedInRound == current.getNbMusics() ) {
            current = current.next();
            nbMusicsPlayedInRound = INIT;
        }
    }


    @Override
    public String toString() {
        return "players=" + players +
                ", nbMusicsPlayed=" + nbMusicsPlayed +
                ", nbMusicsPlayedInRound=" + nbMusicsPlayedInRound +
                ", current=" + current;
    }

}
