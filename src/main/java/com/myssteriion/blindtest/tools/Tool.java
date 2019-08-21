package com.myssteriion.blindtest.tools;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Tool {

	public static final Random RANDOM = new Random();

	public static final ObjectMapper MAPPER = new ObjectMapper();
	


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

	public static void verifyValue(String key, Object value) {
		
		if ( !isNullOrEmpty(key) && isNullOrEmpty(value) )
			throw new IllegalArgumentException("Le champ '" + key + "' est obligatoire.");
	}

	public static List<String> transformToList(Throwable t) {
		
		List<String> list = new ArrayList<>();
		
		if (t == null)
			return list;
		
		list.add( t.getMessage() );
		list.addAll( transformToList(t.getCause()) );
		return list;
	}
}
