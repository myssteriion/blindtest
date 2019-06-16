package com.myssteriion.blindtest.rest;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.myssteriion.blindtest.AbstractTest;

public class RestErrorTest extends AbstractTest {

	@Test
	public void contructor() {

		HttpStatus status = HttpStatus.OK;
		String message = "message";
		
		try {
			new RestError(null, message);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'status' est obligatoire."), e);
		}
		
		try {
			new RestError(status, null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new RestError(status, "");
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}

		RestError re = new RestError(status, message);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertTrue( re.getCauses().isEmpty() );
		
		re = new RestError(status, message, null);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertEquals( new ArrayList<>(), re.getCauses() );
		
		NullPointerException npe = new NullPointerException("npe");
		re = new RestError(status, message, npe);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertEquals( 1, re.getCauses().size() );
		Assert.assertEquals( "npe", re.getCauses().get(0) );
	}

	@Test
	public void getterSetter() {
		
		HttpStatus status = HttpStatus.OK;
		String message = "message";
		
		RestError re = new RestError(status, message);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertTrue( re.getCauses().isEmpty() );
		
	}
	
}
