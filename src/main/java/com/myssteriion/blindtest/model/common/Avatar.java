package com.myssteriion.blindtest.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Avatar {

    private static final Logger LOGGER = LoggerFactory.getLogger(Avatar.class);

    private String name;

    private boolean isFileExists;

    private String contentType;

    private byte[] flux;



    @JsonCreator
    public Avatar(String name) {
        this.name = Tool.isNullOrEmpty(name) ? Constant.DEFAULT_AVATAR : name;
        createFlux();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        Tool.verifyValue("name", name);
        this.name = name.trim();
    }

    public boolean isFileExists() {
        return isFileExists;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getFlux() {
        return flux;
    }


    private void createFlux() {

        try {

            Path path = Paths.get(Constant.BASE_DIR, Constant.AVATAR_FOLDER, name);
            isFileExists = path.toFile().exists();

            if (isFileExists) {
                contentType = URLConnection.guessContentTypeFromName(name);
                flux = Files.readAllBytes(path);
            }
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

        Avatar other = (Avatar) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "name=" + name +
                ", isFileExists=" + isFileExists +
                ", contentType=" + contentType;

    }

}
