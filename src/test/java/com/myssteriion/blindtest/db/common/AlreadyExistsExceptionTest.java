package com.myssteriion.blindtest.db.common;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class AlreadyExistsExceptionTest extends AbstractTest {


    @Test
    public void constructor() {

        String message = "failed";

        try {
            new AlreadyExistsException(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        try {
            new AlreadyExistsException("");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        AlreadyExistsException eme = new AlreadyExistsException(message);
        Assert.assertEquals( message, eme.getMessage() );
    }
    
}