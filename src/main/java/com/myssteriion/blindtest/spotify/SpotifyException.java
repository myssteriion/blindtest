package com.myssteriion.blindtest.spotify;

import com.myssteriion.utils.Tools;

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
		Tools.verifyValue("message", message);
	}

	/**
	 * Instantiates a new Spotify exception.
	 *
	 * @param message	the message
	 * @param cause		the cause
	 */
	public SpotifyException(String message, Throwable cause) {
		super(message, cause);
		Tools.verifyValue("message", message);
		Tools.verifyValue("cause", cause);
	}

}
