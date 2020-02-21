package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.utils.BeanFactory;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.model.IModel;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * The number of listened musics by themes.
     */
    private Map<Theme, Integer> listenedMusics;



    /**
     * Instantiates a new Game.
     *
     * @param players  the players
     * @param duration the duration
     * @param themes   the themes
     * @param connectionMode the connection mode
     */
    public Game(Set<Player> players, Duration duration, List<Theme> themes, List<Effect> effects, ConnectionMode connectionMode) {

        CommonUtils.verifyValue("players", players);
        CommonUtils.verifyValue("duration", duration);
        CommonUtils.verifyValue("gameMode", connectionMode);

        checkNbPlayers(players);

        this.players = players.stream().sorted(Comparator.comparing(player -> player.getProfile().getName(), String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
        this.duration = duration;
        this.nbMusicsPlayed = INIT;
        this.nbMusicsPlayedInRound = INIT;
        this.round = Round.getFirst();
        this.roundContent = this.round.createRoundContent(this);
        this.themes = CommonUtils.isNullOrEmpty(themes) ? Theme.getSortedTheme() : themes;
        this.effects = CommonUtils.isNullOrEmpty(effects) ? Effect.getSortedEffect() : effects;
        this.connectionMode = connectionMode;
        this.listenedMusics = new HashMap<>();
    }

    private void checkNbPlayers(Set<Player> players) {

        ConfigProperties configProperties = BeanFactory.getBean(ConfigProperties.class);

        if ( players.size() < configProperties.getMinPlayers() )
            throw new IllegalArgumentException(configProperties.getMinPlayers() + " players at minimum");

        if ( players.size() > configProperties.getMaxPlayers() )
            throw new IllegalArgumentException(configProperties.getMaxPlayers() + " players at maximum");
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
     * Gets the game mode.
     *
     * @return the game mode
     */
    public ConnectionMode getConnectionMode() {
        return connectionMode;
    }

    /**
     * Gets listenedMusics.
     *
     * @return The listenedMusics.
     */
    public Map<Theme, Integer> getListenedMusics() {
        return listenedMusics;
    }


    /**
     * Increment listenedMusics.
     *
     * @param theme the theme
     */
    public void incrementListenedMusics(Theme theme) {

        CommonUtils.verifyValue("theme", theme);

        if ( !listenedMusics.containsKey(theme) )
            listenedMusics.put(theme, 0);

        listenedMusics.put(theme, listenedMusics.get(theme) + 1);
    }

    /**
     * Pass to the next step.
     */
    public void nextStep() {

        nbMusicsPlayed++;
        nbMusicsPlayedInRound++;

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
        return CommonUtils.isNullOrEmpty(round);
    }


    @Override
    public String toString() {
        return "players=" + players +
                ", duration=" + duration +
                ", nbMusicsPlayed=" + nbMusicsPlayed +
                ", nbMusicsPlayedInRound=" + nbMusicsPlayedInRound +
                ", round=" + round +
                ", roundContent={" + roundContent + "}" +
                ", themes=" + themes +
                ", effects=" + effects +
                ", connectionMode=" + connectionMode +
                ", listenedMusics=" + listenedMusics;
    }

}
