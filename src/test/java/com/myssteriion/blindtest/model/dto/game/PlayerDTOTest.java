package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class PlayerDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        String name = "name";


        try {
            new PlayerDTO(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        try {
            new PlayerDTO("");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        Assert.assertNotNull( new PlayerDTO(name) );



        PlayerDTO playerDto = new PlayerDTO(name + "'a'b'c''");
        Assert.assertEquals( name + "'a'b'c''", playerDto.getName() );
        Assert.assertEquals( 0, playerDto.getScore() );
    }

    @Test
    public void getterSetter() {

        String name = "name";

        PlayerDTO playerDto = new PlayerDTO(name);
        Assert.assertEquals( name, playerDto.getName() );
        Assert.assertEquals( 0, playerDto.getScore() );
    }

    @Test
    public void toStringAndEquals() {

        String name = "name";
        PlayerDTO playerDtoUn = new PlayerDTO(name);

        Assert.assertEquals( "name=name, score=0", playerDtoUn.toString() );

        PlayerDTO musicDTOUnIso = new PlayerDTO(name);
        PlayerDTO musicDTODeux = new PlayerDTO(name + "1");


        Assert.assertNotEquals(playerDtoUn, null);
        Assert.assertNotEquals(playerDtoUn, "bad class");
        Assert.assertEquals(playerDtoUn, playerDtoUn);
        Assert.assertEquals(playerDtoUn, musicDTOUnIso);
        Assert.assertNotEquals(playerDtoUn, musicDTODeux);


        Assert.assertEquals(playerDtoUn.hashCode(), playerDtoUn.hashCode());
        Assert.assertEquals(playerDtoUn.hashCode(), musicDTOUnIso.hashCode());
        Assert.assertNotEquals(playerDtoUn.hashCode(), musicDTODeux.hashCode());
    }
    
}