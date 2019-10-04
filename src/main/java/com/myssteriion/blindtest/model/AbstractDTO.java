package com.myssteriion.blindtest.model;

/**
 * Abstract class for all DTO. A DTO have a table in DB.
 */
public abstract class AbstractDTO implements IModel {

	/**
	 * The DB id.
	 */
	private Integer id;


	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "id=" + id;
	}
	
}
