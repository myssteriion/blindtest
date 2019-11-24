package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class RoundTest extends AbstractTest {

    @Test
    public void getter() {
        Assert.assertEquals( 0, Round.getFirst().getRoundNumber() );
    }

    @Test
    public void nextRound() {
        Assert.assertEquals( Round.CHOICE, Round.CLASSIC.nextRound() );
        Assert.assertEquals( Round.THIEF, Round.CHOICE.nextRound() );
        Assert.assertNull( Round.THIEF.nextRound() );
    }

    @Test
    public void createRoundContent(){

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name2")));
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(players), duration, null, GameMode.OFFLINE);


        Assert.assertTrue( Round.CLASSIC.createRoundContent(game) instanceof ClassicContent );
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );

        Assert.assertTrue( Round.CHOICE.createRoundContent(game) instanceof ChoiceContent);
        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );

        Assert.assertTrue( Round.THIEF.createRoundContent(game) instanceof ThiefContent);
        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
    }

    @Test
    public void isLast() {

        Assert.assertFalse( Round.CLASSIC.isLast() );
        Assert.assertFalse( Round.CHOICE.isLast() );
        Assert.assertTrue( Round.THIEF.isLast() );
    }

    @Test
    public void getFirst() {
        Assert.assertEquals( Round.CLASSIC, Round.getFirst() );
    }

}