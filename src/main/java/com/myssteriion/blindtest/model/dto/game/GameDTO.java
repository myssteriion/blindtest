package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;
import java.util.stream.Collectors;

public class GameDTO extends AbstractDTO {

    public static final int INIT = 0;

    private List<PlayerDTO> players;

    private Duration duration;

    private int nbMusicsPlayed;

    private int nbMusicsPlayedInRound;

    private Round round;

    private AbstractRoundContent roundContent;



    public GameDTO(List<String> playersNames, Duration duration) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);

        this.players = playersNames.stream().map(PlayerDTO::new).collect(Collectors.toList());
        this.duration = duration;
        this.nbMusicsPlayed = INIT;
        this.nbMusicsPlayedInRound = INIT;
        this.round = Round.getFirst();
        this.roundContent = this.round.createRoundContent();
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

    public Round getRound() {
        return round;
    }

    public AbstractRoundContent getRoundContent() {
        return roundContent;
    }


    public void next() {

        nbMusicsPlayed++;
        nbMusicsPlayedInRound++;

        if ( roundContent.isFinished(this) ) {
            round = round.nextRound();
            roundContent = (round == null) ? null : round.createRoundContent();
            nbMusicsPlayedInRound = INIT;
        }
    }

    public boolean isLastNext() {

        nbMusicsPlayed++;
        nbMusicsPlayedInRound++;

        boolean isLastNext = roundContent.isFinished(this) && round.nextRound() == null;

        nbMusicsPlayed--;
        nbMusicsPlayedInRound--;

        return isLastNext;
    }

    public boolean isFinished() {
        return Tool.isNullOrEmpty(round);
    }


    @Override
    public String toString() {
        return "players=" + players +
                ", duration=" + duration +
                ", nbMusicsPlayed=" + nbMusicsPlayed +
                ", nbMusicsPlayedInRound=" + nbMusicsPlayedInRound +
                ", round=" + round +
                ", roundContent={" + roundContent + "}";
    }

}
