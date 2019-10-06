package com.myssteriion.blindtest.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.myssteriion.blindtest.model.IModel;
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
import java.util.Comparator;
import java.util.Objects;

/**
 * The Avatar.
 */
public class Avatar implements IModel {

    /**
     * The constant COMPARATOR.
     */
    public static final Comparator<Avatar> COMPARATOR = new Comparator<Avatar>() {

        @Override
        public int compare(Avatar o1, Avatar o2) {

            if (o1 != null && o2 == null)
                return 1;
            else if (o1 == null && o2 != null)
                return -1;
            else if (o1 == null && o2 == null)
                return 0;
            else
                return String.CASE_INSENSITIVE_ORDER.compare(o1.name, o2.name);
        }
    };

    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Avatar.class);

    /**
     * The name.
     */
    private String name;

    /**
     * If the file exists.
     */
    private boolean fileExists;

    /**
     * The content type.
     */
    private String contentType;

    /**
     * The byte array flux.
     */
    private byte[] flux;



    /**
     * Instantiates a new Avatar.
     *
     * @param name the name
     */
    @JsonCreator
    public Avatar(String name) {
        this.name = Tool.isNullOrEmpty(name) ? Constant.DEFAULT_AVATAR : name;
        createFlux();
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
     * Is file exists boolean.
     *
     * @return the boolean
     */
    public boolean isFileExists() {
        return fileExists;
    }

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Get flux byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getFlux() {
        return flux;
    }



    /**
     * Create a byte array flux if the name match with a real file.
     */
    private void createFlux() {

        try {

            Path path = Paths.get(Constant.BASE_DIR, Constant.AVATAR_FOLDER, name);
            fileExists = path.toFile().exists();

            if (fileExists) {
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
                ", fileExists=" + fileExists +
                ", contentType=" + contentType;

    }

}
