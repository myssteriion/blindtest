package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.Assert;
import org.junit.Test;

public class ProfileStatDTOTest extends AbstractTest {

	@Test
	public void constructor() {
		
		Integer profileId = 1;
		
		try {
			new ProfileStatDTO(null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileId' est obligatoire."), e);
		}
		
		try {
			new ProfileStatDTO(null, 0, null, null, null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileId' est obligatoire."), e);
		}
		
		Assert.assertNotNull( new ProfileStatDTO(profileId) );
		
		
		
		ProfileStatDTO profileStatDto = new ProfileStatDTO(profileId, 1, null, null, null);
		Assert.assertEquals( profileId, profileStatDto.getProfileId() );
		Assert.assertEquals( 1, profileStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profileStatDto.getListenedMusics().size() );
		Assert.assertEquals( 0, profileStatDto.getFoundMusics().size() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );

		profileStatDto = new ProfileStatDTO(profileId, -1, null, null, null);
		Assert.assertEquals( profileId, profileStatDto.getProfileId() );
		Assert.assertEquals( 0, profileStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profileStatDto.getListenedMusics().size() );
		Assert.assertEquals( 0, profileStatDto.getFoundMusics().size() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
	}
	
	@Test
	public void getterSetter() {
		
		Integer profileId = 1;
		
		ProfileStatDTO profileStatDto = new ProfileStatDTO(profileId);
		Assert.assertNull( profileStatDto.getId() );
		Assert.assertEquals( profileId, profileStatDto.getProfileId() );
		Assert.assertEquals( 0, profileStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profileStatDto.getListenedMusics().size() );
		Assert.assertEquals( 0, profileStatDto.getFoundMusics().size() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );

		profileStatDto.setId(123);
		Assert.assertEquals( new Integer(123), profileStatDto.getId() );
		
		profileStatDto.incrementPlayedGames();
		Assert.assertEquals( 1, profileStatDto.getPlayedGames() );

		profileStatDto.incrementListenedMusics(Theme.ANNEES_80);
		profileStatDto.incrementFoundMusics(Theme.ANNEES_80);
		profileStatDto.addBestScoreIfBetter(Duration.NORMAL, 10);
		Assert.assertEquals( new Integer(1), profileStatDto.getListenedMusics().get(Theme.ANNEES_80) );
		Assert.assertEquals( new Integer(1), profileStatDto.getFoundMusics().get(Theme.ANNEES_80) );
		Assert.assertEquals( new Integer(10), profileStatDto.getBestScores().get(Duration.NORMAL) );

		profileStatDto.incrementListenedMusics(Theme.ANNEES_80);
		profileStatDto.incrementFoundMusics(Theme.ANNEES_80);
		profileStatDto.addBestScoreIfBetter(Duration.NORMAL, 100);
		Assert.assertEquals( new Integer(2), profileStatDto.getListenedMusics().get(Theme.ANNEES_80) );
		Assert.assertEquals( new Integer(2), profileStatDto.getFoundMusics().get(Theme.ANNEES_80) );
		Assert.assertEquals( new Integer(100), profileStatDto.getBestScores().get(Duration.NORMAL) );
	}
	
	@Test
	public void toStringAndEquals() {
		
		Integer profileId = 1;
		ProfileStatDTO profileStatDtoUn = new ProfileStatDTO(profileId);
		
		Assert.assertEquals( "id=null, profileId=1, playedGames=0, listenedMusics={}, foundMusics={}, bestScores={}", profileStatDtoUn.toString() );
		
		ProfileStatDTO profileStatDtoUnIso = new ProfileStatDTO(1);
		ProfileStatDTO profileStatDtoDeux = new ProfileStatDTO(2);
		
		Assert.assertNotEquals(profileStatDtoUn, null);
		Assert.assertNotEquals(profileStatDtoUn, "bad class");
		Assert.assertEquals(profileStatDtoUn, profileStatDtoUn);
		Assert.assertEquals(profileStatDtoUn, profileStatDtoUnIso);
		Assert.assertNotEquals(profileStatDtoUn, profileStatDtoDeux);
		
		Assert.assertEquals(profileStatDtoUn.hashCode(), profileStatDtoUn.hashCode());
		Assert.assertEquals(profileStatDtoUn.hashCode(), profileStatDtoUnIso.hashCode());
		Assert.assertNotEquals(profileStatDtoUn.hashCode(), profileStatDtoDeux.hashCode());
	}

}
