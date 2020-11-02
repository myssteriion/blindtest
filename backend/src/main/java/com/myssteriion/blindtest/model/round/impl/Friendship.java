package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.model.common.RoundName;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.model.round.AbstractRound;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The Friendship round.
 */
public class Friendship extends AbstractRound {
    
    /**
     * Teams number.
     */
    private int nbTeams;
    
    
    
    /**
     * Instantiates a new Friendship.
     *
     * @param nbMusics   the nb musics
     * @param nbPointWon the nb point won
     */
    public Friendship(int nbMusics, int nbPointWon) {
        super(RoundName.FRIENDSHIP, nbMusics, nbPointWon);
    }
    
    
    
    /**
     * Gets nbTeams.
     *
     * @return The nbTeams.
     */
    public int getNbTeams() {
        return nbTeams;
    }
    
    
    
    @Override
    public void prepare(Game game) {
        
        super.prepare(game);
        
        int nbPlayers = game.getPlayers().size();
        if (nbPlayers == 2) {
            nbTeams = 2;
            game.getPlayers().get(0).setTeamNumber(0);
            game.getPlayers().get(1).setTeamNumber(1);
        }
        else {
            
            nbTeams = (nbPlayers % 2 == 0) ? nbPlayers / 2 : (nbPlayers / 2) + 1;
            
            List<Player> playersCopied = new ArrayList<>( game.getPlayers() );
            
            int currentTeamNumber = 0;
            if (nbPlayers % 2 == 1) {
                
                playersCopied.stream()
                        .filter( player -> player.getRank() == 1 )
                        .findAny()
                        .get()
                        .setTeamNumber(currentTeamNumber);
                
                playersCopied.removeIf( player -> player.getTeamNumber() != -1);
                
                currentTeamNumber++;
            }
            
            
            while ( !playersCopied.isEmpty() ) {
                
                int index = Constant.RANDOM.nextInt( playersCopied.size() );
                playersCopied.get(index).setTeamNumber(currentTeamNumber);
                playersCopied.remove(index);
                
                index = Constant.RANDOM.nextInt( playersCopied.size() );
                playersCopied.get(index).setTeamNumber(currentTeamNumber);
                playersCopied.remove(index);
                
                currentTeamNumber++;
            }
        }
    }
    
    @Override
    public Game apply(Game game, MusicResult musicResult) {
        
        CommonUtils.verifyValue("game", game);
        CommonUtils.verifyValue("musicResult", musicResult);
        
        // le super n'est pas appelé
        game.getPlayers().stream()
                .filter( player -> musicResult.hadPenalty(player.getProfile().getName()) )
                .forEach( player -> player.addScore(nbPointWon * -2) );
        
        
        
        List<Integer> winningTeams = new ArrayList<>();
        game.getPlayers().stream()
                .filter( player -> musicResult.isAuthorWinner(player.getProfile().getName()) )
                .forEach( player -> winningTeams.add(player.getTeamNumber()) );
        
        game.getPlayers().stream()
                .filter( player -> musicResult.isTitleWinner(player.getProfile().getName()) )
                .forEach( player -> winningTeams.add(player.getTeamNumber()) );
        
        for (int teamNumber : winningTeams) {
            
            game.getPlayers().stream()
                    .filter( player -> player.getTeamNumber() == teamNumber )
                    .forEach( player -> player.addScore(nbPointWon) );
        }
        
        return game;
    }
    
    
    @Override
    public String toString() {
        return super.toString() +
                ", nbTeams=" + nbTeams;
    }
    
}