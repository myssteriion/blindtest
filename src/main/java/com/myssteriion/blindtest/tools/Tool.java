package com.myssteriion.blindtest.tools;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Tool class.
 */
public class Tool {

	/**
	 * The constant RANDOM.
	 */
	public static final Random RANDOM = new Random();

	/**
	 * The constant MAPPER.
	 */
	public static final ObjectMapper MAPPER = new ObjectMapper();


	/**
	 * Test if it's null or empty.
	 *
	 * @param o the object
	 * @return TRUE if it's null or empty, FALSE otherwise
	 */
	public static boolean isNullOrEmpty(Object o) {
		
		if (o == null) {
			return true;
		}
		else if (o instanceof String) {
			return ((String) o).trim().isEmpty();
		}
		else if (o instanceof List) {
			return ((List<?>) o).isEmpty();
		}
		else if (o instanceof Set) {
			return ((Set<?>) o).isEmpty();
		}
		else if (o instanceof Map) {
			return ((Map<?, ?>) o).isEmpty();
		}
		else if (o instanceof File) {
			return !((File) o).exists();
		}
		else {
			return false;
		}
	}

	/**
	 * Throw if it's null or empty.
	 *
	 * @param key   the key
	 * @param value the value
	 * @see #isNullOrEmpty(Object) #isNullOrEmpty(Object)
	 */
	public static void verifyValue(String key, Object value) {
		
		if ( !isNullOrEmpty(key) && isNullOrEmpty(value) )
			throw new IllegalArgumentException("Le champ '" + key + "' est obligatoire.");
	}

	/**
	 * Transform cause throwable recurse to list.
	 *
	 * @param t the throwable
	 * @return the causes list
	 */
	public static List<String> transformToList(Throwable t) {
		
		List<String> list = new ArrayList<>();
		
		if (t == null)
			return list;
		
		list.add( t.getMessage() );
		list.addAll( transformToList(t.getCause()) );
		return list;
	}

	/**
	 * Gets children files from a file.
	 *
	 * @param parent the parent file
	 * @return the children files
	 */
	public static List<File> getChildren(File parent) {

		if ( isNullOrEmpty(parent) || !parent.isDirectory() || parent.listFiles() == null)
			return new ArrayList<>();

		return Arrays.asList( parent.listFiles() );
	}


	private Tool() {}

}
