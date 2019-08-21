package com.myssteriion.blindtest.db.exception;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

public class DaoExceptionTest extends AbstractTest {

	@Test
	public void constructor() {
		
		String message = "failed";
		
		try {
			new DaoException(null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new DaoException("");
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		DaoException eme = new DaoException(message);
		Assert.assertEquals( message, eme.getMessage() );
		
		
		
		
		NullPointerException npe = new NullPointerException("npe");
		
		try {
			new DaoException(null, npe);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new DaoException("", npe);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new DaoException(message, null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'cause' est obligatoire."), e);
		}
		
		eme = new DaoException(message, npe);
		Assert.assertEquals( message, eme.getMessage() );
		Assert.assertEquals( npe, eme.getCause() );
	}

}
