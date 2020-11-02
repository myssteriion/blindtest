package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

class GameControllerTest extends AbstractTest {
    
    @Mock
    private GameService gameService;
    
    @InjectMocks
    private GameController gameController;
    
    
    
    @Test
    void newGame() throws NotFoundException {
        
        Integer profileId = 0;
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Mockito.when(gameService.newGame( Mockito.any(NewGame.class) )).thenReturn(new Game(players, Duration.NORMAL, null, null));
        
        NewGame newGame = new NewGame()
                .setProfilesId( new HashSet<>(Collections.singletonList(profileId)) )
                .setDuration(Duration.NORMAL);
        
        ResponseEntity<Game> re = gameController.newGame(newGame);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertNotNull( re.getBody() );
    }
    
    @Test
    void apply() throws NotFoundException, ConflictException {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Mockito.when(gameService.apply( Mockito.any(MusicResult.class) )).thenReturn(new Game(players, Duration.NORMAL, null, null));
        
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_60);
        MusicResult musicResult = new MusicResult().setGameId(0).setMusic(music);
        
        ResponseEntity<Game> re = gameController.apply(musicResult);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertNotNull( re.getBody() );
        Assertions.assertEquals( players.size(), re.getBody().getPlayers().size() );
    }
    
    @Test
    void findById() throws NotFoundException {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        
        Game game = new Game(players, Duration.NORMAL, null, null);
        game.setId(11);
        Mockito.when(gameService.findById( Mockito.anyInt()) ).thenReturn(game);
        
        ResponseEntity<Game> re = gameController.findById(game.getId());
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertNotNull( re.getBody() );
        Assertions.assertSame( game, re.getBody() );
    }
    
    @Test
    void findAll() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        
        Game game = new Game(players, Duration.NORMAL, null, null);
        game.setId(11);
        Mockito.when(gameService.findAll( Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean() )).thenReturn( new PageImpl<>(Collections.singletonList(game)) );
        
        ResponseEntity< Page<Game> > re = gameController.findAll(0, 1, false);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertNotNull( re.getBody() );
        Assertions.assertSame( game, re.getBody().getContent().get(0) );
    }
    
}