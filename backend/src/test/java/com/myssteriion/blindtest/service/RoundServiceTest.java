package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.RoundName;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.model.round.AbstractRound;
import com.myssteriion.blindtest.model.round.impl.Choice;
import com.myssteriion.blindtest.model.round.impl.Classic;
import com.myssteriion.blindtest.model.round.impl.Friendship;
import com.myssteriion.blindtest.model.round.impl.Lucky;
import com.myssteriion.blindtest.model.round.impl.Recovery;
import com.myssteriion.blindtest.model.round.impl.Thief;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class RoundServiceTest extends AbstractTest {
    
    private RoundService roundService;
    
    
    @BeforeEach
    void before() {
        roundService = new RoundService(roundProperties);
    }
    
    
    
    @Test
    void createFirstRound() {
        
        TestUtils.assertThrowMandatoryField("game", () -> roundService.createFirstRound(null) );
        
        Game game = new Game();
        TestUtils.assertThrowMandatoryField("game -> duration", () -> roundService.createFirstRound(game) );
        
        game.setDuration(Duration.NORMAL)
                .setPlayers(Arrays.asList(
                        new Player().setProfile(new ProfileEntity().setName("name1")),
                        new Player().setProfile(new ProfileEntity().setName("name2"))
                ));
        
        AbstractRound round = roundService.createFirstRound(game);
        Assertions.assertTrue(round instanceof Classic);
    }
    
    @Test
    void createNextRound() {
        
        TestUtils.assertThrowMandatoryField("game", () -> roundService.createNextRound(null) );
        
        Game game = new Game();
        TestUtils.assertThrowMandatoryField("game -> duration", () -> roundService.createFirstRound(game) );
        
        game.setDuration(Duration.NORMAL)
                .setPlayers(Arrays.asList(
                        new Player().setProfile(new ProfileEntity().setName("name1")),
                        new Player().setProfile(new ProfileEntity().setName("name2"))
                ));
        
        game.setRound( roundService.createFirstRound(game) );
        Assertions.assertTrue(game.getRound() instanceof Classic);
        
        game.setRound( roundService.createNextRound(game) );
        Assertions.assertTrue(game.getRound() instanceof Choice);
        
        game.setRound( roundService.createNextRound(game) );
        Assertions.assertTrue(game.getRound() instanceof Lucky);
        
        game.setRound( roundService.createNextRound(game) );
        Assertions.assertTrue(game.getRound() instanceof Friendship);
        
        game.setRound( roundService.createNextRound(game) );
        Assertions.assertTrue(game.getRound() instanceof Thief);
        
        game.setRound( roundService.createNextRound(game) );
        Assertions.assertTrue(game.getRound() instanceof Recovery);
        
        game.setRound( roundService.createNextRound(game) );
        Assertions.assertNull( game.getRound() );
        
        game.setRound( roundService.createNextRound(game) );
        Assertions.assertNull( game.getRound() );
    }
    
    @Test
    void isLastRound() {
        
        Assertions.assertFalse( roundService.isLastRound(null));
        
        Assertions.assertFalse( roundService.isLastRound(RoundName.CLASSIC));
        Assertions.assertFalse( roundService.isLastRound(RoundName.CHOICE));
        Assertions.assertFalse( roundService.isLastRound(RoundName.LUCKY));
        Assertions.assertFalse( roundService.isLastRound(RoundName.FRIENDSHIP));
        Assertions.assertFalse( roundService.isLastRound(RoundName.THIEF));
        Assertions.assertTrue( roundService.isLastRound(RoundName.RECOVERY));
    }
    
}