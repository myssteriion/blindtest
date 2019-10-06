package com.myssteriion.blindtest.model.base;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;

public class ItemsPageTest {

	@Test
	public void constructor() {

		ItemsPage<MusicDTO> list = new ItemsPage<>(null);
		Assert.assertNotNull( list.getItems() );
		
		list = new ItemsPage<>(Arrays.asList( new MusicDTO("name", Theme.ANNEES_80) ));
		Assert.assertEquals( 1, list.getItems().size() );
	}
	
	@Test
	public void getterSeter() {

		ItemsPage<MusicDTO> list = new ItemsPage<>(null);
		Assert.assertNotNull( list.getItems() );
		Assert.assertEquals( 0, list.getItems().size() );

		list = new ItemsPage<>(Arrays.asList( new MusicDTO("name", Theme.ANNEES_80) ));
		Assert.assertEquals( 1, list.getItems().size() );
	}

}
