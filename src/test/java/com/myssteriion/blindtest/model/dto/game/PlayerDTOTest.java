package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class PlayerDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        String name = "name";
        int score = 0;


        try {
            new PlayerDTO(null, score);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        try {
            new PlayerDTO("", score);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        Assert.assertNotNull( new PlayerDTO(name, score) );



        PlayerDTO playerDto = new PlayerDTO(name + "'a'b'c''", score);
        Assert.assertEquals( name + "'a'b'c''", playerDto.getName() );
        Assert.assertEquals( 0, playerDto.getScore() );

        playerDto = new PlayerDTO(name, -2);
        Assert.assertEquals( 0, playerDto.getScore() );
    }

    @Test
    public void getterSetter() {

        String name = "name";
        int score = 0;

        PlayerDTO playerDto = new PlayerDTO(name, score);
        Assert.assertEquals( name, playerDto.getName() );
        Assert.assertEquals( score, playerDto.getScore() );
    }

    @Test
    public void toStringAndEquals() {

        String name = "name";
        int score = 0;
        PlayerDTO playerDtoUn = new PlayerDTO(name, score);

        Assert.assertEquals( "name=name, score=0", playerDtoUn.toString() );

        PlayerDTO musicDTOUnIso = new PlayerDTO(name, score);
        PlayerDTO musicDTODeux = new PlayerDTO(name + "1", score);
        PlayerDTO musicDTOTrois = new PlayerDTO(name, 10);
        PlayerDTO musicDTOQuatre = new PlayerDTO(name + "1", 10);

        Assert.assertNotEquals(playerDtoUn, null);
        Assert.assertNotEquals(playerDtoUn, "bad class");
        Assert.assertEquals(playerDtoUn, playerDtoUn);
        Assert.assertEquals(playerDtoUn, musicDTOUnIso);
        Assert.assertNotEquals(playerDtoUn, musicDTODeux);
        Assert.assertNotEquals(playerDtoUn, musicDTOTrois);
        Assert.assertNotEquals(playerDtoUn, musicDTOQuatre);

        Assert.assertEquals(playerDtoUn.hashCode(), playerDtoUn.hashCode());
        Assert.assertEquals(playerDtoUn.hashCode(), musicDTOUnIso.hashCode());
        Assert.assertNotEquals(playerDtoUn.hashCode(), musicDTODeux.hashCode());
        Assert.assertNotEquals(playerDtoUn.hashCode(), musicDTOTrois.hashCode());
        Assert.assertNotEquals(playerDtoUn.hashCode(), musicDTOQuatre.hashCode());
    }
    
}