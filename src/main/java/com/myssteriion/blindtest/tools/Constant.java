package com.myssteriion.blindtest.tools;

import java.io.File;

/**
 * Constant class.
 */
public class Constant {

	public static final String BASE_DIR				= new File(".").getAbsolutePath();
	public static final String MUSICS_FOLDER		= "musiques";
	public static final String AVATAR_FOLDER		= "avatar";

	public static final String DEFAULT_AVATAR		= "defaut.png";
	public static final String MP3_EXTENSION		= ".mp3";

	public static final String EMPTY_JSON	 		= "{}";

	public static final String ID					= "id";
	public static final String ID_PATH_PARAM		= "/{" + ID + "}";
	public static final String PAGE					= "page";
	public static final String PAGE_DEFAULT_VALUE	= "0";

	private Constant() {}

}
