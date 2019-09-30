package com.myssteriion.blindtest.model.dto;

import org.junit.Assert;
import org.junit.Test;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.tools.Constant;

public class ProfileDTOTest extends AbstractTest {

	@Test
	public void constructor() {
		
		String name = "name";
		String avatar = "avatar";


		try {
			new ProfileDTO(null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}

		try {
			new ProfileDTO("");
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}

		try {
			new ProfileDTO(null, avatar);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			new ProfileDTO("", avatar);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		Assert.assertNotNull( new ProfileDTO(name, avatar) );
		
		
		
		ProfileDTO profileDTO = new ProfileDTO(name + "'a'b'c''", avatar + "b");
		Assert.assertEquals( name + "'a'b'c''", profileDTO.getName() );
		Assert.assertEquals( avatar + "b", profileDTO.getAvatar() );
		Assert.assertFalse( profileDTO.isFileExists() );

		profileDTO = new ProfileDTO(name, null);
		Assert.assertEquals( name, profileDTO.getName() );
		Assert.assertEquals( Constant.DEFAULT_AVATAR, profileDTO.getAvatar() );
		Assert.assertEquals( Constant.DEFAULT_AVATAR, profileDTO.getAvatar() );
		Assert.assertFalse( profileDTO.isFileExists() );

		profileDTO = new ProfileDTO(name, "");
		Assert.assertEquals( name, profileDTO.getName() );
		Assert.assertEquals( Constant.DEFAULT_AVATAR, profileDTO.getAvatar() );
		Assert.assertFalse( profileDTO.isFileExists() );
	}
	
	@Test
	public void getterSetter() {
		
		String name = "name";
		String avatar = "avatar";
		
		ProfileDTO profileDTO = new ProfileDTO(name, avatar);
		Assert.assertNull( profileDTO.getId() );
		Assert.assertEquals( name, profileDTO.getName() );

		profileDTO.setId(123);
		profileDTO.setName("MonNom");
		profileDTO.setAvatar("MonAvatar");
		Assert.assertEquals( new Integer(123), profileDTO.getId() );
		Assert.assertEquals( "MonNom", profileDTO.getName() );
		Assert.assertEquals( "MonAvatar", profileDTO.getAvatar() );
		Assert.assertFalse( profileDTO.isFileExists() );
	}
	
	@Test
	public void toStringAndEquals() {
		
		String name = "name";
		String avatar = "avatar";
		ProfileDTO profileDTOUn = new ProfileDTO(name, avatar);
		
		Assert.assertEquals( "id=null, name=name, avatar=avatar, isFileExists=false", profileDTOUn.toString() );
		
		ProfileDTO profileDTOUnIso = new ProfileDTO(name, avatar);
		ProfileDTO profileDTODeux = new ProfileDTO(name + "1", avatar);
		ProfileDTO profileDTOTrois = new ProfileDTO(name + "2", avatar + "2");
		ProfileDTO profileDTOTroisIso = new ProfileDTO(name + "2", avatar);
		
		Assert.assertNotEquals(profileDTOUn, null);
		Assert.assertNotEquals(profileDTOUn, "bad class");
		Assert.assertEquals(profileDTOUn, profileDTOUn);
		Assert.assertEquals(profileDTOUn, profileDTOUnIso);
		Assert.assertNotEquals(profileDTOUn, profileDTODeux);
		Assert.assertNotEquals(profileDTOUn, profileDTOTrois);
		Assert.assertNotEquals(profileDTOUn, profileDTOTroisIso);
		Assert.assertEquals(profileDTOTrois, profileDTOTroisIso);
		
		Assert.assertEquals(profileDTOUn.hashCode(), profileDTOUn.hashCode());
		Assert.assertEquals(profileDTOUn.hashCode(), profileDTOUnIso.hashCode());
		Assert.assertNotEquals(profileDTOUn.hashCode(), profileDTODeux.hashCode());
		Assert.assertNotEquals(profileDTOUn.hashCode(), profileDTOTrois.hashCode());
		Assert.assertNotEquals(profileDTOUn.hashCode(), profileDTOTroisIso.hashCode());
		Assert.assertEquals(profileDTOTrois.hashCode(), profileDTOTroisIso.hashCode());
	}

}
