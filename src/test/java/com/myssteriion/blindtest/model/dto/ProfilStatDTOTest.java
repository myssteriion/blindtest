package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.Assert;
import org.junit.Test;

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
			new ProfilStatDTO(null, 0, null, null, null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilId' est obligatoire."), e);
		}
		
		Assert.assertNotNull( new ProfilStatDTO(profilId) );
		
		
		
		ProfilStatDTO profilStatDto = new ProfilStatDTO(profilId, 1, null, null, null);
		Assert.assertEquals( profilId, profilStatDto.getProfilId() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profilStatDto.getListenedMusics().size() );
		Assert.assertEquals( 0, profilStatDto.getFoundMusics().size() );
		Assert.assertEquals( 0, profilStatDto.getBestScores().size() );

		profilStatDto = new ProfilStatDTO(profilId, -1, null, null, null);
		Assert.assertEquals( profilId, profilStatDto.getProfilId() );
		Assert.assertEquals( 0, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profilStatDto.getListenedMusics().size() );
		Assert.assertEquals( 0, profilStatDto.getFoundMusics().size() );
		Assert.assertEquals( 0, profilStatDto.getBestScores().size() );
	}
	
	@Test
	public void getterSetter() {
		
		Integer profilId = 1;
		
		ProfilStatDTO profilStatDto = new ProfilStatDTO(profilId);
		Assert.assertNull( profilStatDto.getId() );
		Assert.assertEquals( profilId, profilStatDto.getProfilId() );
		Assert.assertEquals( 0, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profilStatDto.getListenedMusics().size() );
		Assert.assertEquals( 0, profilStatDto.getFoundMusics().size() );
		Assert.assertEquals( 0, profilStatDto.getBestScores().size() );

		profilStatDto.setId(123);
		Assert.assertEquals( new Integer(123), profilStatDto.getId() );
		
		profilStatDto.incrementPlayedGames();
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );

		profilStatDto.incrementListenedMusics(Theme.ANNEES_80);
		profilStatDto.incrementFoundMusics(Theme.ANNEES_80);
		profilStatDto.addBestScoreIfBetter(Duration.NORMAL, 10);
		Assert.assertEquals( new Integer(1), profilStatDto.getListenedMusics().get(Theme.ANNEES_80) );
		Assert.assertEquals( new Integer(1), profilStatDto.getFoundMusics().get(Theme.ANNEES_80) );
		Assert.assertEquals( new Integer(10), profilStatDto.getBestScores().get(Duration.NORMAL) );

		profilStatDto.incrementListenedMusics(Theme.ANNEES_80);
		profilStatDto.incrementFoundMusics(Theme.ANNEES_80);
		profilStatDto.addBestScoreIfBetter(Duration.NORMAL, 100);
		Assert.assertEquals( new Integer(2), profilStatDto.getListenedMusics().get(Theme.ANNEES_80) );
		Assert.assertEquals( new Integer(2), profilStatDto.getFoundMusics().get(Theme.ANNEES_80) );
		Assert.assertEquals( new Integer(100), profilStatDto.getBestScores().get(Duration.NORMAL) );
	}
	
	@Test
	public void toStringAndEquals() {
		
		Integer profilId = 1;
		ProfilStatDTO profilStatDtoUn = new ProfilStatDTO(profilId);
		
		Assert.assertEquals( "id=null, profilId=1, playedGames=0, listenedMusics={}, foundMusics={}, bestScores={}", profilStatDtoUn.toString() );
		
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
