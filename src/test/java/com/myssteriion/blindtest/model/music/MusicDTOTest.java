package com.myssteriion.blindtest.model.music;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;

public class MusicDTOTest extends AbstractTest {

	@Test
	public void constructor() {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		
		try {
			new MusicDTO(null, theme);
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			new MusicDTO("", theme);
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			new MusicDTO(name, null);
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'theme' est obligatoire."), e);
		}
		
		Assert.assertNotNull( new MusicDTO(name, theme) );
		
		
		
		MusicDTO musicDTO = new MusicDTO(name, theme, 2);
		Assert.assertEquals( 2, musicDTO.getNbPlayed() );
		
		musicDTO = new MusicDTO(name, theme, -2);
		Assert.assertEquals( 0, musicDTO.getNbPlayed() );
	}
	
	@Test
	public void getterSetter() {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		MusicDTO musicDTO = new MusicDTO(name, theme);
		Assert.assertNull( musicDTO.getId() );
		Assert.assertEquals( name, musicDTO.getName() );
		Assert.assertEquals( theme, musicDTO.getTheme() );
		Assert.assertEquals( 0, musicDTO.getNbPlayed() );
		
		musicDTO.setId("123");
		Assert.assertEquals( "123", musicDTO.getId() );
		
		musicDTO.incrementNbPlayed();
		Assert.assertEquals( 1, musicDTO.getNbPlayed() );
	}
	
	@Test
	public void toStringAndEquals() {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		MusicDTO musicDTOUn = new MusicDTO(name, theme);
		
		Assert.assertEquals( "id=null, name=name, theme=ANNEES_80, nbPlayed=0", musicDTOUn.toString() );
		
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
