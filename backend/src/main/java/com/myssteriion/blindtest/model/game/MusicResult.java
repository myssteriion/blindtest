package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.utils.CommonUtils;

import java.util.List;

/**
 * Represents a result of music.
 */
public class MusicResult {
    
    /**
     * The game id.
     */
    private Integer gameId;
    
    /**
     * The music.
     */
    private MusicDTO music;
    
    /**
     * The author winners list.
     */
    private List<String> authorWinners;
    
    /**
     * The title winners list.
     */
    private List<String> titleWinners;
    
    /**
     * The losers list.
     */
    private List<String> losers;
    
    /**
     * The penalties list.
     */
    private List<String> penalties;
    
    
    
    /**
     * Instantiates a new Music result.
     */
    public MusicResult() {
    }
    
    
    
    /**
     * Get gameId.
     *
     * @return the gameId
     */
    public Integer getGameId() {
        return gameId;
    }
    
    /**
     * Set gameId.
     *
     * @param gameId the gameId
     * @return this
     */
    public MusicResult setGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }
    
    /**
     * Get music.
     *
     * @return the music
     */
    public MusicDTO getMusic() {
        return music;
    }
    
    /**
     * Set music.
     *
     * @param music the music
     * @return this
     */
    public MusicResult setMusic(MusicDTO music) {
        this.music = music;
        return this;
    }
    
    /**
     * Get authorWinners.
     *
     * @return the authorWinners
     */
    public List<String> getAuthorWinners() {
        return authorWinners;
    }
    
    /**
     * Set authorWinners.
     *
     * @param authorWinners the authorWinners
     * @return this
     */
    public MusicResult setAuthorWinners(List<String> authorWinners) {
        this.authorWinners = authorWinners;
        return this;
    }
    
    /**
     * Get titleWinners.
     *
     * @return the titleWinners
     */
    public List<String> getTitleWinners() {
        return titleWinners;
    }
    
    /**
     * Set titleWinners.
     *
     * @param titleWinners the titleWinners
     * @return this
     */
    public MusicResult setTitleWinners(List<String> titleWinners) {
        this.titleWinners = titleWinners;
        return this;
    }
    
    /**
     * Get losers.
     *
     * @return the losers
     */
    public List<String> getLosers() {
        return losers;
    }
    
    /**
     * Set losers.
     *
     * @param losers the losers
     * @return this
     */
    public MusicResult setLosers(List<String> losers) {
        this.losers = losers;
        return this;
    }
    
    /**
     * Get penalties.
     *
     * @return the penalties
     */
    public List<String> getPenalties() {
        return penalties;
    }
    
    /**
     * Set penalties.
     *
     * @param penalties the penalties
     * @return this
     */
    public MusicResult setPenalties(List<String> penalties) {
        this.penalties = penalties;
        return this;
    }
    
    
    
    /**
     * Test if "name" is a author winner.
     *
     * @param name the name
     * @return TRUE if "name" is a author winner, FALSE otherwise
     */
    public boolean isAuthorWinner(String name) {
        
        CommonUtils.verifyValue("name", name);
        
        return authorWinners != null && authorWinners.stream().anyMatch(playerName -> playerName.equals(name));
    }
    
    /**
     * Test if "name" is a title winner.
     *
     * @param name the name
     * @return TRUE if "name" is a title winner, FALSE otherwise
     */
    public boolean isTitleWinner(String name) {
        
        CommonUtils.verifyValue("name", name);
        
        return titleWinners != null && titleWinners.stream().anyMatch(playerName -> playerName.equals(name));
    }
    
    /**
     * Test if "name" is a author and title winner.
     *
     * @param name the name
     * @return TRUE if "name" is a author and title winner, FALSE otherwise
     */
    public boolean isAuthorAndTitleWinner(String name) {
        return isAuthorWinner(name) && isTitleWinner(name);
    }
    
    /**
     * Test if "name" is a looser.
     *
     * @param name the name
     * @return TRUE if "name" is a looser, FALSE otherwise
     */
    public boolean isLoser(String name) {
        
        CommonUtils.verifyValue("name", name);
        
        return losers != null && losers.stream().anyMatch(playerName -> playerName.equals(name));
    }
    
    /**
     * Test if "name" had penalty.
     *
     * @param name the name
     * @return TRUE if "name" had penalty, FALSE otherwise
     */
    public boolean hadPenalty(String name) {
        
        CommonUtils.verifyValue("name", name);
        
        return penalties != null && penalties.stream().anyMatch(playerName -> playerName.equals(name));
    }
    
    
    @Override
    public String toString() {
        return "gameId=" + gameId +
                ", music={" + music + "}" +
                ", authorWinners=" + authorWinners +
                ", titleWinners=" + titleWinners +
                ", losers=" + losers +
                ", penalties=" + penalties;
    }
    
}
