package com.myssteriion.blindtest.rest.exception;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;

public class NotFoundExceptionTest extends AbstractTest {

    @Test
    public void constructor() {

        String message = "failed";

        try {
            new NotFoundException(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        try {
            new NotFoundException("");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        NotFoundException nfe = new NotFoundException(message);
        Assert.assertEquals( message, nfe.getMessage() );
    }
    
}