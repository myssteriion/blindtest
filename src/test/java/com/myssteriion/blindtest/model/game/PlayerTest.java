package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest extends AbstractTest {

    @Test
    public void constructor() {

        String name = "name";


        try {
            new Player(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        try {
            new Player("");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        Assert.assertNotNull( new Player(name) );



        Player player = new Player(name + "'a'b'c''");
        Assert.assertEquals( name + "'a'b'c''", player.getName() );
        Assert.assertEquals( 0, player.getScore() );
    }

    @Test
    public void getterSetter() {

        String name = "name";

        Player player = new Player(name);
        Assert.assertEquals( name, player.getName() );
        Assert.assertEquals( 0, player.getScore() );
    }

    @Test
    public void toStringAndEquals() {

        String name = "name";
        Player playerUn = new Player(name);

        Assert.assertEquals( "name=name, score=0, turnToChoose=false", playerUn.toString() );

        Player playerUnIso = new Player(name);
        Player playerDeux = new Player(name + "1");


        Assert.assertNotEquals(playerUn, null);
        Assert.assertNotEquals(playerUn, "bad class");
        Assert.assertEquals(playerUn, playerUn);
        Assert.assertEquals(playerUn, playerUnIso);
        Assert.assertNotEquals(playerUn, playerDeux);


        Assert.assertEquals(playerUn.hashCode(), playerUn.hashCode());
        Assert.assertEquals(playerUn.hashCode(), playerUnIso.hashCode());
        Assert.assertNotEquals(playerUn.hashCode(), playerDeux.hashCode());
    }

    @Test
    public void comparator() {

        String name = "name";
        Player playerUn = new Player(name);
        Player playerUnIso = new Player(name);
        Player playerDeux = new Player(name + "1");

        Assert.assertEquals( 0, Player.COMPARATOR.compare(playerUn, playerUn) );
        Assert.assertEquals( 0, Player.COMPARATOR.compare(playerUn, playerUnIso) );
        Assert.assertEquals( -1, Player.COMPARATOR.compare(playerUn, playerDeux) );
        Assert.assertEquals( 1, Player.COMPARATOR.compare(playerDeux, playerUn) );

        Assert.assertEquals( 0, Player.COMPARATOR.compare(null, null) );
        Assert.assertEquals( 1, Player.COMPARATOR.compare(playerUn, null) );
        Assert.assertEquals( -1, Player.COMPARATOR.compare(null, playerDeux) );
    }
    
}