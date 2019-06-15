package com.myssteriion.blindtest.model;

import java.util.Objects;

public abstract class AbstractDTO {

	private String id;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		AbstractDTO other = (AbstractDTO) obj;
		return Objects.equals(this.id, other.id); 
	}

	@Override
	public String toString() {
		return "id=" + id;
	}
	
}
