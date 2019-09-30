package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ProfileDTO extends AbstractDTO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileDTO.class);

	private String name;

	private String avatar;

	private boolean isFileExists;

	private byte[] avatarFlux;



	public ProfileDTO(String name) {
		this(name, Constant.DEFAULT_AVATAR);
	}

	public ProfileDTO(String name, String avatar) {

		Tool.verifyValue("name", name);
		
		this.name = name.trim();
		this.avatar = ( Tool.isNullOrEmpty(avatar) ) ? Constant.DEFAULT_AVATAR : avatar;
		createAvatarFlux();
	}



	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		Tool.verifyValue("name", name);
		this.name = name.trim();
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = ( Tool.isNullOrEmpty(avatar) ) ? Constant.DEFAULT_AVATAR : avatar;
		createAvatarFlux();
	}

	public boolean isFileExists() {
		return isFileExists;
	}

	public byte[] getAvatarFlux() {
		return avatarFlux;
	}


	private void createAvatarFlux() {

		try {

			Path path = Paths.get(Constant.BASE_DIR, Constant.AVATAR_FOLDER, this.avatar);
			isFileExists = path.toFile().exists();

			if (isFileExists)
				avatarFlux = Files.readAllBytes(path);
		}
		catch (IOException e) {
			String message = "can't read avatar file";
			LOGGER.error(message, e);
			throw new CustomRuntimeException(message, e);
		}
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
				", avatar=" + avatar +
				", isFileExists=" + isFileExists;
	}
	
}
