package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.tools.Constant;
import org.junit.Assert;
import org.junit.Test;

public class ProfileDTOTest extends AbstractTest {

	@Test
	public void constructor() {
		
		String name = "name";
		Avatar avatar = new Avatar("avatar");


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
		
		
		
		ProfileDTO profileDTO = new ProfileDTO(name + "'a'b'c''", new Avatar(avatar + "b"));
		Assert.assertEquals( name + "'a'b'c''", profileDTO.getName() );
		Assert.assertEquals( avatar + "b", profileDTO.getAvatar().getName() );

		profileDTO = new ProfileDTO(name + "'a'b'c''", null);
		Assert.assertEquals( name + "'a'b'c''", profileDTO.getName() );
		Assert.assertEquals( "defaut.png", profileDTO.getAvatar().getName() );

		profileDTO = new ProfileDTO(name, new Avatar(""));
		Assert.assertEquals( name, profileDTO.getName() );
		Assert.assertEquals( Constant.DEFAULT_AVATAR, profileDTO.getAvatar().getName() );
	}
	
	@Test
	public void getterSetter() {
		
		String name = "name";
		Avatar avatar = new Avatar("avatar");
		
		ProfileDTO profileDTO = new ProfileDTO(name, avatar);
		Assert.assertNull( profileDTO.getId() );
		Assert.assertEquals( name, profileDTO.getName() );

		profileDTO.setId(123);
		profileDTO.setName("MonNom");
		profileDTO.setAvatar( new Avatar("MonAvatar") );
		Assert.assertEquals( new Integer(123), profileDTO.getId() );
		Assert.assertEquals( "MonNom", profileDTO.getName() );
		Assert.assertEquals( "MonAvatar", profileDTO.getAvatar().getName() );
	}
	
	@Test
	public void toStringAndEquals() {
		
		String name = "name";
		Avatar avatar = new Avatar("avatar");
		ProfileDTO profileDTOUn = new ProfileDTO(name, avatar);
		
		Assert.assertEquals( "id=null, name=name, avatar={name=avatar, fileExists=false, contentType=null}", profileDTOUn.toString() );
		
		ProfileDTO profileDTOUnIso = new ProfileDTO(name, avatar);
		ProfileDTO profileDTODeux = new ProfileDTO(name + "1", avatar);
		ProfileDTO profileDTOTrois = new ProfileDTO(name + "2", new Avatar("avatar2"));
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
