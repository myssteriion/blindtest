package com.myssteriion.blindtest.model.common;

import java.util.Arrays;
import java.util.List;

public enum Theme {

	ANNEES_60("annees_60"), 
	ANNEES_70("annees_70"), 
	ANNEES_80("annees_80"), 
	ANNEES_90("annees_90"), 
	ANNEES_2000("annees_2000"), 
	CINEMAS("cinemas"), 
	SERIES("series"), 
	DISNEY("disney"), 
	FRANCAIS("francais");
	
	
	
	private String folderName;

	
	
	private Theme(String folderName) {
		this.folderName = folderName;
	}

	
	
	public String getFolderName() {
		return folderName;
	}
	
	
	
	public static List<Theme> getSortedTheme() {
		return Arrays.asList(ANNEES_60, ANNEES_70, ANNEES_80, ANNEES_90, ANNEES_2000, CINEMAS, SERIES, DISNEY, FRANCAIS);
	}
	
}
