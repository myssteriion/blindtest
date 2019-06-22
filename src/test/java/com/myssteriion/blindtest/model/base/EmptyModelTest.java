package com.myssteriion.blindtest.model.base;

import org.junit.Assert;
import org.junit.Test;

public class EmptyModelTest {

	@Test
	public void constructor() {
		Assert.assertNotNull( new Empty() );
	}

}
