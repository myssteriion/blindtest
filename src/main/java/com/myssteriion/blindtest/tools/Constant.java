package com.myssteriion.blindtest.tools;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Constant class.
 */
public class Constant {

	public static final String BASE_DIR				= new File(".").getAbsolutePath();
	public static final String MUSICS_FOLDER		= "musiques";
	public static final String AVATAR_FOLDER		= "avatar";

	public static final String FEAT 				= " feat ";
	public static final Integer LIMIT 				= 100;

	public static final String DEFAULT_AVATAR		    = "defaut.png";
	public static final List<String> IMAGE_EXTENSIONS   = Arrays.asList(".png", ".jpg", ".jpeg");
	public static final List<String> AUDIO_EXTENSIONS   = Arrays.asList(".mp3", ".wav", ".aac", ".wma");
    public static final String WAV_CONTENT_TYPE		    = "audio/wav";

	public static final String EMPTY_JSON	 		    	= "{}";

	public static final String ID							= "id";
	public static final String ID_PATH_PARAM				= "/{" + ID + "}";
	public static final String THEMES 						= "themes";
	public static final String GAME_MODE 					= "gameMode";
	public static final String PAGE_NUMBER 					= "pageNumber";
	public static final String PAGE_NUMBER_DEFAULT_VALUE 	= "0";
	public static final String PREFIX_NAME					= "prefixName";
	public static final String PREFIX_NAME_DEFAULT_VALUE	= "";
	public static final String PROFILES_IDS					= "profilesIds";
	public static final String PROFILES_IDS_DEFAULT_VALUE	= "";

	private Constant() {}

}
