package com.myssteriion.blindtest.model.dto;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;

public class ProfilDTOTest extends AbstractTest {

	@Test
	public void constructor() {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			new ProfilDTO(null, avatar);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			new ProfilDTO("", avatar);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			new ProfilDTO(name, null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'avatar' est obligatoire."), e);
		}
		
		try {
			new ProfilDTO(name, "");
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'avatar' est obligatoire."), e);
		}
		
		Assert.assertNotNull( new ProfilDTO(name, avatar) );
		
		
		
		ProfilDTO profilDTO = new ProfilDTO(name + "'a'b'c''", avatar + "b");
		Assert.assertEquals( name + "'a'b'c''", profilDTO.getName() );
		Assert.assertEquals( avatar + "b", profilDTO.getAvatar() );
	}
	
	@Test
	public void getterSetter() {
		
		String name = "name";
		String avatar = "avatar";
		
		ProfilDTO profilDTO = new ProfilDTO(name, avatar);
		Assert.assertNull( profilDTO.getId() );
		Assert.assertEquals( name, profilDTO.getName() );

		profilDTO.setId("123");
		profilDTO.setName("MonNom");
		profilDTO.setAvatar("MonAvatar");
		Assert.assertEquals( "123", profilDTO.getId() );
		Assert.assertEquals( "MonNom", profilDTO.getName() );
		Assert.assertEquals( "MonAvatar", profilDTO.getAvatar() );
	}
	
	@Test
	public void toStringAndEquals() {
		
		String name = "name";
		String avatar = "avatar";
		ProfilDTO profilDTOUn = new ProfilDTO(name, avatar);
		
		Assert.assertEquals( "id=null, name=name, avatar=avatar", profilDTOUn.toString() );
		
		ProfilDTO profilDTOUnIso = new ProfilDTO(name, avatar);
		ProfilDTO profilDTODeux = new ProfilDTO(name + "1", avatar);
		ProfilDTO profilDTOTrois = new ProfilDTO(name + "2", avatar + "2");
		ProfilDTO profilDTOTroisIso = new ProfilDTO(name + "2", avatar);
		
		Assert.assertNotEquals(profilDTOUn, null);
		Assert.assertNotEquals(profilDTOUn, "bad class");
		Assert.assertEquals(profilDTOUn, profilDTOUn);
		Assert.assertEquals(profilDTOUn, profilDTOUnIso);
		Assert.assertNotEquals(profilDTOUn, profilDTODeux);
		Assert.assertNotEquals(profilDTOUn, profilDTOTrois);
		Assert.assertNotEquals(profilDTOUn, profilDTOTroisIso);
		Assert.assertEquals(profilDTOTrois, profilDTOTroisIso);
		
		Assert.assertEquals(profilDTOUn.hashCode(), profilDTOUn.hashCode());
		Assert.assertEquals(profilDTOUn.hashCode(), profilDTOUnIso.hashCode());
		Assert.assertNotEquals(profilDTOUn.hashCode(), profilDTODeux.hashCode());
		Assert.assertNotEquals(profilDTOUn.hashCode(), profilDTOTrois.hashCode());
		Assert.assertNotEquals(profilDTOUn.hashCode(), profilDTOTroisIso.hashCode());
		Assert.assertEquals(profilDTOTrois.hashCode(), profilDTOTroisIso.hashCode());
	}

}
