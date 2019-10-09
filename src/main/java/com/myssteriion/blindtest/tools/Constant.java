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

	public static final String ID							= "id";
	public static final String ID_PATH_PARAM				= "/{" + ID + "}";
	public static final String PAGE_NUMBER 					= "pageNumber";
	public static final String PAGE_NUMBER_DEFAULT_VALUE 	= "0";
	public static final String PREFIX_NAME					= "prefixName";
	public static final String PREFIX_NAME_DEFAULT_VALUE	= "";
	public static final String PROFILES_IDS					= "profilesIds";
	public static final String PROFILES_IDS_DEFAULT_VALUE	= "";

	private Constant() {}

}
