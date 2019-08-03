package com.myssteriion.blindtest.db.common;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class ConflictExceptionTest extends AbstractTest {


    @Test
    public void constructor() {

        String message = "failed";

        try {
            new ConflictException(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        try {
            new ConflictException("");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        ConflictException eme = new ConflictException(message);
        Assert.assertEquals( message, eme.getMessage() );
    }
    
}