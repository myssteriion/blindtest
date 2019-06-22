package com.myssteriion.blindtest.db.common;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.SqlException;

public class SqlExceptionTest extends AbstractTest {

	@Test
	public void constructor() {
		
		String message = "failed";
		
		try {
			new SqlException(null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new SqlException("");
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		SqlException eme = new SqlException(message);
		Assert.assertEquals( message, eme.getMessage() );
		
		
		
		
		NullPointerException npe = new NullPointerException("npe");
		
		try {
			new SqlException(null, npe);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new SqlException("", npe);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new SqlException(message, null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'cause' est obligatoire."), e);
		}
		
		eme = new SqlException(message, npe);
		Assert.assertEquals( message, eme.getMessage() );
		Assert.assertEquals( npe, eme.getCause() );
	}

}
