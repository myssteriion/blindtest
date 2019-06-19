package com.myssteriion.blindtest.model.base;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;

public class ListModelTest {

	@Test
	public void constructor() {

		ListModel<MusicDTO> list = new ListModel<>(null);
		Assert.assertNotNull( list.getItems() );
		
		list = new ListModel<>(Arrays.asList( new MusicDTO("name", Theme.ANNEES_80) ));
		Assert.assertEquals( 1, list.getItems().size() );
	}

}