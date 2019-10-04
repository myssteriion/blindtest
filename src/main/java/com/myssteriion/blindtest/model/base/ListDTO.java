package com.myssteriion.blindtest.model.base;

import java.util.ArrayList;
import java.util.List;

import com.myssteriion.blindtest.model.IModel;

/**
 * Use when REST service return a list of IModel.
 *
 * @param <T> a IModel
 */
public class ListDTO<T extends IModel> implements IModel {

	/**
	 * The list.
	 */
	private List<T> items;

	/**
	 * The size's list.
	 */
	private int size;


	/**
	 * Instantiates a new List dto.
	 *
	 * @param items the items
	 */
	public ListDTO(List<T> items) {
		this.items = (items == null) ? new ArrayList<>() : items;
		this.size = this.items.size();
	}


	/**
	 * Gets items.
	 *
	 * @return the items
	 */
	public List<T> getItems() {
		return items;
	}

	/**
	 * Gets size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	
}
