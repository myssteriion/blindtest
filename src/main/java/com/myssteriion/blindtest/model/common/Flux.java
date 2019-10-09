package com.myssteriion.blindtest.model.common;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Objects;

/**
 * The type Flux.
 */
public class Flux {

    /**
     * The name.
     */
    private String name;

    /**
     * File exists.
     */
    private boolean fileExists;

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
    public Flux(File file) throws IOException {

        if (file == null)
            throw new IllegalArgumentException("Le champ 'file' est obligatoire.");

        this.name = file.getName();
        fileExists = file.exists() && file.isFile();

        if (fileExists) {
            this.contentFlux = Files.readAllBytes(file.toPath());
            this.contentType = URLConnection.guessContentTypeFromName(file.getName());
        }
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
     * Gets fileExists.
     *
     * @return The fileExists.
     */
    public boolean isFileExists() {
        return fileExists;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flux flux = (Flux) o;
        return Objects.equals(name, flux.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "name=" + name +
                ", fileExists=" + fileExists +
                ", contentType=" + contentType;
    }

}