package com.myssteriion.blindtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.tools.Tool;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractTest {

	protected void verifyException(Throwable expected, Throwable actual) {

		if (expected == null && actual == null) {
			return;
		}
		if (expected == null && actual != null) {
			Assert.assertNull(actual);
		}
		else if (expected != null && actual == null) {
			Assert.assertNotNull(actual);
		}
		
		Assert.assertEquals( expected.getClass(), actual.getClass() );
		Assert.assertEquals( expected.getMessage(), actual.getMessage() );
		
		verifyException( expected.getCause(), actual.getCause() );
	}


	protected void setMapper(ObjectMapper mockOrReal) throws NoSuchFieldException, IllegalAccessException {

		Field f = Tool.class.getDeclaredField("MAPPER");
		f.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);

		f.set(null, mockOrReal);
	}

}
