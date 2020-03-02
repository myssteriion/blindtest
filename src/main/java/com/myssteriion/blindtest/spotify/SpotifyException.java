package com.myssteriion.blindtest.spotify;

import com.myssteriion.utils.CommonUtils;

/**
 * The Spotify exception.
 */
public class SpotifyException extends Exception {
    
    /**
     * Instantiates a new Spotify exception.
     *
     * @param message the message
     */
    public SpotifyException(String message) {
        
        super(message);
        CommonUtils.verifyValue("message", message);
    }
    
    /**
     * Instantiates a new Spotify exception.
     *
     * @param message	the message
     * @param cause		the cause
     */
    public SpotifyException(String message, Throwable cause) {
        super(message, cause);
        CommonUtils.verifyValue("message", message);
        CommonUtils.verifyValue("cause", cause);
    }
    
}
