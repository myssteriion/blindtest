package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a player.
 */
public class Player {
    
    /**
     * The profile.
     */
    private ProfileEntity profile;
    
    /**
     * The current score.
     */
    private int score;
    
    /**
     * The rank.
     */
    private int rank;
    
    /**
     * If is the last (rank).
     */
    private boolean last;
    
    /**
     * If is his turn to play/choose.
     */
    private boolean turnToChoose;
    
    /**
     * Team number.
     */
    private int teamNumber;
    
    /**
     * The number of found musics by themes by WinMode.
     */
    private Map<Theme, Map<GoodAnswer, Integer>> foundMusics;
    
    
    
    /**
     * Instantiates a new Player.
     */
    public Player() {
    }
    
    /**
     * Instantiates a new Player.
     *
     * @param profile the profile
     */
    public Player(ProfileEntity profile) {
        
        CommonUtils.verifyValue("profile", profile);
        
        this.profile = profile;
        this.score = 0;
        this.rank = 1;
        this.last = false;
        this.turnToChoose = false;
        this.teamNumber = -1;
        this.foundMusics = new HashMap<>();
    }
    
    
    
    /**
     * Get profile.
     *
     * @return the profile
     */
    public ProfileEntity getProfile() {
        return profile;
    }
    
    /**
     * Set profile.
     *
     * @param profile the profile
     * @return this
     */
    public Player setProfile(ProfileEntity profile) {
        this.profile = profile;
        return this;
    }
    
    /**
     * Get score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Set score.
     *
     * @param score the score
     * @return this
     */
    public Player setScore(int score) {
        this.score = score;
        return this;
    }
    
    /**
     * Get rank.
     *
     * @return the rank
     */
    public int getRank() {
        return rank;
    }
    
    /**
     * Set rank.
     *
     * @param rank the rank
     * @return this
     */
    public Player setRank(int rank) {
        this.rank = rank;
        return this;
    }
    
    /**
     * Get last.
     *
     * @return the last
     */
    public boolean isLast() {
        return last;
    }
    
    /**
     * Set last.
     *
     * @param last the last
     * @return this
     */
    public Player setLast(boolean last) {
        this.last = last;
        return this;
    }
    
    /**
     * Get turnToChoose.
     *
     * @return the turnToChoose
     */
    public boolean isTurnToChoose() {
        return turnToChoose;
    }
    
    /**
     * Set turnToChoose.
     *
     * @param turnToChoose the turnToChoose
     * @return this
     */
    public Player setTurnToChoose(boolean turnToChoose) {
        this.turnToChoose = turnToChoose;
        return this;
    }
    
    /**
     * Get teamNumber.
     *
     * @return the teamNumber
     */
    public int getTeamNumber() {
        return teamNumber;
    }
    
    /**
     * Set teamNumber.
     *
     * @param teamNumber the teamNumber
     * @return this
     */
    public Player setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
        return this;
    }
    
    /**
     * Get foundMusics.
     *
     * @return the foundMusics
     */
    public Map<Theme, Map<GoodAnswer, Integer>> getFoundMusics() {
        return foundMusics;
    }
    
    /**
     * Set foundMusics.
     *
     * @param foundMusics the foundMusics
     * @return this
     */
    public Player setFoundMusics(Map<Theme, Map<GoodAnswer, Integer>> foundMusics) {
        this.foundMusics = foundMusics;
        return this;
    }
    
    
    /**
     * Add score.
     *
     * @param score the score
     */
    public void addScore(int score) {
        this.score += score;
    }
    
    /**
     * Increment foundMusics.
     *
     * @param theme 	the theme
     * @param goodAnswer 	the winMode
     */
    public void incrementFoundMusics(Theme theme, GoodAnswer goodAnswer) {
        
        CommonUtils.verifyValue("theme", theme);
        CommonUtils.verifyValue("goodAnswer", goodAnswer);
        
        if (foundMusics == null)
            foundMusics = new HashMap<>();
        
        if ( !foundMusics.containsKey(theme) )
            foundMusics.put(theme, new HashMap<>());
        
        if ( !foundMusics.get(theme).containsKey(goodAnswer) )
            foundMusics.get(theme).put(goodAnswer, 0);
        
        foundMusics.get(theme).put(goodAnswer, foundMusics.get(theme).get(goodAnswer) + 1);
    }
    
    
    @Override
    public int hashCode() {
        return Objects.hash(profile);
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (this == obj)
            return true;
        
        if(obj == null || obj.getClass()!= this.getClass())
            return false;
        
        Player other = (Player) obj;
        return Objects.equals(this.profile, other.profile);
    }
    
    @Override
    public String toString() {
        return "profile={" + profile + "}" +
                ", score=" + score +
                ", rank=" + rank +
                ", last=" + last +
                ", turnToChoose=" + turnToChoose +
                ", teamNumber=" + teamNumber +
                ", foundMusics=" + foundMusics;
    }
    
}
