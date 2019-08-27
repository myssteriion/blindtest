package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
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

        List<String> playersNames = Arrays.asList("name", "name2");
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(new HashSet<>(playersNames), duration);


        Assert.assertTrue( Round.CLASSIC.createRoundContent(gameDTO) instanceof ClassicContent );
        Assert.assertFalse( gameDTO.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( gameDTO.getPlayers().get(1).isTurnToChoose() );

        Assert.assertTrue( Round.CHOICE.createRoundContent(gameDTO) instanceof ChoiceContent);
        Assert.assertTrue( gameDTO.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( gameDTO.getPlayers().get(1).isTurnToChoose() );

        Assert.assertTrue( Round.THIEF.createRoundContent(gameDTO) instanceof ThiefContent);
        Assert.assertTrue( gameDTO.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( gameDTO.getPlayers().get(1).isTurnToChoose() );
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