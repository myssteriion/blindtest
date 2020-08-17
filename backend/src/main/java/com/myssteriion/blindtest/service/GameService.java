package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.properties.RoundContentProperties;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    
    private ConfigProperties configProperties;
    
    // TODO refactor en supprimant car BeanFactory n'existe plus pour la class ROUND
    private RoundContentProperties prop;
    
    /**
     * The game list.
     */
    private List<Game> games = new ArrayList<>();
    
    
    
    /**
     * Instantiates a new Game service
     *
     * @param musicService       the musicService
     * @param profileService     the profileService
     * @param profileStatService the profileStatService
     * @param spotifyService     the spotifyService
     * @param configProperties   the configProperties
     */
    @Autowired
    public GameService(MusicService musicService, ProfileService profileService, ProfileStatService profileStatService, SpotifyService spotifyService,
                       ConfigProperties configProperties, RoundContentProperties prop) {
        this.musicService = musicService;
        this.profileService = profileService;
        this.profileStatService = profileStatService;
        this.spotifyService = spotifyService;
        this.configProperties = configProperties;
        this.prop = prop;
    }
    
    
    
    /**
     * Create a game from new game.
     *
     * @param newGame the new game
     * @return the game
     * @throws NotFoundException if a theme no have musics or if a profile is not found
     * @throws SpotifyException  the spotify exception
     */
    public Game newGame(NewGame newGame) throws NotFoundException, SpotifyException {
        
        checkAndFillNewGame(newGame);
        
        musicService.refresh();
        
        if ( newGame.getConnectionMode().isNeedConnection() )
            spotifyService.testConnection();
        
        List<Player> players = cratePlayersList( newGame.getProfilesId() );
        
        // TODO refactor en supprimant car BeanFactory n'existe plus pour la class ROUND
        Game game = new Game(players , newGame.getDuration(), newGame.getThemes(), newGame.getEffects(), newGame.getConnectionMode(), prop );
        game.setId( findNextId() );
        
        games.add(game);
        
        return game;
    }
    
    /**
     * Check and fill the NewGame.
     *
     * @param newGame the new game
     * @throws NotFoundException if a theme no have musics
     */
    private void checkAndFillNewGame(NewGame newGame) throws NotFoundException {
        
        CommonUtils.verifyValue("newGame", newGame);
        CommonUtils.verifyValue("newGame -> players", newGame.getProfilesId() );
        CommonUtils.verifyValue("newGame -> duration", newGame.getDuration() );
        CommonUtils.verifyValue("newGame -> connectionMode", newGame.getConnectionMode() );
        
        
        if ( CommonUtils.isNullOrEmpty(newGame.getEffects()) )
            newGame.setEffects( Effect.getSortedEffect() );
        
        if ( CommonUtils.isNullOrEmpty(newGame.getThemes()) )
            newGame.setThemes( Theme.getSortedTheme() );
        
        for ( Theme theme : newGame.getThemes() ) {
            Integer nbMusic = musicService.getMusicNumber( theme, newGame.getConnectionMode() );
            if (nbMusic == 0)
                throw new NotFoundException("Zero music found ('" + theme + "' ; '" + newGame.getConnectionMode().transformForSearchMusic() + "')");
        }
        
        
        Set<Integer> profilesId = newGame.getProfilesId();
        if ( profilesId.size() < configProperties.getMinPlayers() )
            throw new IllegalArgumentException(configProperties.getMinPlayers() + " players at minimum");
        
        if ( profilesId.size() > configProperties.getMaxPlayers() )
            throw new IllegalArgumentException(configProperties.getMaxPlayers() + " players at maximum");
    }
    
    /**
     * Create players list.
     *
     * @param profilesId the profiles id
     * @return the players list
     * @throws NotFoundException if a profile is not found
     */
    private List<Player> cratePlayersList(Set<Integer> profilesId) throws NotFoundException {
        
        Set<Player> players = new HashSet<>();
        
        for (Integer profileId : profilesId) {
            
            ProfileDTO profile = profileService.find( new ProfileDTO().setId(profileId) );
            if (profile == null)
                throw new NotFoundException("Profile '" + profileId + "' not found.");
            
            players.add( new Player(profile) );
        }
        
        return players.stream()
                .sorted(Comparator.comparing(player -> player.getProfile().getName(), String.CASE_INSENSITIVE_ORDER))
                .collect( Collectors.toList() );
    }
    
    /**
     * Find the next id game.
     *
     * @return the id game
     */
    private Integer findNextId() {
        OptionalInt optionalInt = games.stream().mapToInt(Game::getId).max();
        return ( optionalInt.isPresent() ) ? optionalInt.getAsInt() + 1 : 0;
    }
    
    
    /**
     * Update the game from music result.
     *
     * @param musicResult the music result
     * @return the game
     * @throws NotFoundException if the game, music or profile is not found
     * @throws ConflictException the conflict exception
     */
    public Game apply(MusicResult musicResult) throws NotFoundException, ConflictException {
        
        checkAndFillMusicResult(musicResult);
        
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
            
            // TODO refactor en supprimant car BeanFactory n'existe plus pour la class ROUND
            game.nextStep(prop);
        }
        
        return game;
    }
    
    /**
     * Check the MusicResult. Fill the MusicResult for prepare Game.apply.
     *
     * @param musicResult the musicResult
     */
    private void checkAndFillMusicResult(MusicResult musicResult) {
        
        CommonUtils.verifyValue("musicResult", musicResult);
        CommonUtils.verifyValue("musicResult -> gameId", musicResult.getGameId() );
        musicService.checkDTO( musicResult.getMusic() );
        
        musicResult.setAuthorWinners( CommonUtils.removeDuplicate(musicResult.getAuthorWinners()) );
        musicResult.setTitleWinners( CommonUtils.removeDuplicate(musicResult.getTitleWinners()) );
        musicResult.setLosers( Objects.requireNonNullElse(musicResult.getTitleWinners(), new ArrayList<>()) );
        musicResult.setPenalties( CommonUtils.removeDuplicate(musicResult.getPenalties()) );
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
    public Game findById(Integer id) throws NotFoundException {
        
        CommonUtils.verifyValue("id", id);
        
        return games.stream()
                .filter( game -> game.getId().equals(id) )
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Game not found."));
    }
    
    /**
     * Find a page of Game.
     *
     * @param pageNumber        the page number
     * @param itemPerPage       the item per page
     * @param showFinishedGames TRUE if show finished game, FALSE otherwise
     * @return the pageable of game
     */
    public Page<Game> findAll(int pageNumber, int itemPerPage, boolean showFinishedGames) {
        
        itemPerPage = Math.max(itemPerPage, 1);
        itemPerPage = Math.min(itemPerPage, Constant.ITEM_PER_PAGE_MAX);
        
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id").ignoreCase();
        Pageable pageable = PageRequest.of( pageNumber, itemPerPage, Sort.by(order) );
        
        int start = itemPerPage * pageNumber;
        int end = start + itemPerPage;
        
        List<Game> filteredGames = games.stream().filter( g -> g.isFinished() == showFinishedGames ).collect( Collectors.toList() );
        
        Page<Game> page;
        
        if ( start > filteredGames.size() )
            page = new PageImpl<>( new ArrayList<>(), pageable, filteredGames.size());
        else if ( end > filteredGames.size() )
            page = new PageImpl<>( filteredGames.subList(start, filteredGames.size()), pageable, filteredGames.size());
        else
            page = new PageImpl<>( filteredGames.subList(start, end), pageable, filteredGames.size());
        
        return page;
    }
    
}
