package com.myssteriion.blindtest.rest;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.base.ErrorModel;

public class RestErrorTest extends AbstractTest {

	@Test
	public void contructor() {

		HttpStatus status = HttpStatus.OK;
		String message = "message";
		
		try {
			new ErrorModel(null, message);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'status' est obligatoire."), e);
		}
		
		try {
			new ErrorModel(status, null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new ErrorModel(status, "");
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}

		ErrorModel re = new ErrorModel(status, message);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertTrue( re.getCauses().isEmpty() );
		
		re = new ErrorModel(status, message, null);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertEquals( new ArrayList<>(), re.getCauses() );
		
		NullPointerException npe = new NullPointerException("npe");
		re = new ErrorModel(status, message, npe);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertEquals( 1, re.getCauses().size() );
		Assert.assertEquals( "npe", re.getCauses().get(0) );
	}

	@Test
	public void getterSetter() {
		
		HttpStatus status = HttpStatus.OK;
		String message = "message";
		
		ErrorModel re = new ErrorModel(status, message);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertTrue( re.getCauses().isEmpty() );
		
	}
	
}
