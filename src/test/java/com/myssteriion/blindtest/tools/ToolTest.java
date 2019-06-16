package com.myssteriion.blindtest.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;

public class ToolTest extends AbstractTest {

	@Test
	public void isNullOrEmpty() {
		
		Assert.assertTrue( Tool.isNullOrEmpty(null) );
		Assert.assertTrue( Tool.isNullOrEmpty("") );
		Assert.assertTrue( Tool.isNullOrEmpty( new ArrayList<>() ) );
		Assert.assertTrue( Tool.isNullOrEmpty( new HashMap<>() ) );
		
		Assert.assertFalse( Tool.isNullOrEmpty("test") );
		Assert.assertFalse( Tool.isNullOrEmpty( Arrays.asList("test") ) );
		
		Map<String, String> map = new HashMap<>();
		map.put("key", "value");
		Assert.assertFalse( Tool.isNullOrEmpty(map) );
		
		Assert.assertFalse( Tool.isNullOrEmpty(new Integer(1)) );
	}

	@Test
	public void verifyValue() {
		
		Tool.verifyValue(null, "test");
		Tool.verifyValue("", null);
		
		try {
			Tool.verifyValue("key", null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'key' est obligatoire."), e);
		}
		
		try {
			Tool.verifyValue("key", "");
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'key' est obligatoire."), e);
		}
		
		try {
			Tool.verifyValue("key", new ArrayList<>());
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'key' est obligatoire."), e);
		}

		try {
			Tool.verifyValue("key", new HashMap<>());
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'key' est obligatoire."), e);
		}
		
		Tool.verifyValue("key", "pouet");
	}

}
