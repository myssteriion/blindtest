package com.myssteriion.blindtest.spotify.dto;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class SpotifyMusicTest extends AbstractTest {

    @Test
    public void constructor() {

        String trackId = "trackId";
        String previewUrl = "previewUrl";
        String artists = "artists";
        String name = "name";

        try {
            new SpotifyMusic(null, previewUrl, artists, name);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'trackId' est obligatoire."), e);
        }

        try {
            new SpotifyMusic("", previewUrl, artists, name);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'trackId' est obligatoire."), e);
        }

        try {
            new SpotifyMusic(trackId, null, artists, name);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'previewUrl' est obligatoire."), e);
        }

        try {
            new SpotifyMusic(trackId, "", artists, name);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'previewUrl' est obligatoire."), e);
        }

        try {
            new SpotifyMusic(trackId, previewUrl, null, name);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'artists' est obligatoire."), e);
        }

        try {
            new SpotifyMusic(trackId, previewUrl, "", name);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'artists' est obligatoire."), e);
        }

        try {
            new SpotifyMusic(trackId, previewUrl, artists, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }
        try {
            new SpotifyMusic(trackId, previewUrl, artists, "");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        SpotifyMusic spotifyMusic = new SpotifyMusic(trackId, previewUrl, artists, name);
        Assert.assertEquals( trackId, spotifyMusic.getTrackId() );
        Assert.assertEquals( previewUrl, spotifyMusic.getPreviewUrl() );
        Assert.assertEquals( "https://open.spotify.com/embed/track/" + trackId, spotifyMusic.getTrackUrl() );
        Assert.assertEquals( artists, spotifyMusic.getArtists() );
        Assert.assertEquals( name, spotifyMusic.getName() );
    }

    @Test
    public void getterSetter() {

        String trackId = "trackId";
        String previewUrl = "previewUrl";
        String artists = "artists";
        String name = "name";

        SpotifyMusic spotifyMusic = new SpotifyMusic(trackId, previewUrl, artists, name);
        Assert.assertEquals( trackId, spotifyMusic.getTrackId() );
        Assert.assertEquals( previewUrl, spotifyMusic.getPreviewUrl() );
        Assert.assertEquals( "https://open.spotify.com/embed/track/" + trackId, spotifyMusic.getTrackUrl() );
        Assert.assertEquals( artists, spotifyMusic.getArtists() );
        Assert.assertEquals( name, spotifyMusic.getName() );
    }

    @Test
    public void toStringAndEquals() {

        String trackId = "trackId";
        String previewUrl = "previewUrl";
        String artists = "artists";
        String name = "name";

        SpotifyMusic spotifyMusic = new SpotifyMusic(trackId, previewUrl, artists, name);
        SpotifyMusic spotifyMusicIso1 = new SpotifyMusic(trackId, previewUrl, artists, name);
        SpotifyMusic spotifyMusicIso2 = new SpotifyMusic(trackId, previewUrl + "1", artists + "1", name + "1");
        SpotifyMusic spotifyMusic2 = new SpotifyMusic(trackId + "1", previewUrl, artists, name);

        Assert.assertEquals("trackId=trackId, previewUrl=previewUrl, trackUrl=https://open.spotify.com/embed/track/trackId, artists=artists, name=name", spotifyMusic.toString() );

        Assert.assertNotEquals(spotifyMusic, null);
        Assert.assertNotEquals(spotifyMusic, "bad class");
        Assert.assertEquals(spotifyMusic, spotifyMusicIso1);
        Assert.assertEquals(spotifyMusic, spotifyMusicIso2);
        Assert.assertNotEquals(spotifyMusic, spotifyMusic2);

        Assert.assertEquals(spotifyMusic.hashCode(), spotifyMusicIso1.hashCode());
        Assert.assertEquals(spotifyMusic.hashCode(), spotifyMusicIso2.hashCode());
        Assert.assertNotEquals(spotifyMusic.hashCode(), spotifyMusic2.hashCode());
    }

}