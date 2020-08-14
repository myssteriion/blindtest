package com.myssteriion.blindtest.model.dto.param;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.persistence.converter.theme.ThemeStringMapConverter;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.model.dto.AbstractDTO;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The spotify dto.
 */
@Entity
@Table(name = "spotify_param")
@SequenceGenerator(name = "sequence_id", sequenceName = "spotify_param_sequence", allocationSize = 1)
public class SpotifyParamDTO extends AbstractDTO {
    
    @Column(name = "client_id")
    private String clientId;
    
    @Column(name = "client_secret")
    private String clientSecret;
    
    @Column(name = "playlist_ids", length = 500)
    @Convert(converter = ThemeStringMapConverter.class)
    private Map<Theme, String> playlistIds;
    
    
    
    /**
     * Instantiate a new SpotifyParam dto.
     */
    public SpotifyParamDTO() {
        this( "", "", new HashMap<>() );
    }
    
    /**
     * Instantiate a new SpotifyParam dto.
     *
     * @param clientId     the client id
     * @param clientSecret the client secret
     * @param playlistIds  the playlist ids
     */
    public SpotifyParamDTO(String clientId, String clientSecret, Map<Theme, String> playlistIds) {
        
        this.clientId = CommonUtils.isNullOrEmpty(clientId) ? "" : clientId;
        this.clientSecret = CommonUtils.isNullOrEmpty(clientSecret) ? "" : clientSecret;
        this.playlistIds = CommonUtils.isNullOrEmpty(playlistIds) ? new HashMap<>() : playlistIds;
    }
    
    
    
    public String getClientId() {
        return clientId;
    }
    
    public SpotifyParamDTO setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }
    
    public String getClientSecret() {
        return clientSecret;
    }
    
    public SpotifyParamDTO setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }
    
    public Map<Theme, String> getPlaylistIds() {
        return playlistIds;
    }
    
    public SpotifyParamDTO setPlaylistIds(Map<Theme, String> playlistIds) {
        this.playlistIds = playlistIds;
        return this;
    }
    
    
    @Override
    public boolean equals(Object o) {
        
        if (this == o)
            return true;
        
        if (o == null || getClass() != o.getClass())
            return false;
        
        SpotifyParamDTO that = (SpotifyParamDTO) o;
        return Objects.equals(clientId, that.clientId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
    
    @Override
    public String toString() {
        return "clientId=" + clientId +
                ", playListId=" + playlistIds;
    }
    
}
