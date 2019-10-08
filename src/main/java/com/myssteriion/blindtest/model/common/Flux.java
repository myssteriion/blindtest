package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

/**
 * The type Flux.
 */
public class Flux {

    /**
     * The name.
     */
    private String name;

    /**
     * The contentFlux.
     */
    private byte[] contentFlux;

    /**
     * The content type.
     */
    private String contentType;



    /**
     * Instantiates a new Flux.
     *
     * @param file the file
     */
    public Flux(File file) throws NotFoundException, IOException {

        Tool.verifyValue("file", file);

        if ( !file.isFile() )
            throw new NotFoundException("Music file must be a file.");

        this.name = file.getName();
        this.contentFlux = Files.readAllBytes(file.toPath());
        this.contentType = URLConnection.guessContentTypeFromName(file.getName());
    }


    /**
     * Gets name.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets contentFlux.
     *
     * @return The contentFlux.
     */
    public byte[] getContentFlux() {
        return contentFlux;
    }

    /**
     * Gets contentType.
     *
     * @return The contentType.
     */
    public String getContentType() {
        return contentType;
    }

}
