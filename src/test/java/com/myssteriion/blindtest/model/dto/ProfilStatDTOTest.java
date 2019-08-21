package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.tools.Tool;
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
			new ProfilStatDTO(null, 0, 0, 0, null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilId' est obligatoire."), e);
		}
		
		Assert.assertNotNull( new ProfilStatDTO(profilId) );
		
		
		
		ProfilStatDTO profilStatDto = new ProfilStatDTO(profilId, 1, 2, 3, null);
		Assert.assertEquals( profilId, profilStatDto.getProfilId() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 2, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 3, profilStatDto.getFoundMusics() );
		Assert.assertEquals( 0, profilStatDto.getBestScores().size() );

		profilStatDto = new ProfilStatDTO(profilId, -1, -2, -3, null);
		Assert.assertEquals( profilId, profilStatDto.getProfilId() );
		Assert.assertEquals( 0, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 0, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 0, profilStatDto.getFoundMusics() );
		Assert.assertEquals( 0, profilStatDto.getBestScores().size() );
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
		Assert.assertEquals( 0, profilStatDto.getBestScores().size() );

		profilStatDto.setId(123);
		Assert.assertEquals( new Integer(123), profilStatDto.getId() );
		
		profilStatDto.incrementPlayedGames();
		profilStatDto.incrementListenedMusics();
		profilStatDto.incrementFoundMusics();
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 1, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 1, profilStatDto.getFoundMusics() );

		profilStatDto.addBestScoreIfBetter(Duration.NORMAL, 10);
		Assert.assertEquals( new Integer(10), profilStatDto.getBestScores().get(Duration.NORMAL) );
		profilStatDto.addBestScoreIfBetter(Duration.NORMAL, 8);
		Assert.assertEquals( new Integer(10), profilStatDto.getBestScores().get(Duration.NORMAL) );
	}
	
	@Test
	public void toStringAndEquals() {
		
		Integer profilId = 1;
		ProfilStatDTO profilStatDtoUn = new ProfilStatDTO(profilId);
		
		Assert.assertEquals( "id=null, profilId=1, playedGames=0, listenedMusics=0, foundMusics=0, bestScores={}", profilStatDtoUn.toString() );
		
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
