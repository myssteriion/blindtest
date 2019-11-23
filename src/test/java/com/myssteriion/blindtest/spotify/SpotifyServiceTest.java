package com.myssteriion.blindtest.spotify;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.spotify.exception.SpotifyException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpotifyServiceTest extends AbstractTest {

    @Autowired
    private SpotifyService spotifyService;



    @Test
    public void isConnected() {
        Assert.assertFalse( spotifyService.isConnected() );
    }

    @Test
    public void testConnection() {

        try {
            spotifyService.testConnection();
            Assert.fail("Doit lever SpotifyException une car le mock throw.");
        }
        catch (SpotifyException e) {
            Assert.assertEquals( "Can't create spotify connection.", e.getMessage() );
        }
    }

}