package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.IModel;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a current game.
 */
public class Game implements IModel {

    private static final int INIT = 0;

    /**
     * The id.
     */
    private Integer id;

    /**
     * The players list.
     */
    private List<Player> players;

    /**
     * The duration.
     */
    private Duration duration;

    /**
     * The number of musics played.
     */
    private int nbMusicsPlayed;

    /**
     * The number of musics played in current roud.
     */
    private int nbMusicsPlayedInRound;

    /**
     * The current round.
     */
    private Round round;

    /**
     * The implementation of the current round.
     */
    private AbstractRoundContent roundContent;

    /**
     * The current effect.
     */
    private Effect nextEffect;


    /**
     * Instantiates a new Game.
     *
     * @param playersNames the players names
     * @param duration     the duration
     */
    public Game(Set<String> playersNames, Duration duration) {

        Tool.verifyValue("playersNames", playersNames);
        Tool.verifyValue("duration", duration);

        this.players = playersNames.stream().map(Player::new).sorted(Player.COMPARATOR).collect(Collectors.toList());
        this.duration = duration;
        this.nbMusicsPlayed = INIT;
        this.nbMusicsPlayedInRound = INIT;
        this.round = Round.getFirst();
        this.roundContent = this.round.createRoundContent(this);
        this.nextEffect = Effect.NONE;
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public List<Player> getPlayers() {
        return players;
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
     * Gets nbMusicsPlayed.
     *
     * @return the nbMusicsPlayed
     */
    public int getNbMusicsPlayed() {
        return nbMusicsPlayed;
    }

    /**
     * Gets nbMusicsPlayed.
     *
     * @return the nbMusicsPlayed
     */
    public int getNbMusicsPlayedInRound() {
        return nbMusicsPlayedInRound;
    }

    /**
     * Gets round.
     *
     * @return the round
     */
    public Round getRound() {
        return round;
    }

    /**
     * Gets roundContent.
     *
     * @return the roundContent
     */
    public AbstractRoundContent getRoundContent() {
        return roundContent;
    }

    /**
     * Gets nextEffect.
     *
     * @return the nextEffect
     */
    public Effect getNextEffect() {
        return nextEffect;
    }


    /**
     * Pass to the next step.
     */
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

    /**
     * Test if it's the first step.
     *
     * @return TRUE if it's the first step, FALSE otherwise
     */
    public boolean isFirstStep() {
        return nbMusicsPlayed == INIT;
    }

    /**
     * Test if it's the last step.
     *
     * @return TRUE if it's the last step, FALSE otherwise
     */
    public boolean isLastStep() {
        return round != null && roundContent != null && round.isLast() && roundContent.isLastMusic(this);
    }

    /**
     * Test if it's the game is finish.
     *
     * @return TRUE if it's the game is finish, FALSE otherwise
     */
    public boolean isFinished() {
        return Tool.isNullOrEmpty(round);
    }


    /**
     * Randomly choose an effect.
     *
     * @return an effect
     */
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
