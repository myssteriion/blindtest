package com.myssteriion.blindtest.spotify;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.properties.SpotifyProperties;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonUtils;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The spotify service.
 */
@Service
public class SpotifyService {

    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);

    /**
     * The spotify properties.
     */
    private SpotifyProperties spotifyProperties;



    /**
     * Instantiates a new Spotify service.
     *
     * @param spotifyProperties the spotify properties
     */
    @Autowired
    public SpotifyService(SpotifyProperties spotifyProperties) {
        this.spotifyProperties = spotifyProperties;
    }



    /**
     * Gets spotify api.
     */
    private SpotifyApi getSpotifyApi() throws SpotifyException {

        try {

            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setClientId( spotifyProperties.getClientId() )
                    .setClientSecret( spotifyProperties.getClientSecret() )
                    .build();

            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            spotifyApi.setAccessToken( clientCredentialsRequest.execute().getAccessToken() );

            return spotifyApi;
        }
        catch (IOException | SpotifyWebApiException e) {
            LOGGER.warn("Can't create spotify connection.");
            throw new SpotifyException("Can't create spotify connection.", e);
        }
    }

    /**
     * If the connection is ok.
     *
     * @return TRUE if the connection is ok, FALSE otherwise.
     */
    public boolean isConnected() {

        try {
            return !CommonUtils.isNullOrEmpty( getSpotifyApi() );
        }
        catch (SpotifyException e) {
            return false;
        }
    }

    /**
     * Test the connection.
     */
    public void testConnection() throws SpotifyException {
        getSpotifyApi();
    }

    /**
     * Gets musics by theme.
     *
     * @param theme the theme
     * @return the musics by theme
     * @throws SpotifyException the spotify exception
     */
    public List<SpotifyMusic> getMusicsByTheme(Theme theme) throws SpotifyException {

        try {

            List<SpotifyMusic> spotifyMusics = new ArrayList<>();

            String playlistId = spotifyProperties.getPlaylistIdByTheme(theme);
            SpotifyApi spotifyApi = getSpotifyApi();

            boolean hasNext = true;
            int currentOffset = 0;
            while (hasNext) {

                Paging<PlaylistTrack> page = spotifyApi.getPlaylistsTracks(playlistId).offset(currentOffset).limit(Constant.LIMIT).build().execute();
                hasNext = !CommonUtils.isNullOrEmpty( page.getNext() );
                currentOffset += Constant.LIMIT;

                for ( PlaylistTrack playlistTrack : page.getItems() ) {

                    Track track = playlistTrack.getTrack();

                    StringBuilder artists = new StringBuilder(track.getArtists()[0].getName());
                    for (int i = 1; i < track.getArtists().length; i++)
                        artists.append(Constant.FEAT).append(track.getArtists()[i].getName());

                    if ( CommonUtils.isNullOrEmpty(track.getPreviewUrl()) )
                        LOGGER.warn("The music haven't preview ('" + track.getId() + "', '" + theme + "'" + "', '" + track.getName() + "', '" + artists.toString() + "')");
                    else
                        spotifyMusics.add( new SpotifyMusic(track.getId(), track.getPreviewUrl(), artists.toString(), track.getName()) );
                }
            }

            return spotifyMusics;
        }
        catch (IOException | SpotifyWebApiException e) {
            throw new SpotifyException("Can't get tracks.", e);
        }
    }

    /**
     * Test if the track exists in theme.
     *
     * @param trackId the track id
     * @param theme   the theme
     * @return TRUE if the track exists, FALSE otherwise.
     * @throws SpotifyException the spotify exception
     */
    public boolean trackExistsInTheme(String trackId, Theme theme) throws SpotifyException {

        try {

            boolean isFound = false;

            String playlistId = spotifyProperties.getPlaylistIdByTheme(theme);
            SpotifyApi spotifyApi = getSpotifyApi();

            boolean hasNext = true;
            int currentOffset = 0;
            while (hasNext && !isFound) {

                Paging<PlaylistTrack> page = spotifyApi.getPlaylistsTracks(playlistId).offset(currentOffset).limit(Constant.LIMIT).build().execute();
                hasNext = !CommonUtils.isNullOrEmpty( page.getNext() );
                currentOffset += Constant.LIMIT;

                List<PlaylistTrack> list = Arrays.asList( page.getItems() );
                isFound = list.stream().anyMatch( track -> track.getTrack().getId().equals(trackId) );
            }

            return isFound;
        }
        catch (NotFoundException e) {
            return false;
        }
        catch (IOException | SpotifyWebApiException e) {
            throw new SpotifyException("Can't get track.", e);
        }
    }

}

