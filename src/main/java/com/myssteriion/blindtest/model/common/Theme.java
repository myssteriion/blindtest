package com.myssteriion.blindtest.model.common;

import java.util.Arrays;
import java.util.List;

/**
 * The enum Theme.
 */
public enum Theme {

	/**
	 * Annees 60 theme.
	 */
	ANNEES_60("annees_60"),

	/**
	 * Annees 70 theme.
	 */
	ANNEES_70("annees_70"),

	/**
	 * Annees 80 theme.
	 */
	ANNEES_80("annees_80"),

	/**
	 * Annees 90 theme.
	 */
	ANNEES_90("annees_90"),

	/**
	 * Annees 2000 theme.
	 */
	ANNEES_2000("annees_2000"),

	/**
	 * Cinemas theme.
	 */
	CINEMAS("cinemas"),

	/**
	 * Series theme.
	 */
	SERIES("series"),

	/**
	 * Disney theme.
	 */
	DISNEY("disney"),

	/**
	 * Classiques theme.
	 */
	CLASSIQUES("classiques");
	
	
	
	private String folderName;

	
	
	private Theme(String folderName) {
		this.folderName = folderName;
	}


	/**
	 * Gets folder name.
	 *
	 * @return the folder name
	 */
	public String getFolderName() {
		return folderName;
	}


	/**
	 * Gets sorted theme.
	 *
	 * @return the sorted theme
	 */
	public static List<Theme> getSortedTheme() {
		return Arrays.asList(ANNEES_60, ANNEES_70, ANNEES_80, ANNEES_90, ANNEES_2000, CINEMAS, SERIES, DISNEY, CLASSIQUES);
	}
	
}
