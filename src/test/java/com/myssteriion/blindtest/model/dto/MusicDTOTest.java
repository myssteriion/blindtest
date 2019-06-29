package com.myssteriion.blindtest.model.dto;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;

public class MusicDTOTest extends AbstractTest {

	@Test
	public void constructor() {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		
		try {
			new MusicDTO(null, theme);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			new MusicDTO("", theme);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			new MusicDTO(name, null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'theme' est obligatoire."), e);
		}
		
		Assert.assertNotNull( new MusicDTO(name, theme) );
		
		
		
		MusicDTO musicDTO = new MusicDTO(name + "'a'b'c''", theme, 2);
		Assert.assertEquals( name + "'a'b'c''", musicDTO.getName() );
		Assert.assertEquals( 2, musicDTO.getPlayed() );
		
		musicDTO = new MusicDTO(name, theme, -2);
		Assert.assertEquals( 0, musicDTO.getPlayed() );
	}
	
	@Test
	public void getterSetter() {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		MusicDTO musicDTO = new MusicDTO(name, theme);
		Assert.assertNull( musicDTO.getId() );
		Assert.assertEquals( name, musicDTO.getName() );
		Assert.assertEquals( theme, musicDTO.getTheme() );
		Assert.assertEquals( 0, musicDTO.getPlayed() );
		
		musicDTO.setId("123");
		Assert.assertEquals( "123", musicDTO.getId() );
		
		musicDTO.incrementPlayed();
		Assert.assertEquals( 1, musicDTO.getPlayed() );
	}
	
	@Test
	public void toStringAndEquals() {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		MusicDTO musicDTOUn = new MusicDTO(name, theme);
		
		Assert.assertEquals( "id=null, name=name, theme=ANNEES_80, played=0", musicDTOUn.toString() );
		
		MusicDTO musicDTOUnIso = new MusicDTO(name, theme);
		MusicDTO musicDTODeux = new MusicDTO(name + "1", theme);
		MusicDTO musicDTOTrois = new MusicDTO(name, Theme.ANNEES_90);
		MusicDTO musicDTOQuatre = new MusicDTO(name + "1", Theme.ANNEES_90);
		MusicDTO musicDTOCinq = new MusicDTO(name, theme, 10);
		
		Assert.assertNotEquals(musicDTOUn, null);
		Assert.assertNotEquals(musicDTOUn, "bad class");
		Assert.assertEquals(musicDTOUn, musicDTOUn);
		Assert.assertEquals(musicDTOUn, musicDTOUnIso);
		Assert.assertNotEquals(musicDTOUn, musicDTODeux);
		Assert.assertNotEquals(musicDTOUn, musicDTOTrois);
		Assert.assertNotEquals(musicDTOUn, musicDTOQuatre);
		Assert.assertEquals(musicDTOUn, musicDTOCinq);
		
		Assert.assertEquals(musicDTOUn.hashCode(), musicDTOUn.hashCode());
		Assert.assertEquals(musicDTOUn.hashCode(), musicDTOUnIso.hashCode());
		Assert.assertNotEquals(musicDTOUn.hashCode(), musicDTODeux.hashCode());
		Assert.assertNotEquals(musicDTOUn.hashCode(), musicDTOTrois.hashCode());
		Assert.assertNotEquals(musicDTOUn.hashCode(), musicDTOQuatre.hashCode());
		Assert.assertEquals(musicDTOUn.hashCode(), musicDTOCinq.hashCode());
	}

}
