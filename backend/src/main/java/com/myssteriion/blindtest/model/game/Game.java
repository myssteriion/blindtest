package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.round.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.round.AbstractRoundContent;
import com.myssteriion.blindtest.properties.RoundContentProperties;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.model.IModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * The themes.
     */
    private List<Theme> themes;
    
    /**
     * The effects.
     */
    private List<Effect> effects;
    
    /**
     * The number of listened musics by themes.
     */
    private Map<Theme, Integer> listenedMusics;
    
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
     * Instantiates a new Game.
     */
    public Game() {
    }
    
    
    
    /**
     * Instantiates a new Game.
     *
     * @param players        the players
     * @param duration       the duration
     * @param themes         the themes
     * @param effects        the effects
     */
    public Game(List<Player> players, Duration duration, List<Theme> themes, List<Effect> effects, RoundContentProperties prop) {
        
        this.players = players;
        this.duration = duration;
        this.themes = themes;
        this.effects = effects;
        
        this.nbMusicsPlayed = INIT;
        this.nbMusicsPlayedInRound = INIT;
        this.round = Round.getFirst();
        // TODO refactor en supprimant car BeanFactory n'existe plus pour la class ROUND
        this.roundContent = this.round.createRoundContent(this, prop);
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
     * Sets players.
     *
     * @param players the players
     * @return the players
     */
    public Game setPlayers(List<Player> players) {
        this.players = players;
        return this;
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
     * Sets duration.
     *
     * @param duration the duration
     * @return the duration
     */
    public Game setDuration(Duration duration) {
        this.duration = duration;
        return this;
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
     * Sets themes.
     *
     * @param themes the themes
     * @return the themes
     */
    public Game setThemes(List<Theme> themes) {
        this.themes = themes;
        return this;
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
     * Sets effects.
     *
     * @param effects the effects
     * @return the effects
     */
    public Game setEffects(List<Effect> effects) {
        this.effects = effects;
        return this;
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
     * Sets listened musics.
     *
     * @param listenedMusics the listened musics
     * @return the listened musics
     */
    public Game setListenedMusics(Map<Theme, Integer> listenedMusics) {
        this.listenedMusics = listenedMusics;
        return this;
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
     * Sets nb musics played.
     *
     * @param nbMusicsPlayed the nb musics played
     * @return the nb musics played
     */
    public Game setNbMusicsPlayed(int nbMusicsPlayed) {
        this.nbMusicsPlayed = nbMusicsPlayed;
        return this;
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
     * Sets nb musics played in round.
     *
     * @param nbMusicsPlayedInRound the nb musics played in round
     * @return the nb musics played in round
     */
    public Game setNbMusicsPlayedInRound(int nbMusicsPlayedInRound) {
        this.nbMusicsPlayedInRound = nbMusicsPlayedInRound;
        return this;
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
     * Sets round.
     *
     * @param round the round
     * @return the round
     */
    public Game setRound(Round round) {
        this.round = round;
        return this;
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
     * Sets round content.
     *
     * @param roundContent the round content
     * @return the round content
     */
    public Game setRoundContent(AbstractRoundContent roundContent) {
        this.roundContent = roundContent;
        return this;
    }
    
    
    /**
     * Increment listenedMusics.
     *
     * @param theme the theme
     */
    public void incrementListenedMusics(Theme theme) {
        
        CommonUtils.verifyValue("theme", theme);
        
        if (listenedMusics == null)
            listenedMusics = new HashMap<>();
        
        if ( !listenedMusics.containsKey(theme) )
            listenedMusics.put(theme, 0);
        
        listenedMusics.put(theme, listenedMusics.get(theme) + 1);
    }
    
    /**
     * Pass to the next step.
     */
    public void nextStep(RoundContentProperties prop) {
        
        nbMusicsPlayed++;
        nbMusicsPlayedInRound++;
        
        if ( roundContent != null && roundContent.isFinished(this) ) {
            round = round.nextRound();
            
            // TODO refactor en supprimant car BeanFactory n'existe plus pour la class ROUND
            roundContent = (round == null) ? null : round.createRoundContent(this, prop);
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
                ", themes=" + themes +
                ", effects=" + effects +
                ", listenedMusics=" + listenedMusics +
                ", nbMusicsPlayed=" + nbMusicsPlayed +
                ", nbMusicsPlayedInRound=" + nbMusicsPlayedInRound +
                ", round=" + round +
                ", roundContent={" + roundContent + "}";
    }
    
}
