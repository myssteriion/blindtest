package com.myssteriion.blindtest.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.utils.CommonUtils;

import java.util.ArrayList;
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
     *
     * @param gameId   		the game id
     * @param music 		the music
     * @param authorWinners	the author winners
     * @param titleWinners	the title winners
     * @param losers  		the losers
     */
    @JsonCreator
    public MusicResult(Integer gameId, MusicDTO music, List<String> authorWinners, List<String> titleWinners, List<String> losers, List<String> penalties) {
        
        CommonUtils.verifyValue("gameId", gameId);
        CommonUtils.verifyValue("music", music);
        
        this.gameId = gameId;
        this.music = music;
        
        this.authorWinners = (authorWinners == null) ? new ArrayList<>() : CommonUtils.removeDuplicate(authorWinners);
        this.titleWinners = (titleWinners == null) ? new ArrayList<>() : CommonUtils.removeDuplicate(titleWinners);
        this.losers = (losers == null) ? new ArrayList<>() : losers;
        this.penalties = (penalties == null) ? new ArrayList<>() : CommonUtils.removeDuplicate(penalties);
    }
    
    
    
    /**
     * Gets game id.
     *
     * @return the game id
     */
    public Integer getGameId() {
        return gameId;
    }
    
    /**
     * Gets music.
     *
     * @return the music
     */
    public MusicDTO getMusic() {
        return music;
    }
    
    /**
     * Gets author winners.
     *
     * @return the winners
     */
    public List<String> getAuthorWinners() {
        return authorWinners;
    }
    
    /**
     * Gets title winners.
     *
     * @return the winners
     */
    public List<String> getTitleWinners() {
        return titleWinners;
    }
    
    /**
     * Gets losers.
     *
     * @return the losers
     */
    public List<String> getLosers() {
        return losers;
    }
    
    /**
     * Gets penalties.
     *
     * @return The penalties
     */
    public List<String> getPenalties() {
        return penalties;
    }
    
    
    /**
     * Test if "name" is a author winner.
     *
     * @param name the name
     * @return TRUE if "name" is a author winner, FALSE otherwise
     */
    public boolean isAuthorWinner(String name) {
        
        CommonUtils.verifyValue("name", name);
        
        return authorWinners.stream().anyMatch(playerName -> playerName.equals(name));
    }
    
    /**
     * Test if "name" is a title winner.
     *
     * @param name the name
     * @return TRUE if "name" is a title winner, FALSE otherwise
     */
    public boolean isTitleWinner(String name) {
        
        CommonUtils.verifyValue("name", name);
        
        return titleWinners.stream().anyMatch(playerName -> playerName.equals(name));
    }
    
    /**
     * Test if "name" is a author and title winner.
     *
     * @param name the name
     * @return TRUE if "name" is a author and title winner, FALSE otherwise
     */
    public boolean isAuthorAndTitleWinner(String name) {
        
        CommonUtils.verifyValue("name", name);
        
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
        
        return losers.stream().anyMatch(playerName -> playerName.equals(name));
    }
    
    /**
     * Test if "name" had penalty.
     *
     * @param name the name
     * @return TRUE if "name" had penalty, FALSE otherwise
     */
    public boolean hadPenalty(String name) {
        
        CommonUtils.verifyValue("name", name);
        
        return penalties.stream().anyMatch(playerName -> playerName.equals(name));
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
