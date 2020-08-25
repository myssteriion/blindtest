package com.myssteriion.blindtest.tools;


import java.util.Random;

/**
 * Constant class.
 */
public class Constant {
    
    public static final Random RANDOM = new Random();
    
    public static final Integer MAX_PLAYERS     = 16;
    public static final Integer MIN_PLAYERS     = 2;
    
    
    public static final Integer ITEM_PER_PAGE_MAX       = 50;
    
    public static final String NAME                     = "name";
    public static final String NAME_DEFAULT_VALUE       = "";
    
    public static final String SHOW_FINISHED_GAMES                  = "showFinishedGames";
    public static final String SHOW_FINISHED_GAMES_DEFAULT_VALUE    = "false";
    
    public static final String THEMES       = "themes";
    public static final String EFFECTS      = "effects";
    
    public static final String DEFAULT_BACKGROUND       = "'#cccccc'";
    
    
    private Constant() {}
    
}
