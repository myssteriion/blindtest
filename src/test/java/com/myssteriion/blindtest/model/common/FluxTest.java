package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.ToolTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FluxTest extends AbstractTest {

    private static final File RESOURCE_DIR = new File( ToolTest.class.getClassLoader().getResource(".").getFile());

    private static final Path FILE_EXISTS = Paths.get(RESOURCE_DIR.getAbsolutePath(), "exists-file.txt");



    @Before
    public void before() throws IOException {
        Files.createFile(FILE_EXISTS);
    }

    @After
    public void after() throws IOException {
        Files.deleteIfExists(FILE_EXISTS);
    }




    @Test
    public void constructor( ) throws NotFoundException, IOException {

        try {
            new Flux(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'file' est obligatoire."), e);
        }

        try {
            new Flux( new File("existe pas") );
            Assert.fail("Doit lever une NotFoundException car le file n'existe pas.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'file' est obligatoire."), e);
        }

        try {
            new Flux( RESOURCE_DIR );
            Assert.fail("Doit lever une NotFoundException car le file n'est pas un file.");
        }
        catch (NotFoundException e) {
            verifyException(new NotFoundException("Music file must be a file."), e);
        }

        Flux flux = new Flux( FILE_EXISTS.toFile() );
        Assert.assertEquals( FILE_EXISTS.toFile().getName(), flux.getName() );
        Assert.assertEquals( Files.readAllBytes(FILE_EXISTS).length, flux.getContentFlux().length );
        Assert.assertEquals( URLConnection.guessContentTypeFromName(FILE_EXISTS.toFile().getName()), flux.getContentType() );
    }

}