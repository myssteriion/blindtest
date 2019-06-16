package com.myssteriion.blindtest.model;

public abstract class AbstractDTO {

	private String id;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	@Override
	public String toString() {
		return "id=" + id;
	}
	
}
