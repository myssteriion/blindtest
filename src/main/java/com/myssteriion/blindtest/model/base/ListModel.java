package com.myssteriion.blindtest.model.base;

import java.util.ArrayList;
import java.util.List;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.IModel;

public class ListModel<T extends AbstractDTO> implements IModel {

	private List<T> items;
	
	private int size;

	
	
	public ListModel(List<T> items) {
		this.items = (items == null) ? new ArrayList<>() : items;
		this.size = this.items.size();
	}



	public List<T> getItems() {
		return items;
	}

	public int getSize() {
		return size;
	}
	
}
