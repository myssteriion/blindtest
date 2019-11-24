package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpotifyPropertiesTest extends AbstractTest {

    @Autowired
    private SpotifyProperties props;



    @Test
    public void getClientId() {
        Assert.assertEquals("123a", props.getClientId() );
    }

    @Test
    public void getClientSecret() {
        Assert.assertEquals("123b", props.getClientSecret() );
    }

    @Test
    public void getPlaylistIdByTheme() {
        Assert.assertEquals("123c", props.getPlaylistIdByTheme(Theme.ANNEES_60) );
        Assert.assertEquals("123d", props.getPlaylistIdByTheme(Theme.ANNEES_70) );
        Assert.assertEquals("123e", props.getPlaylistIdByTheme(Theme.ANNEES_80) );
        Assert.assertEquals("123f", props.getPlaylistIdByTheme(Theme.ANNEES_90) );
        Assert.assertEquals("123g", props.getPlaylistIdByTheme(Theme.ANNEES_2000) );
        Assert.assertEquals("123h", props.getPlaylistIdByTheme(Theme.ANNEES_2010) );
        Assert.assertEquals("123i", props.getPlaylistIdByTheme(Theme.SERIES_CINEMAS) );
        Assert.assertEquals("123j", props.getPlaylistIdByTheme(Theme.DISNEY) );
        Assert.assertEquals("123k", props.getPlaylistIdByTheme(Theme.JEUX) );
        Assert.assertEquals("123l", props.getPlaylistIdByTheme(Theme.CLASSIQUES) );
    }

}