package com.myssteriion.blindtest.db.dao;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.EntityManager;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
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


public class ProfilStatDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private ProfilStatDAO profilStatDao;

	@Mock
	protected ObjectMapper mapperMock;



	@Test
	public void save() throws DaoException, SQLException {

		profilStatDao = Mockito.spy( new ProfilStatDAO() );
		MockitoAnnotations.initMocks(this);

		ProfilStatDTO profilStatDto = new ProfilStatDTO(1);
		profilStatDto.setId(1);
		Mockito.doReturn(profilStatDto).when(profilStatDao).find(Mockito.any(ProfilStatDTO.class));
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilStatDao.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto' est obligatoire."), e);
		}
		
		
		ProfilStatDTO profilStatDtoToSave = new ProfilStatDTO(1);
		try {
			profilStatDao.save(profilStatDtoToSave);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't save profilStatDto.", sql), e);
		}
		
		ProfilStatDTO profilStatDtoReturned = profilStatDao.save(profilStatDtoToSave);
		Assert.assertEquals( new Integer(1), profilStatDtoReturned.getId() );
	}

	@SuppressWarnings("deprecation")
	@Test
	public void update() throws DaoException, SQLException, JsonProcessingException, NoSuchFieldException, IllegalAccessException {

		ProfilStatDTO profilStatDto = new ProfilStatDTO(1);
		profilStatDto.setId(1);
		

		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);

		JsonProcessingException jpe = new JsonGenerationException("jpe");
		Mockito.when(mapperMock.writeValueAsString(Mockito.anyMap())).thenThrow(jpe).thenCallRealMethod();

		try {
			profilStatDao.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto' est obligatoire."), e);
		}
		
		
		ProfilStatDTO profilStatDtoToUpdate = new ProfilStatDTO(1);
		try {
			profilStatDao.update(profilStatDtoToUpdate);
			Assert.fail("Doit lever une IllegalArgumentException car il manque l'id.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto -> id' est obligatoire."), e);
		}
		
		profilStatDtoToUpdate.setId(1);
		setMapper(mapperMock);
		try {
			profilStatDao.update(profilStatDtoToUpdate);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't parse 'bestScores' profilStatDto.", jpe), e);
		}
		setMapper( new ObjectMapper() );

		try {
			profilStatDao.update(profilStatDtoToUpdate);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't update profilStatDto.", sql), e);
		}

		ProfilStatDTO profilStatDtoReturned = profilStatDao.update(profilStatDtoToUpdate);
		Assert.assertEquals( new Integer(1), profilStatDtoReturned.getId() );
	}

	@SuppressWarnings("deprecation")
	@Test
	public void find() throws SQLException, DaoException, IOException, NoSuchFieldException, IllegalAccessException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow(1, 1, 1, 2, 3, "{\"NORMAL\":100}");
		rs.addRow(2, 1, 1, 2, 3, "{\"NORMAL\":100}");

		SimpleResultSet rs2 = getResultSet();
		rs2.addRow(1, 1, 1, 2, 3, "{\"NORMAL\":100}");
		rs2.addRow(2, 1, 1, 2, 3, "{\"NORMAL\":100}");

		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rs, rsEmpty, rs2);
		Mockito.when(em.createStatement()).thenReturn(statement);

		JsonProcessingException jpe = new JsonGenerationException("jpe");
		Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenThrow(jpe).thenCallRealMethod();
		
		try {
			profilStatDao.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto' est obligatoire."), e);
		}
		
		
		ProfilStatDTO profilStatDto = new ProfilStatDTO(1);
		try {
			profilStatDao.find(profilStatDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't find profilStatDto.", sql), e);
		}

		setMapper(mapperMock);
		try {
			profilStatDao.find(profilStatDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't parse 'bestScores' profilStatDto.", jpe), e);
		}
		setMapper( new ObjectMapper() );

		profilStatDto = profilStatDao.find(profilStatDto);
		Assert.assertNull(profilStatDto);
		
		profilStatDto = new ProfilStatDTO(1);
		profilStatDto = profilStatDao.find(profilStatDto);
		Assert.assertNotNull(profilStatDto);
		
		profilStatDto.setId(1);
		profilStatDto = profilStatDao.find(profilStatDto);
		Assert.assertNotNull(profilStatDto);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void findAll() throws DaoException, SQLException, IOException, NoSuchFieldException, IllegalAccessException {

		SimpleResultSet rs = getResultSet();
		rs.addRow(1, 1, 1, 2, 3, "{\"NORMAL\":100}");

		SimpleResultSet rs2 = getResultSet();
		rs2.addRow(1, 1, 1, 2, 3, "{\"NORMAL\":100}");

		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rs, rs2);
		Mockito.when(em.createStatement()).thenReturn(statement);

		JsonProcessingException jpe = new JsonGenerationException("jpe");
		Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.any(TypeReference.class))).thenThrow(jpe).thenCallRealMethod();
		
		try {
			profilStatDao.findAll();
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't find all profilStatDto.", sql), e);
		}

		setMapper(mapperMock);
		try {
			profilStatDao.findAll();
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't parse 'bestScores' profilStatDto.", jpe), e);
		}
		setMapper( new ObjectMapper() );


		ProfilStatDTO profilStatDto = profilStatDao.findAll().get(0);
		Assert.assertEquals( new Integer(1), profilStatDto.getId() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 2, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 3, profilStatDto.getFoundMusics() );
		Assert.assertEquals( new Integer(100), profilStatDto.getBestScores().get(Duration.NORMAL) );
	}
	
	
	
	private SimpleResultSet getResultSet() {
		
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("id", Types.BIGINT, 100, 0);
		rs.addColumn("profil_id", Types.BIGINT, 100, 0);
		rs.addColumn("played_games", Types.INTEGER, 100, 0);
		rs.addColumn("listened_musics", Types.INTEGER, 100, 0);
		rs.addColumn("found_musics", Types.INTEGER, 100, 0);
		rs.addColumn("best_scores", Types.VARCHAR, 100, 0);
		return rs;
	}
	
}
