package com.myssteriion.blindtest.model.base;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.base.ErrorMessage;

public class ErrorMessageTest extends AbstractTest {

	@Test
	public void contructor() {

		HttpStatus status = HttpStatus.OK;
		String message = "message";
		
		try {
			new ErrorMessage(null, message);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'status' est obligatoire."), e);
		}
		
		try {
			new ErrorMessage(status, null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}
		
		try {
			new ErrorMessage(status, "");
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'message' est obligatoire."), e);
		}

		ErrorMessage re = new ErrorMessage(status, message);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertTrue( re.getCauses().isEmpty() );
		
		re = new ErrorMessage(status, message, null);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertEquals( new ArrayList<>(), re.getCauses() );
		
		NullPointerException npe = new NullPointerException("npe");
		re = new ErrorMessage(status, message, npe);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertEquals( 1, re.getCauses().size() );
		Assert.assertEquals( "npe", re.getCauses().get(0) );
	}

	@Test
	public void getterSetter() {
		
		HttpStatus status = HttpStatus.OK;
		String message = "message";
		
		ErrorMessage re = new ErrorMessage(status, message);
		Assert.assertEquals( status, re.getStatus() );
		Assert.assertEquals( message, re.getMessage() );
		Assert.assertTrue( re.getCauses().isEmpty() );
		
	}
	
}
