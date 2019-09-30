package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

import java.util.Objects;

public class ProfileDTO extends AbstractDTO {

	private String name;

	private Avatar avatar;



	public ProfileDTO(String name) {
		this( name, new Avatar(Constant.DEFAULT_AVATAR) );
	}

	public ProfileDTO(String name, Avatar avatar) {

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
		this.name = name.trim();
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
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
		
		ProfileDTO other = (ProfileDTO) obj;
		return Objects.equals(this.name, other.name);
	}

	@Override
	public String toString() {
		return super.toString() + 
				", name=" + name +
				", avatar={" + avatar + "}";
	}
	
}
