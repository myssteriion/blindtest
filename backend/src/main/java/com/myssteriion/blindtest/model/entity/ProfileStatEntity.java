package com.myssteriion.blindtest.model.entity;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.persistence.converter.duration.DurationIntegerMapConverter;
import com.myssteriion.blindtest.persistence.converter.theme.ThemeGoodAnswerIntegerMapConverter;
import com.myssteriion.blindtest.persistence.converter.theme.ThemeIntegerMapConverter;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.entity.AbstractEntity;
import com.myssteriion.utils.persistence.converter.impl.StringIntegerMapConverter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * The ProfileStat.
 */
@Entity
@Table(name = "profile_stat")
@SequenceGenerator(name = "sequence_id", sequenceName = "profile_stat_sequence", allocationSize = 1)
public class ProfileStatEntity extends AbstractEntity<ProfileStatEntity> {
    
    /**
     * The number of game played.
     */
    @Column(name = "played_games", nullable = false, length = 1000)
    @ColumnDefault(CommonConstant.EMPTY_JSON_WITH_QUOTE)
    @Convert(converter = DurationIntegerMapConverter.class)
    private Map<Duration, Integer> playedGames;
    
    /**
     * The bests scores by durations.
     */
    @Column(name = "best_scores", nullable = false, length = 1000)
    @ColumnDefault(CommonConstant.EMPTY_JSON_WITH_QUOTE)
    @Convert(converter = DurationIntegerMapConverter.class)
    private Map<Duration, Integer> bestScores;
    
    /**
     * The number of game won by rank.
     */
    /*
     * La clé est un String car en JSON ca ne peut pas être un Integer -> mais ca reste parsable.
     */
    @Column(name = "won_games", nullable = false, length = 1000)
    @ColumnDefault(CommonConstant.EMPTY_JSON_WITH_QUOTE)
    @Convert(converter = StringIntegerMapConverter.class)
    private Map<String, Integer> wonGames;
    
    /**
     * The number of listened musics by themes.
     */
    @Column(name = "listened_musics", nullable = false, length = 1000)
    @ColumnDefault(CommonConstant.EMPTY_JSON_WITH_QUOTE)
    @Convert(converter = ThemeIntegerMapConverter.class)
    private Map<Theme, Integer> listenedMusics;
    
    /**
     * The number of found musics by themes by WinMode.
     */
    @Column(name = "found_musics", nullable = false, length = 1000)
    @ColumnDefault(CommonConstant.EMPTY_JSON_WITH_QUOTE)
    @Convert(converter = ThemeGoodAnswerIntegerMapConverter.class)
    private Map< Theme, Map<GoodAnswer, Integer> > foundMusics;
    
    
    
    /**
     * Instantiates a new ProfileStatEntity.
     */
    public ProfileStatEntity() {
    }
    
    
    
    /**
     * Gets played games.
     *
     * @return the played games
     */
    public Map<Duration, Integer> getPlayedGames() {
        return playedGames;
    }
    
    /**
     * Sets played games.
     *
     * @param playedGames the played games
     * @return this
     */
    public ProfileStatEntity setPlayedGames(Map<Duration, Integer> playedGames) {
        this.playedGames = playedGames;
        return this;
    }
    
    /**
     * Gets best scores.
     *
     * @return the best scores
     */
    public Map<Duration, Integer> getBestScores() {
        return bestScores;
    }
    
    /**
     * Sets best scores.
     *
     * @param bestScores the best scores
     * @return this
     */
    public ProfileStatEntity setBestScores(Map<Duration, Integer> bestScores) {
        this.bestScores = bestScores;
        return this;
    }
    
    /**
     * Gets wonGames.
     *
     * @return the wonGames
     */
    public Map<String, Integer> getWonGames() {
        return wonGames;
    }
    
    /**
     * Set wonGames.
     *
     * @param wonGames the wonGames
     * @return this
     */
    public ProfileStatEntity setWonGames(Map<String, Integer> wonGames) {
        this.wonGames = wonGames;
        return this;
    }
    
    /**
     * Gets listened musics.
     *
     * @return the listened musics
     */
    public Map<Theme, Integer> getListenedMusics() {
        return listenedMusics;
    }
    
    /**
     * Sets listened musics.
     *
     * @param listenedMusics the listened musics
     * @return this
     */
    public ProfileStatEntity setListenedMusics(Map<Theme, Integer> listenedMusics) {
        this.listenedMusics = listenedMusics;
        return this;
    }
    
    /**
     * Gets found musics.
     *
     * @return the found musics
     */
    public Map< Theme, Map<GoodAnswer, Integer> > getFoundMusics() {
        return foundMusics;
    }
    
    /**
     * Sets found musics.
     *
     * @param foundMusics the found musics
     * @return this
     */
    public ProfileStatEntity setFoundMusics(Map< Theme, Map<GoodAnswer, Integer> > foundMusics) {
        this.foundMusics = foundMusics;
        return this;
    }
    
    
    /**
     * Increment playedGames.
     */
    public void incrementPlayedGames(Duration duration) {
        
        CommonUtils.verifyValue("duration", duration);
        
        if (playedGames == null)
            playedGames = new HashMap<>();
        
        if ( !playedGames.containsKey(duration) )
            playedGames.put(duration, 0);
        
        playedGames.put(duration, playedGames.get(duration) + 1);
    }
    
    /**
     * Add best score if its better.
     *
     * @param duration the duration
     * @param scores   the scores
     */
    public void addBestScoreIfBetter(Duration duration, int scores) {
        
        CommonUtils.verifyValue("duration", duration);
        
        if (bestScores == null)
            bestScores = new HashMap<>();
        
        if ( !bestScores.containsKey(duration) )
            bestScores.put(duration, 0);
        
        if ( scores > bestScores.get(duration) )
            bestScores.put(duration, scores);
    }
    
    /**
     * Increment wonGames.
     *
     * @param rank the rank
     */
    public void incrementWonGames(int rank) {
        
        CommonUtils.verifyValue("rank", rank);
        
        String strRank = Integer.valueOf(rank).toString();
        
        if (wonGames == null)
            wonGames = new HashMap<>();
        
        if ( !wonGames.containsKey(strRank) )
            wonGames.put(strRank, 0);
        
        wonGames.put(strRank, wonGames.get(strRank) + 1);
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
     * Increment foundMusics.
     *
     * @param theme 	the theme
     * @param goodAnswer 	the winMode
     */
    public void incrementFoundMusics(Theme theme, GoodAnswer goodAnswer) {
        
        CommonUtils.verifyValue("theme", theme);
        CommonUtils.verifyValue("winMode", goodAnswer);
        
        if (foundMusics == null)
            foundMusics = new HashMap<>();
        
        if ( !foundMusics.containsKey(theme) )
            foundMusics.put(theme, new HashMap<>());
        
        if ( !foundMusics.get(theme).containsKey(goodAnswer) )
            foundMusics.get(theme).put(goodAnswer, 0);
        
        foundMusics.get(theme).put(goodAnswer, foundMusics.get(theme).get(goodAnswer) + 1);
    }
    
    
    @Override
    public String toString() {
        return super.toString() +
                ", playedGames=" + playedGames +
                ", bestScores=" + bestScores +
                ", wonGames=" + wonGames +
                ", listenedMusics=" + listenedMusics +
                ", foundMusics=" + foundMusics;
    }
    
}
