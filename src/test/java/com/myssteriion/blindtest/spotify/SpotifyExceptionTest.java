package com.myssteriion.blindtest.spotify;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.spotify.SpotifyException;
import org.junit.Assert;
import org.junit.Test;

public class SpotifyExceptionTest extends AbstractTest {

    @Test
    public void constructor() {

        String message = "failed";

        try {
            new SpotifyException(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        try {
            new SpotifyException("");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        SpotifyException se = new SpotifyException(message);
        Assert.assertEquals( message, se.getMessage() );




        try {
            new SpotifyException(message, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'cause' est obligatoire."), e);
        }

        IllegalArgumentException iae = new IllegalArgumentException("iae");

        se = new SpotifyException(message, iae);
        Assert.assertEquals( message, se.getMessage() );
        Assert.assertEquals( iae, se.getCause() );
    }
    
}