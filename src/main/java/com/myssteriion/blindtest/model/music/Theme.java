package com.myssteriion.blindtest.model.music;

public enum Theme {

	ANNEES_80("annees_80"), 
	ANNEES_90("annees_90");
	
	
	
	private String folderName;

	
	
	private Theme(String folderName) {
		this.folderName = folderName;
	}

	
	
	public String getFolderName() {
		return folderName;
	}
	
}
