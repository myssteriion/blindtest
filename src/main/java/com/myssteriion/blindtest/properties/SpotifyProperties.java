package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.model.common.Theme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The mapping with spotify.properties.
 */
@Configuration
@PropertySource("classpath:spotify.properties")
public class SpotifyProperties {

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${playlistId.annees_60}")
    private String playlistIdAnnees60;

    @Value("${playlistId.annees_70}")
    private String playlistIdAnnees70;

    @Value("${playlistId.annees_80}")
    private String playlistIdAnnees80;

    @Value("${playlistId.annees_90}")
    private String playlistIdAnnees90;

    @Value("${playlistId.annees_2000}")
    private String playlistIdAnnees2000;

    @Value("${playlistId.cinemas}")
    private String playlistIdCinemas;

    @Value("${playlistId.series}")
    private String playlistIdSeries;

    @Value("${playlistId.disney}")
    private String playlistIdDisney;

    @Value("${playlistId.classiques}")
    private String playlistIdClassiques;



    /**
     * Gets client id.
     *
     * @return the client id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets client secret.
     *
     * @return the client secret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Gets playlist id for the theme.
     *
     * @param theme the theme
     * @return the playlist id
     */
    public String getPlaylistIdByTheme(Theme theme) {

        switch (theme) {

            case ANNEES_60:     return playlistIdAnnees60;
            case ANNEES_70:     return playlistIdAnnees70;
            case ANNEES_80:     return playlistIdAnnees80;
            case ANNEES_90:     return playlistIdAnnees90;
            case ANNEES_2000:   return playlistIdAnnees2000;
            case CINEMAS:       return playlistIdCinemas;
            case SERIES:        return playlistIdSeries;
            case DISNEY:        return playlistIdDisney;
            case CLASSIQUES:    return playlistIdClassiques;

            default:            throw new IllegalArgumentException("Il manque un case.");
        }
    }

}
