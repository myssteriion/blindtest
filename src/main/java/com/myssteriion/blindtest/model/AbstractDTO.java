package com.myssteriion.blindtest.model;

public abstract class AbstractDTO implements IModel {

	private Integer id;

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
	@Override
	public String toString() {
		return "id=" + id;
	}
	
}
