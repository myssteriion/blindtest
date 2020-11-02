package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FluxTest extends AbstractTest {
    
    private static final File RESOURCE_DIR = new File( FluxTest.class.getClassLoader().getResource(".").getFile());
    
    private static final Path FILE_EXISTS = Paths.get(RESOURCE_DIR.getAbsolutePath(), "exists-file.txt");
    
    private static final Path FILE_EXISTS_2 = Paths.get(RESOURCE_DIR.getAbsolutePath(), "exists-file-2.txt");
    
    private static final Path FILE_EXISTS_3 = Paths.get(RESOURCE_DIR.getAbsolutePath(), "exists-file-2.mp3");
    
    
    
    @BeforeEach
    void before() throws IOException {
        Files.createFile(FILE_EXISTS);
        Files.createFile(FILE_EXISTS_2);
        Files.createFile(FILE_EXISTS_3);
    }
    
    @AfterEach
    void after() throws IOException {
        Files.deleteIfExists(FILE_EXISTS);
        Files.deleteIfExists(FILE_EXISTS_2);
        Files.deleteIfExists(FILE_EXISTS_3);
    }
    
    
    
    @Test
    void constructor( ) throws IOException {
        
        TestUtils.assertThrowMandatoryField( "file", () -> new Flux(null) );
        
        Flux flux = new Flux( new File("existe pas") );
        Assertions.assertEquals( "existe pas", flux.getName() );
        Assertions.assertFalse( flux.isFileExists() );
        Assertions.assertNull( flux.getContentFlux() );
        Assertions.assertNull( flux.getContentType() );
        
        flux = new Flux( FILE_EXISTS.toFile() );
        Assertions.assertEquals( FILE_EXISTS.toFile().getName(), flux.getName() );
        Assertions.assertTrue( flux.isFileExists() );
        Assertions.assertEquals( Files.readAllBytes(FILE_EXISTS).length, flux.getContentFlux().length );
        Assertions.assertEquals( URLConnection.guessContentTypeFromName(FILE_EXISTS.toFile().getName()), flux.getContentType() );
        
        flux = new Flux( FILE_EXISTS_3.toFile() );
        Assertions.assertEquals( FILE_EXISTS_3.toFile().getName(), flux.getName() );
        Assertions.assertTrue( flux.isFileExists() );
        Assertions.assertEquals( Files.readAllBytes(FILE_EXISTS).length, flux.getContentFlux().length );
        Assertions.assertEquals( CommonConstant.WAV_CONTENT_TYPE, flux.getContentType() );
    }
    
    @Test
    void toStringAndEquals() throws IOException {
        
        Flux fluxOne = new Flux(FILE_EXISTS.toFile());
        Flux fluxOneIso = new Flux(FILE_EXISTS.toFile());
        Flux fluxTwo = new Flux(FILE_EXISTS_2.toFile());
        
        Assertions.assertEquals("name=exists-file.txt, fileExists=true, contentType=text/plain", fluxOne.toString());
        
        Assertions.assertNotEquals(fluxOne, null);
        Assertions.assertNotEquals(fluxOne, "bad class");
        Assertions.assertEquals(fluxOne, fluxOne);
        Assertions.assertEquals(fluxOne, fluxOneIso);
        Assertions.assertNotEquals(fluxOne, fluxTwo);
        
        Assertions.assertEquals(fluxOne.hashCode(), fluxOne.hashCode());
        Assertions.assertEquals(fluxOne.hashCode(), fluxOneIso.hashCode());
        Assertions.assertNotEquals(fluxOne.hashCode(), fluxTwo.hashCode());
    }
    
}