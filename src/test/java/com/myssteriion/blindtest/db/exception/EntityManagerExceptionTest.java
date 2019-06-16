package com.myssteriion.blindtest.db.exception;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;

public class EntityManagerExceptionTest extends AbstractTest {

	@Test
	public void constructor() {
		
		String message = "failed";
		
		try {
			new EntityManagerException(null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new EntityManagerException("");
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		EntityManagerException eme = new EntityManagerException(message);
		Assert.assertEquals( message, eme.getMessage() );
		
		
		
		
		NullPointerException npe = new NullPointerException("npe");
		
		try {
			new EntityManagerException(null, npe);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new EntityManagerException("", npe);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new EntityManagerException(message, null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'cause' est obligatoire."), e);
		}
		
		eme = new EntityManagerException(message, npe);
		Assert.assertEquals( message, eme.getMessage() );
		Assert.assertEquals( npe, eme.getCause() );
	}

}
