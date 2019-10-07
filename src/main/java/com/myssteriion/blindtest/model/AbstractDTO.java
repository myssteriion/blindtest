package com.myssteriion.blindtest.model;

/**
 * Abstract class for all DTO. A DTO have a table in DB.
 */
public abstract class AbstractDTO implements IModel {

	/**
	 * Sets id.
	 *
	 * @param id the id
	 * @return this
	 */
	public abstract AbstractDTO setId(Integer id);

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public abstract Integer getId();
	
}
