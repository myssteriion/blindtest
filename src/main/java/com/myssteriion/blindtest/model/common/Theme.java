package com.myssteriion.blindtest.model.common;

import java.util.Arrays;
import java.util.List;

/**
 * The enum Theme.
 */
public enum Theme {

	ANNEES_60("annees_60"),
	ANNEES_70("annees_70"),
	ANNEES_80("annees_80"),
	ANNEES_90("annees_90"),
	ANNEES_2000("annees_2000"),
	CINEMAS("cinemas"),
	SERIES("series"),
	DISNEY("disney"),
	CLASSIQUES("classiques");
	
	
	
	private String folderName;

	Theme(String folderName) {
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
