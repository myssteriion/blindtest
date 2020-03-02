package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.rest.exception.ConflictException;
import com.myssteriion.utils.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for game.
 */
@Service
public class GameService {
    
    private MusicService musicService;
    
    private ProfileService profileService;
    
    private ProfileStatService profileStatService;
    
    private SpotifyService spotifyService;
    
    /**
     * The game list.
     */
    private List<Game> games = new ArrayList<>();
    
    
    
    @Autowired
    public GameService(MusicService musicService, ProfileService profileService, ProfileStatService profileStatService, SpotifyService spotifyService) {
        this.musicService = musicService;
        this.profileService = profileService;
        this.profileStatService = profileStatService;
        this.spotifyService = spotifyService;
    }
    
    
    
    /**
     * Create a game from new game.
     *
     * @param newGame the new game
     * @return the game
     * @throws NotFoundException the not found exception
     * @throws SpotifyException  the spotify exception
     */
    public Game newGame(NewGame newGame) throws NotFoundException, SpotifyException {
        
        CommonUtils.verifyValue("newGame", newGame);
        
        musicService.refresh();
        verifyContentTheme(newGame);
        
        if ( newGame.getConnectionMode().isNeedConnection() )
            spotifyService.testConnection();
        
        Game game = new Game( cratePlayersList(newGame.getPlayersNames()), newGame.getDuration(), newGame.isSameProbability(), newGame.getThemes(), newGame.getEffects(), newGame.getConnectionMode() );
        game.setId( findNextId() );
        
        games.add(game);
        
        return game;
    }
    
    /**
     * Check if all theme have one music at least.
     *
     * @param newGame the new game
     * @throws NotFoundException the not found exception
     */
    private void verifyContentTheme(NewGame newGame) throws NotFoundException {
        
        for ( Theme theme : newGame.getThemes() ) {
            
            Integer nbMusic = musicService.getMusicNumber( theme, newGame.getConnectionMode() );
            if (nbMusic == 0)
                throw new NotFoundException("Zero music found ('" + theme + "' ; '" + newGame.getConnectionMode().transformForSearchMusic() + "')");
        }
    }
    
    /**
     * Create players list.
     *
     * @param playersNames the players names list
     * @return the players list
     * @throws NotFoundException the not found exception
     */
    private Set<Player> cratePlayersList(Set<String> playersNames) throws NotFoundException {
        
        Set<Player> players = new HashSet<>();
        
        for (String playerName : playersNames) {
            
            ProfileDTO profile = profileService.find(new ProfileDTO(playerName));
            if (profile == null)
                throw new NotFoundException("Player '" + playerName + "' must have a profile.");
            
            players.add( new Player(profile) );
        }
        
        return players;
    }
    
    private Integer findNextId() {
        OptionalInt optionalInt = games.stream().mapToInt(Game::getId).max();
        return ( optionalInt.isPresent() ) ? optionalInt.getAsInt() + 1 : 0;
    }
    
    
    /**
     * Update the game from music result.
     *
     * @param musicResult the music result
     * @return the game
     * @throws NotFoundException the not found exception
     * @throws ConflictException the conflict exception
     */
    public Game apply(MusicResult musicResult) throws NotFoundException, ConflictException {
        
        CommonUtils.verifyValue("musicResult", musicResult);
        Game game = games.stream()
                .filter( g -> g.getId().equals(musicResult.getGameId()) )
                .findFirst()
                .orElseThrow( () -> new NotFoundException("Game not found.") );
        
        if ( !game.isFinished() ) {
            
            // update musicDto
            MusicDTO musicDto = musicService.find( musicResult.getMusic() );
            if (musicDto == null)
                throw new NotFoundException("Music not found.");
            
            if ( !game.getThemes().contains(musicDto.getTheme()) )
                throw new NotFoundException("'" + musicDto.getTheme() + "' not found for this game (" + game.getThemes() + ").");
            
            musicDto.incrementPlayed();
            musicService.update(musicDto);
            
            game.incrementListenedMusics( musicResult.getMusic().getTheme() );
            
            // apply score
            game = game.getRoundContent().apply(game, musicResult);
            
            // update profileStatDto
            List<Player> players = game.getPlayers();
            
            updatePlayersRanks(players);
            
            for (Player player : players) {
                
                ProfileDTO profileDto = player.getProfile();
                ProfileStatDTO profileStatDto = profileStatService.findByProfile(profileDto);
                profileStatDto.incrementListenedMusics( musicResult.getMusic().getTheme() );
                
                if ( musicResult.isAuthorAndTitleWinner(profileDto.getName()) ) {
                    profileStatDto.incrementFoundMusics( musicResult.getMusic().getTheme(), GoodAnswer.BOTH );
                    player.incrementFoundMusics( musicResult.getMusic().getTheme(), GoodAnswer.BOTH );
                }
                else if ( musicResult.isAuthorWinner(profileDto.getName()) ) {
                    profileStatDto.incrementFoundMusics( musicResult.getMusic().getTheme(), GoodAnswer.AUTHOR );
                    player.incrementFoundMusics( musicResult.getMusic().getTheme(), GoodAnswer.AUTHOR );
                }
                else if ( musicResult.isTitleWinner(profileDto.getName()) ) {
                    profileStatDto.incrementFoundMusics( musicResult.getMusic().getTheme(), GoodAnswer.TITLE );
                    player.incrementFoundMusics( musicResult.getMusic().getTheme(), GoodAnswer.TITLE );
                }
                
                if ( game.isLastStep() ) {
                    profileStatDto.incrementPlayedGames( game.getDuration() );
                    profileStatDto.addBestScoreIfBetter( game.getDuration(), player.getScore() );
                    profileStatDto.incrementWonGames( player.getRank() );
                }
                
                profileStatService.update(profileStatDto);
            }
            
            game.nextStep();
        }
        
        return game;
    }
    
    /**
     * Update players ranks.
     *
     * @param players the players
     */
    private void updatePlayersRanks(List<Player> players) {
        
        List<Player> playersSorted = players.stream().sorted(Comparator.comparingInt(Player::getScore).reversed()).collect(Collectors.toList());
        
        int currentRank = 1;
        int jumpRank = 1;
        
        playersSorted.get(0).setRank(currentRank);
        int currentScore = playersSorted.get(0).getScore();
        
        for (int i = 1; i < playersSorted.size(); i++) {
            
            if (playersSorted.get(i).getScore() == currentScore) {
                jumpRank++;
            }
            else {
                for (int j = 0; j < jumpRank; j++)
                    currentRank++;
                jumpRank = 1;
            }
            
            playersSorted.get(i).setRank(currentRank);
            currentScore = playersSorted.get(i).getScore();
        }
        
        int lastRank = currentRank;
        players.forEach( player -> player.setLast(player.getRank() == lastRank) );
    }
    
    /**
     * Find the game by id.
     *
     * @param id the id
     * @return the game
     * @throws NotFoundException the not found exception
     */
    public Game findGame(Integer id) throws NotFoundException {
        
        CommonUtils.verifyValue("id", id);
        
        return games.stream()
                .filter( game -> game.getId().equals(id) )
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Game not found."));
    }
    
    /**
     * Load game.
     *
     * @param game the game
     */
    public Integer load(Game game) {
        
        CommonUtils.verifyValue("game", game);
        game.setId( findNextId() );
        game.generateRoundContent();
        
        for ( Player player : game.getPlayers() )
            profileService.createProfileAvatarFlux( player.getProfile() );
        
        games.add(game);
        
        return game.getId();
    }
    
}
