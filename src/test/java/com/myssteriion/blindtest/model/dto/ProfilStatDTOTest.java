package com.myssteriion.blindtest.model.dto;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;

public class ProfilStatDTOTest extends AbstractTest {

	@Test
	public void constructor() {
		
		Integer profilId = 1;
		
		try {
			new ProfilStatDTO(null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilId' est obligatoire."), e);
		}
		
		try {
			new ProfilStatDTO(null, 0, 0, 0);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilId' est obligatoire."), e);
		}
		
		Assert.assertNotNull( new ProfilStatDTO(profilId) );
		
		
		
		ProfilStatDTO profilStatDto = new ProfilStatDTO(profilId, 1, 2, 3);
		Assert.assertEquals( profilId, profilStatDto.getProfilId() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 2, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 3, profilStatDto.getFoundMusics() );
		
		profilStatDto = new ProfilStatDTO(profilId, -1, -2, -3);
		Assert.assertEquals( profilId, profilStatDto.getProfilId() );
		Assert.assertEquals( 0, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 0, profilStatDto.getFoundMusics() );
	}
	
	@Test
	public void getterSetter() {
		
		Integer profilId = 1;
		
		ProfilStatDTO profilStatDto = new ProfilStatDTO(profilId);
		Assert.assertNull( profilStatDto.getId() );
		Assert.assertEquals( profilId, profilStatDto.getProfilId() );
		Assert.assertEquals( 0, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 0, profilStatDto.getFoundMusics() );
		
		profilStatDto.setId(123);
		Assert.assertEquals( new Integer(123), profilStatDto.getId() );
		
		profilStatDto.incrementPlayedGames();
		profilStatDto.incrementListenedMusics();
		profilStatDto.incrementFoundMusics();
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 1, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 1, profilStatDto.getFoundMusics() );
	}
	
	@Test
	public void toStringAndEquals() {
		
		Integer profilId = 1;
		ProfilStatDTO profilStatDtoUn = new ProfilStatDTO(profilId);
		
		Assert.assertEquals( "id=null, profilId=1, playedGames=0, listenedMusics=0, foundMusics=0", profilStatDtoUn.toString() );
		
		ProfilStatDTO profilStatDtoUnIso = new ProfilStatDTO(1);
		ProfilStatDTO profilStatDtoDeux = new ProfilStatDTO(2);
		
		Assert.assertNotEquals(profilStatDtoUn, null);
		Assert.assertNotEquals(profilStatDtoUn, "bad class");
		Assert.assertEquals(profilStatDtoUn, profilStatDtoUn);
		Assert.assertEquals(profilStatDtoUn, profilStatDtoUnIso);
		Assert.assertNotEquals(profilStatDtoUn, profilStatDtoDeux);
		
		Assert.assertEquals(profilStatDtoUn.hashCode(), profilStatDtoUn.hashCode());
		Assert.assertEquals(profilStatDtoUn.hashCode(), profilStatDtoUnIso.hashCode());
		Assert.assertNotEquals(profilStatDtoUn.hashCode(), profilStatDtoDeux.hashCode());
	}

}
