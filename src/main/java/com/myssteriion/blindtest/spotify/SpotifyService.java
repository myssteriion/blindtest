package com.myssteriion.blindtest.spotify;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.blindtest.service.param.SpotifyParamService;
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
    private SpotifyParamService spotifyParamService;
    
    
    
    /**
     * Instantiates a new Spotify service.
     *
     * @param spotifyParamService the SpotifyParam service
     */
    @Autowired
    public SpotifyService(SpotifyParamService spotifyParamService) {
        this.spotifyParamService = spotifyParamService;
    }
    
    
    
    /**
     * Gets spotify api.
     *
     * @throws SpotifyException the spotify exception
     */
    private SpotifyApi getSpotifyApi() throws SpotifyException {
        
        try {
            
            SpotifyParamDTO spotifyParam = spotifyParamService.find();
            
            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setClientId( spotifyParam.getClientId() )
                    .setClientSecret( spotifyParam.getClientSecret() )
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
     *
     * @throws SpotifyException the spotify exception
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
            
            String playlistId = spotifyParamService.find().getPlaylistIds().get(theme);
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
            
            String playlistId = spotifyParamService.find().getPlaylistIds().get(theme);
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
    
    
    
    /**
     * Test the connection with the param.
     *
     * @throws SpotifyException the spotify exception
     */
    public void testSpotifyParamConnection(SpotifyParamDTO spotifyParam) throws SpotifyException {
        
        CommonUtils.verifyValue("spotifyParam", spotifyParam);
        
        SpotifyApi spotifyApi = credentialTest(spotifyParam);
        
        for ( Theme theme : Theme.getSortedTheme() ) {
            
            if ( !spotifyParam.getPlaylistIds().containsKey(theme) )
                throw new SpotifyException("playlist id is mandatory (theme: '" + theme + "').");
            
            String playlistId = spotifyParam.getPlaylistIds().get(theme);
            if ( CommonUtils.isNullOrEmpty(playlistId) )
                throw new SpotifyException("playlist id is mandatory (theme: '" + theme + "').");
            
            try {
                spotifyApi.getPlaylist(playlistId).build().execute();
            }
            catch (IOException | SpotifyWebApiException e) {
                throw new SpotifyException("Playlist id is not found (theme: '" + theme + "', id: '" + playlistId +"').", e);
            }
        }
    }
    
    /**
     * Credentials test.
     *
     * @param spotifyParam the spotifyParam
     * @return the SpotifyApi if credentials are valid, throw otherwise.
     * @throws SpotifyException the spotify exception
     */
    private SpotifyApi credentialTest(SpotifyParamDTO spotifyParam) throws SpotifyException {
        
        try {
            
            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setClientId( spotifyParam.getClientId() )
                    .setClientSecret( spotifyParam.getClientSecret() )
                    .build();
            
            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            spotifyApi.setAccessToken( clientCredentialsRequest.execute().getAccessToken() );
            
            return spotifyApi;
        }
        catch (IOException | SpotifyWebApiException e) {
            LOGGER.warn("Can't create spotify connection.");
            throw new SpotifyException("Login or password are incorrect.", e);
        }
    }
    
}

