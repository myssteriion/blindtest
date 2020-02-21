package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.test.TestUtils;
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

    private static final File RESOURCE_DIR = new File( FluxTest.class.getClassLoader().getResource(".").getFile());

    private static final Path FILE_EXISTS = Paths.get(RESOURCE_DIR.getAbsolutePath(), "exists-file.txt");

    private static final Path FILE_EXISTS_2 = Paths.get(RESOURCE_DIR.getAbsolutePath(), "exists-file-2.txt");

    private static final Path FILE_EXISTS_3 = Paths.get(RESOURCE_DIR.getAbsolutePath(), "exists-file-2.mp3");


    @Before
    public void before() throws IOException {
        Files.createFile(FILE_EXISTS);
        Files.createFile(FILE_EXISTS_2);
        Files.createFile(FILE_EXISTS_3);
    }

    @After
    public void after() throws IOException {
        Files.deleteIfExists(FILE_EXISTS);
        Files.deleteIfExists(FILE_EXISTS_2);
        Files.deleteIfExists(FILE_EXISTS_3);
    }



    @Test
    public void constructor( ) throws IOException {

        try {
            new Flux(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'file' est obligatoire."), e);
        }

        Flux flux = new Flux( new File("existe pas") );
        Assert.assertEquals( "existe pas", flux.getName() );
        Assert.assertFalse( flux.isFileExists() );
        Assert.assertNull( flux.getContentFlux() );
        Assert.assertNull( flux.getContentType() );

        flux = new Flux( FILE_EXISTS.toFile() );
        Assert.assertEquals( FILE_EXISTS.toFile().getName(), flux.getName() );
        Assert.assertTrue( flux.isFileExists() );
        Assert.assertEquals( Files.readAllBytes(FILE_EXISTS).length, flux.getContentFlux().length );
        Assert.assertEquals( URLConnection.guessContentTypeFromName(FILE_EXISTS.toFile().getName()), flux.getContentType() );

        flux = new Flux( FILE_EXISTS_3.toFile() );
        Assert.assertEquals( FILE_EXISTS_3.toFile().getName(), flux.getName() );
        Assert.assertTrue( flux.isFileExists() );
        Assert.assertEquals( Files.readAllBytes(FILE_EXISTS).length, flux.getContentFlux().length );
        Assert.assertEquals( CommonConstant.WAV_CONTENT_TYPE, flux.getContentType() );
    }

    @Test
    public void toStringAndEquals() throws IOException {

        Flux fluxOne = new Flux(FILE_EXISTS.toFile());
        Flux fluxOneIso = new Flux(FILE_EXISTS.toFile());
        Flux fluxTwo = new Flux(FILE_EXISTS_2.toFile());

        Assert.assertEquals("name=exists-file.txt, fileExists=true, contentType=text/plain", fluxOne.toString());

        Assert.assertNotEquals(fluxOne, null);
        Assert.assertNotEquals(fluxOne, "bad class");
        Assert.assertEquals(fluxOne, fluxOne);
        Assert.assertEquals(fluxOne, fluxOneIso);
        Assert.assertNotEquals(fluxOne, fluxTwo);

        Assert.assertEquals(fluxOne.hashCode(), fluxOne.hashCode());
        Assert.assertEquals(fluxOne.hashCode(), fluxOneIso.hashCode());
        Assert.assertNotEquals(fluxOne.hashCode(), fluxTwo.hashCode());
    }

}