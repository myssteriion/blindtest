package com.myssteriion.blindtest.db.dao;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.EntityManager;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import org.h2.tools.SimpleResultSet;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;


public class ProfileStatDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private ProfileStatDAO profileStatDao;

	@Mock
	protected ObjectMapper mapperMock;



	@Test
	public void save() throws DaoException, SQLException {

		profileStatDao = Mockito.spy( new ProfileStatDAO() );
		MockitoAnnotations.initMocks(this);

		ProfileStatDTO profileStatDto = new ProfileStatDTO(1);
		profileStatDto.setId(1);
		Mockito.doReturn(profileStatDto).when(profileStatDao).find(Mockito.any(ProfileStatDTO.class));
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profileStatDao.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileStatDto' est obligatoire."), e);
		}
		
		
		ProfileStatDTO profileStatDtoToSave = new ProfileStatDTO(1);
		try {
			profileStatDao.save(profileStatDtoToSave);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't save profileStatDto.", sql), e);
		}
		
		ProfileStatDTO profileStatDtoReturned = profileStatDao.save(profileStatDtoToSave);
		Assert.assertEquals( new Integer(1), profileStatDtoReturned.getId() );
	}

	@SuppressWarnings("deprecation")
	@Test
	public void update() throws DaoException, SQLException, JsonProcessingException, NoSuchFieldException, IllegalAccessException {

		ProfileStatDTO profileStatDto = new ProfileStatDTO(1);
		profileStatDto.setId(1);
		

		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);

		JsonProcessingException jpe = new JsonGenerationException("jpe");
		Mockito.when(mapperMock.writeValueAsString(Mockito.anyMap())).thenThrow(jpe).thenCallRealMethod();

		try {
			profileStatDao.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileStatDto' est obligatoire."), e);
		}
		
		
		ProfileStatDTO profileStatDtoToUpdate = new ProfileStatDTO(1);
		try {
			profileStatDao.update(profileStatDtoToUpdate);
			Assert.fail("Doit lever une IllegalArgumentException car il manque l'id.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileStatDto -> id' est obligatoire."), e);
		}
		
		profileStatDtoToUpdate.setId(1);
		setMapper(mapperMock);
		try {
			profileStatDao.update(profileStatDtoToUpdate);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't parse profileStatDto.", jpe), e);
		}
		setMapper( new ObjectMapper() );

		try {
			profileStatDao.update(profileStatDtoToUpdate);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't update profileStatDto.", sql), e);
		}

		ProfileStatDTO profileStatDtoReturned = profileStatDao.update(profileStatDtoToUpdate);
		Assert.assertEquals( new Integer(1), profileStatDtoReturned.getId() );
	}

	@SuppressWarnings("deprecation")
	@Test
	public void find() throws SQLException, DaoException, IOException, NoSuchFieldException, IllegalAccessException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow(1, 1, 1, "{\"ANNEES_60\":100}", "{\"ANNEES_60\":50}", "{\"NORMAL\":100}");
		rs.addRow(2, 1, 1, "{\"ANNEES_60\":100}", "{\"ANNEES_60\":50}", "{\"NORMAL\":100}");

		SimpleResultSet rs2 = getResultSet();
		rs2.addRow(1, 1, 1, "{\"ANNEES_60\":100}", "{\"ANNEES_60\":50}", "{\"NORMAL\":100}");
		rs2.addRow(2, 1, 1, "{\"ANNEES_60\":100}", "{\"ANNEES_60\":50}", "{\"NORMAL\":100}");

		SimpleResultSet rs3 = getResultSet();
		rs3.addRow(1, 1, 1, "{\"ANNEES_60\":100}", "{\"ANNEES_60\":50}", "{\"NORMAL\":100}");
		rs3.addRow(2, 1, 1, "{\"ANNEES_60\":100}", "{\"ANNEES_60\":50}", "{\"NORMAL\":100}");
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rs, rsEmpty, rs2, rs3);
		Mockito.when(em.createStatement()).thenReturn(statement);

		JsonProcessingException jpe = new JsonGenerationException("jpe");
		Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenThrow(jpe).thenCallRealMethod();
		
		try {
			profileStatDao.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileStatDto' est obligatoire."), e);
		}
		
		
		ProfileStatDTO profileStatDto = new ProfileStatDTO(1);
		try {
			profileStatDao.find(profileStatDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't find profileStatDto.", sql), e);
		}

		setMapper(mapperMock);
		try {
			profileStatDao.find(profileStatDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't parse profileStatDto.", jpe), e);
		}
		setMapper( new ObjectMapper() );

		profileStatDto = profileStatDao.find(profileStatDto);
		Assert.assertNull(profileStatDto);
		
		profileStatDto = new ProfileStatDTO(1);
		profileStatDto = profileStatDao.find(profileStatDto);
		Assert.assertNotNull(profileStatDto);
		
		profileStatDto.setId(1);
		profileStatDto = profileStatDao.find(profileStatDto);
		Assert.assertNotNull(profileStatDto);
		Assert.assertEquals( new Integer(1), profileStatDto.getId() );
		Assert.assertEquals( 1, profileStatDto.getPlayedGames() );
		Assert.assertEquals( new Integer(100), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(50), profileStatDto.getFoundMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(100), profileStatDto.getBestScores().get(Duration.NORMAL) );
	}

	@SuppressWarnings("deprecation")
	@Test
	public void findAll() throws DaoException, SQLException, IOException, NoSuchFieldException, IllegalAccessException {

		SimpleResultSet rs = getResultSet();
		rs.addRow(1, 1, 1, "{\"ANNEES_60\":100}", "{\"ANNEES_60\":50}", "{\"NORMAL\":100}");

		SimpleResultSet rs2 = getResultSet();
		rs2.addRow(1, 1, 1, "{\"ANNEES_60\":100}", "{\"ANNEES_60\":50}", "{\"NORMAL\":100}");

		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rs, rs2);
		Mockito.when(em.createStatement()).thenReturn(statement);

		JsonProcessingException jpe = new JsonGenerationException("jpe");
		Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenThrow(jpe).thenCallRealMethod();
		
		try {
			profileStatDao.findAll();
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't find all profileStatDto.", sql), e);
		}

		setMapper(mapperMock);
		try {
			profileStatDao.findAll();
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't parse profileStatDto.", jpe), e);
		}
		setMapper( new ObjectMapper() );


		ProfileStatDTO profileStatDto = profileStatDao.findAll().get(0);
		Assert.assertEquals( new Integer(1), profileStatDto.getId() );
		Assert.assertEquals( 1, profileStatDto.getPlayedGames() );
		Assert.assertEquals( new Integer(100), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(50), profileStatDto.getFoundMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(100), profileStatDto.getBestScores().get(Duration.NORMAL) );
	}
	
	
	
	private SimpleResultSet getResultSet() {
		
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("id", Types.BIGINT, 100, 0);
		rs.addColumn("profile_id", Types.BIGINT, 100, 0);
		rs.addColumn("played_games", Types.INTEGER, 100, 0);
		rs.addColumn("listened_musics", Types.INTEGER, 100, 0);
		rs.addColumn("found_musics", Types.INTEGER, 100, 0);
		rs.addColumn("best_scores", Types.VARCHAR, 100, 0);
		return rs;
	}
	
}
