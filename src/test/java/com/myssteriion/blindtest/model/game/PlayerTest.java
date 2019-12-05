package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest extends AbstractTest {

    @Test
    public void constructor() {

        String name = "name";
        ProfileDTO profile = new ProfileDTO(name);


        try {
            new Player(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }


        Player player = new Player(profile);
        Assert.assertEquals( profile, player.getProfile() );
        Assert.assertEquals( 0, player.getScore() );
        Assert.assertEquals( Rank.FIRST, player.getRank() );
    }

    @Test
    public void getterSetter() {

        String name = "name";
        ProfileDTO profile = new ProfileDTO(name);

        Player player = new Player(profile);
        Assert.assertEquals( profile, player.getProfile() );
        Assert.assertEquals( 0, player.getScore() );
        Assert.assertEquals( Rank.FIRST, player.getRank() );

        player.setRank(Rank.SEVENTH);
        Assert.assertEquals( Rank.SEVENTH, player.getRank() );
    }

    @Test
    public void toStringAndEquals() {

        String name = "name";
        ProfileDTO profile = new ProfileDTO(name);
        Player playerUn = new Player(profile);

        Assert.assertEquals( "profile={" + profile + "}, score=0, rank=FIRST, last=false, turnToChoose=false, foundMusics={}", playerUn.toString() );

        Player playerUnIso = new Player(profile);
        Player playerDeux = new Player(new ProfileDTO(name + "1"));


        Assert.assertNotEquals(playerUn, null);
        Assert.assertNotEquals(playerUn, "bad class");
        Assert.assertEquals(playerUn, playerUn);
        Assert.assertEquals(playerUn, playerUnIso);
        Assert.assertNotEquals(playerUn, playerDeux);


        Assert.assertEquals(playerUn.hashCode(), playerUn.hashCode());
        Assert.assertEquals(playerUn.hashCode(), playerUnIso.hashCode());
        Assert.assertNotEquals(playerUn.hashCode(), playerDeux.hashCode());
    }

}