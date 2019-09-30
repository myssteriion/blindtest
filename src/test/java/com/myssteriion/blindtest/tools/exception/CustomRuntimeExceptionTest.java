package com.myssteriion.blindtest.tools.exception;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class CustomRuntimeExceptionTest extends AbstractTest {

    @Test
    public void constructor() {

        String message = "failed";
        NullPointerException npe = new NullPointerException("null");

        try {
            new CustomRuntimeException(null, npe);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        try {
            new CustomRuntimeException("", npe);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
        }

        try {
            new CustomRuntimeException(message, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'throwable' est obligatoire."), e);
        }

        CustomRuntimeException eme = new CustomRuntimeException(message, npe);
        Assert.assertEquals( message, eme.getMessage() );
    }
    
}