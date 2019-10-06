package com.myssteriion.blindtest.model.base;

import com.myssteriion.blindtest.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Use when REST service return a list of IModel.
 *
 * @param <T> a IModel
 */
public class ItemsPage<T extends IModel> implements IModel {

	/**
	 * The lists.
	 */
	private List<T> items;

	/**
	 * Total items.
	 */
	private int totalItems;

	/**
	 * The number of current page.
	 */
	private int currentPage;

	/**
	 * The total number of pages.
	 */
	private int totalPage;



	/**
	 * Instantiates a new Items page.
	 *
	 * @param items the items
	 */
	public ItemsPage(List<T> items) {
		this.items = (items == null) ? new ArrayList<>() : items;
	}



	/**
	 * Gets items list.
	 *
	 * @return the items list
	 */
	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
