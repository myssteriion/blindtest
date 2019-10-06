package com.myssteriion.blindtest.model.base;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;

public class ItemsListTest {

	@Test
	public void constructor() {

		ItemsList<MusicDTO> list = new ItemsList<>(null);
		Assert.assertNotNull( list.getItems() );
		
		list = new ItemsList<>(Arrays.asList( new MusicDTO("name", Theme.ANNEES_80) ));
		Assert.assertEquals( 1, list.getItems().size() );
	}
	
	@Test
	public void getterSeter() {

		ItemsList<MusicDTO> list = new ItemsList<>(null);
		Assert.assertNotNull( list.getItems() );
		Assert.assertEquals( 0, list.getItems().size() );
		Assert.assertEquals( 0, list.getSize() );
		
		list = new ItemsList<>(Arrays.asList( new MusicDTO("name", Theme.ANNEES_80) ));
		Assert.assertEquals( 1, list.getItems().size() );
		Assert.assertEquals( 1, list.getSize() );
	}

}
