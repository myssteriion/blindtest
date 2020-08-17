package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GameControllerTest extends AbstractTest {
    
    @Mock
    private GameService gameService;
    
    @InjectMocks
    private GameController gameController;
    
    
    
    @Test
    public void newGame() throws NotFoundException, SpotifyException {
        
        Integer profileId = 0;
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Mockito.when(gameService.newGame( Mockito.any(NewGame.class) )).thenReturn(new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties));
        
        NewGame newGame = new NewGame()
            .setProfilesId( new HashSet<>(Collections.singletonList(profileId)) )
            .setDuration(Duration.NORMAL)
            .setConnectionMode(ConnectionMode.OFFLINE);
        
        ResponseEntity<Game> re = gameController.newGame(newGame);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertNotNull( re.getBody() );
    }
    
    @Test
    public void apply() throws NotFoundException, ConflictException {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Mockito.when(gameService.apply( Mockito.any(MusicResult.class) )).thenReturn(new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties));
        
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60, ConnectionMode.OFFLINE);
        MusicResult musicResult = new MusicResult().setGameId(0).setMusic(musicDto);
        
        ResponseEntity<Game> re = gameController.apply(musicResult);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertNotNull( re.getBody() );
        Assert.assertEquals( players.size(), re.getBody().getPlayers().size() );
    }
    
    @Test
    public void findById() throws NotFoundException {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        
        Game game = new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        game.setId(11);
        Mockito.when(gameService.findById( Mockito.anyInt()) ).thenReturn(game);
        
        ResponseEntity<Game> re = gameController.findById(game.getId());
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertNotNull( re.getBody() );
        Assert.assertSame( game, re.getBody() );
    }
    
    @Test
    public void findAll() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        
        Game game = new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        game.setId(11);
        Mockito.when(gameService.findAll( Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean() )).thenReturn( new PageImpl<>(Collections.singletonList(game)) );
        
        ResponseEntity< Page<Game> > re = gameController.findAll(0, 1, false);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertNotNull( re.getBody() );
        Assert.assertSame( game, re.getBody().getContent().get(0) );
    }
    
}
