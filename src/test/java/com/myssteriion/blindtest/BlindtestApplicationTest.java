package com.myssteriion.blindtest;

import org.junit.Test;

import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

public class BlindtestApplicationTest extends AbstractTest {

	@Test
	public void constructorStaticClass() {

		new Constant();
		new Tool();
	}

}
