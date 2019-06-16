package com.myssteriion.blindtest;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

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
	
}
