package com.myssteriion.blindtest.db.common;

import com.myssteriion.blindtest.AbstractTest;
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

        NotFoundException eme = new NotFoundException(message);
        Assert.assertEquals( message, eme.getMessage() );
    }
    
}