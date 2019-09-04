package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameDTO extends AbstractDTO {

    private static final int INIT = 0;

    private List<PlayerDTO> players;

    private Duration duration;

    private int nbMusicsPlayed;

    private int nbMusicsPlayedInRound;

    private Round round;

    private AbstractRoundContent roundContent;

    private Effect nextEffect;



    public GameDTO(Set<String> playersNames, Duration duration) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);

        this.players = playersNames.stream().map(PlayerDTO::new).sorted(PlayerDTO.COMPARATOR).collect(Collectors.toList());
        this.duration = duration;
        this.nbMusicsPlayed = INIT;
        this.nbMusicsPlayedInRound = INIT;
        this.round = Round.getFirst();
        this.roundContent = this.round.createRoundContent(this);
        this.nextEffect = Effect.NONE;
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

    public Effect getNextEffect() {
        return nextEffect;
    }


    public void nextStep() {

        nbMusicsPlayed++;
        nbMusicsPlayedInRound++;
        this.nextEffect = findNextEffect();

        if ( roundContent.isFinished(this) ) {
            round = round.nextRound();
            roundContent = (round == null) ? null : round.createRoundContent(this);
            nbMusicsPlayedInRound = INIT;
        }
    }

    public boolean isFirstStep() {
        return nbMusicsPlayed == INIT;
    }

    public boolean isLastStep() {
        return round != null && roundContent != null && round.isLast() && roundContent.isLastMusic(this);
    }

    public boolean isFinished() {
        return Tool.isNullOrEmpty(round);
    }


    private Effect findNextEffect() {

        int r = Tool.RANDOM.nextInt(100);

        if (r >= 70 && r < 80) return Effect.SLOW;
        if (r >= 80 && r < 90) return Effect.SPEED;
        if (r >= 90 && r < 100) return Effect.REVERSE;

        return Effect.NONE;
    }


    @Override
    public String toString() {
        return "players=" + players +
                ", duration=" + duration +
                ", nbMusicsPlayed=" + nbMusicsPlayed +
                ", nbMusicsPlayedInRound=" + nbMusicsPlayedInRound +
                ", round=" + round +
                ", roundContent={" + roundContent + "}" +
                ", nextEffect=" + nextEffect;
    }

}
