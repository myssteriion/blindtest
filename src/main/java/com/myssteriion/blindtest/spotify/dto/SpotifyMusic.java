package com.myssteriion.blindtest.spotify.dto;

import com.myssteriion.blindtest.tools.Tool;

import java.util.Objects;

/**
 * The Spotify music.
 */
public class SpotifyMusic {

    private static final String URL_PREFIX = "https://open.spotify.com/embed/track/";

    /**
     * The track id.
     */
    private String trackId;

    /**
     * The preview url.
     */
    private String previewUrl;

    /**
     * The track url.
     */
    private String trackUrl;

    /**
     * The artists.
     */
    private String artists;

    /**
     * The name.
     */
    private String name;


    /**
     * Instantiates a new Spotify music.
     *
     * @param trackId    the track id
     * @param previewUrl the preview url
     * @param artists    the artists
     * @param name       the name
     */
    public SpotifyMusic(String trackId, String previewUrl, String artists, String name) {

        Tool.verifyValue("trackId", trackId);
        Tool.verifyValue("previewUrl", previewUrl);
        Tool.verifyValue("artists", artists);
        Tool.verifyValue("name", name);

        this.trackId = trackId;
        this.previewUrl = previewUrl;
        this.trackUrl = URL_PREFIX + this.trackId;
        this.artists = artists;
        this.name = name;
    }


    /**
     * Gets track id.
     *
     * @return the track id
     */
    public String getTrackId() {
        return trackId;
    }

    /**
     * Gets preview url.
     *
     * @return the preview url
     */
    public String getPreviewUrl() {
        return previewUrl;
    }

    /**
     * Gets track url.
     *
     * @return the track url
     */
    public String getTrackUrl() {
        return trackUrl;
    }

    /**
     * Gets artists.
     *
     * @return the artists
     */
    public String getArtists() {
        return artists;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpotifyMusic that = (SpotifyMusic) o;
        return Objects.equals(trackId, that.trackId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId);
    }

    @Override
    public String toString() {
        return "trackId=" + trackId +
               ", previewUrl=" + previewUrl +
               ", trackUrl=" + trackUrl +
                ", artists=" + artists +
                ", name=" + name;
    }

}
