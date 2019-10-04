package com.myssteriion.blindtest.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

import java.util.Objects;

/**
 * The ProfileDTO.
 */
public class ProfileDTO extends AbstractDTO {

	/**
	 * The name.
	 */
	private String name;

	/**
	 * The avatar.
	 */
	private Avatar avatar;



	/**
	 * Instantiates a new ProfileDto.
	 *
	 * @param name the name
	 */
	public ProfileDTO(String name) {
		this( name, new Avatar(Constant.DEFAULT_AVATAR) );
	}

	/**
	 * Instantiates a new ProfileDto.
	 *
	 * @param name   the name
	 * @param avatar the avatar
	 */
	@JsonCreator
	public ProfileDTO(String name, Avatar avatar) {

		Tool.verifyValue("name", name);

		this.name = name.trim();
		this.avatar = Tool.isNullOrEmpty(avatar) ? new Avatar(Constant.DEFAULT_AVATAR) : avatar;
	}


	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		Tool.verifyValue("name", name);
		this.name = name.trim();
	}

	/**
	 * Gets avatar.
	 *
	 * @return the avatar
	 */
	public Avatar getAvatar() {
		return avatar;
	}

	/**
	 * Sets avatar.
	 *
	 * @param avatar the avatar
	 */
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
