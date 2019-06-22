package com.myssteriion.blindtest.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.SqlException;

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
		
		Assert.assertFalse( Tool.isNullOrEmpty( new File(".") ) );
		Assert.assertTrue( Tool.isNullOrEmpty( new File("./exitePas") ) );
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

	@Test
	public void transformToList() {
		
		Assert.assertEquals( new ArrayList<>(), Tool.transformToList(null) );
		
		NullPointerException npe = new NullPointerException("npe");
		List<String> expected = new ArrayList<>();
		expected.add( npe.getMessage() );
		List<String> actual = Tool.transformToList(npe);
		for (int i = 0; i < expected.size(); i++) {
			Assert.assertEquals( expected.get(i), actual.get(i) );
		}
		

		IllegalArgumentException iae = new IllegalArgumentException("iea", npe);
		expected = new ArrayList<>();
		expected.add( iae.getMessage() );
		expected.add( npe.getMessage() );
		actual = Tool.transformToList(iae);
		for (int i = 0; i < expected.size(); i++) {
			Assert.assertEquals( expected.get(i), actual.get(i) );
		}
		
		
		SqlException sql = new SqlException("sql", iae);
		expected = new ArrayList<>();
		expected.add( sql.getMessage() );
		expected.add( iae.getMessage() );
		expected.add( npe.getMessage() );
		actual = Tool.transformToList(sql);
		for (int i = 0; i < expected.size(); i++) {
			Assert.assertEquals( expected.get(i), actual.get(i) );
		}
	}
	
}
