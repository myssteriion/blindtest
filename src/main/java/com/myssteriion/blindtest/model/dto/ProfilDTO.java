package com.myssteriion.blindtest.model.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Tool;

public class ProfilDTO extends AbstractDTO {

	private String name;
	
	private String avatar;
	
	
	
	@JsonCreator
	public ProfilDTO(String name, String avatar) {

		Tool.verifyValue("name", name);
		Tool.verifyValue("avatar", avatar);
		
		this.name = name.trim();
		this.avatar = avatar;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		
		Tool.verifyValue("name", name);
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		
		Tool.verifyValue("avatar", avatar);
		this.avatar = avatar;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if(obj == null || obj.getClass()!= this.getClass()) 
            return false; 
		
		ProfilDTO other = (ProfilDTO) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public String toString() {
		return super.toString() + 
				", name=" + name +
				", avatar=" + avatar;
	}
	
}
