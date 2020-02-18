package com.myssteriion.blindtest.spotify;

import com.myssteriion.blindtest.tools.Tool;

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
		Tool.verifyValue("message", message);
	}

	/**
	 * Instantiates a new Spotify exception.
	 *
	 * @param message	the message
	 * @param cause		the cause
	 */
	public SpotifyException(String message, Throwable cause) {
		super(message, cause);
		Tool.verifyValue("message", message);
		Tool.verifyValue("cause", cause);
	}

}
