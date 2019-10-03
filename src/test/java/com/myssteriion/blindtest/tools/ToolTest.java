package com.myssteriion.blindtest.tools;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.exception.DaoException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ToolTest extends AbstractTest {

	private static final File RESOURCE_DIR = new File( ToolTest.class.getClassLoader().getResource(".").getFile());

	private static final Path FILE_EXISTS = Paths.get(RESOURCE_DIR.getAbsolutePath(), "exists-file.txt");

	private static final Path DIRECTORY_EXISTS = Paths.get(RESOURCE_DIR.getAbsolutePath() , "exists-directory");

	private static final Path FILE_IN_DIRECTORY_EXISTS = Paths.get(RESOURCE_DIR.getAbsolutePath() , "exists-directory", "exists-file.txt");


	@Before
	public void before() throws IOException {
		Files.createFile(FILE_EXISTS);
		Files.createDirectory(DIRECTORY_EXISTS);
		Files.createFile(FILE_IN_DIRECTORY_EXISTS);
	}


	@After
	public void after() throws IOException {
		Files.deleteIfExists(FILE_EXISTS);
		Files.deleteIfExists(FILE_IN_DIRECTORY_EXISTS);
		Files.deleteIfExists(DIRECTORY_EXISTS);
	}



	@Test
	public void isNullOrEmpty() {
		
		Assert.assertTrue( Tool.isNullOrEmpty(null) );
		Assert.assertTrue( Tool.isNullOrEmpty("") );
		Assert.assertTrue( Tool.isNullOrEmpty( new ArrayList<>() ) );
		Assert.assertTrue( Tool.isNullOrEmpty( new HashMap<>() ) );

		Assert.assertTrue( Tool.isNullOrEmpty(" ") );
		Assert.assertTrue( Tool.isNullOrEmpty("  ") );

		Assert.assertFalse( Tool.isNullOrEmpty("test") );
		Assert.assertFalse( Tool.isNullOrEmpty( Arrays.asList("test") ) );

		Assert.assertTrue( Tool.isNullOrEmpty( new HashSet<>() ) );
		Assert.assertFalse( Tool.isNullOrEmpty( new HashSet<>(Arrays.asList("test")) ) );

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
		
		
		DaoException sql = new DaoException("sql", iae);
		expected = new ArrayList<>();
		expected.add( sql.getMessage() );
		expected.add( iae.getMessage() );
		expected.add( npe.getMessage() );
		actual = Tool.transformToList(sql);
		for (int i = 0; i < expected.size(); i++) {
			Assert.assertEquals( expected.get(i), actual.get(i) );
		}
	}

	@Test
	public void getChildren() {

		List<File> empty = new ArrayList<>();

		Assert.assertEquals( empty, Tool.getChildren(null) );

		File f = Paths.get(Constant.BASE_DIR , "existe-pas").toFile();
		Assert.assertEquals( empty, Tool.getChildren(f) );

		f = FILE_EXISTS.toFile();
		Assert.assertEquals( empty, Tool.getChildren(f) );

		f = DIRECTORY_EXISTS.toFile();
		Assert.assertEquals( Arrays.asList(FILE_IN_DIRECTORY_EXISTS.toFile()), Tool.getChildren(f) );
	}

}
