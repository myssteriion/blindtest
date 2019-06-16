package com.myssteriion.blindtest.tools;

import java.util.List;
import java.util.Map;

public class Tool {

	public static boolean isNullOrEmpty(Object o) {
		
		if (o == null) {
			return true;
		}
		else if (o instanceof String) {
			return ((String) o).isEmpty();
		}
		else if (o instanceof List) {
			return ((List<?>) o).isEmpty();
		}
		else if (o instanceof Map) {
			return ((Map<?, ?>) o).isEmpty();
		}
		else {
			return false;
		}
	}

	public static void verifyValue(String key, Object value) {
		
		if ( !isNullOrEmpty(key) && isNullOrEmpty(value) )
			throw new IllegalArgumentException("Le champ '" + key + "' est obligatoire.");
	}

}
