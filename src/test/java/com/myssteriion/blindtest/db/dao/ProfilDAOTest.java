package com.myssteriion.blindtest.db.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.h2.tools.SimpleResultSet;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.EntityManager;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.ProfilDTO;


public class ProfilDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private ProfilDAO profilDao;
	
	
	
	@Test
	public void save() throws SqlException, SQLException {

		profilDao = Mockito.spy( new ProfilDAO() );
		MockitoAnnotations.initMocks(this);

		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		profilDto.setId("1");
		Mockito.doReturn(profilDto).when(profilDao).find(Mockito.any(ProfilDTO.class));
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilDao.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto' est obligatoire."), e);
		}
		
		
		ProfilDTO profilDtoToSave = new ProfilDTO("name", "avatar");
		try {
			profilDao.save(profilDtoToSave);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't save profilDto.", sql), e);
		}
		
		ProfilDTO profilDtoReturned = profilDao.save(profilDtoToSave);
		Assert.assertEquals(profilDtoReturned.getId(), "1");
	}
	
	@Test
	public void update() throws SqlException, SQLException {

		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		profilDto.setId("1");
		

		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilDao.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto' est obligatoire."), e);
		}
		
		
		ProfilDTO profilDtoToUpdate = new ProfilDTO("name", "avatar");
		try {
			profilDao.update(profilDtoToUpdate);
			Assert.fail("Doit lever une IllegalArgumentException car il manque l'id.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto -> id' est obligatoire."), e);
		}
		
		profilDtoToUpdate.setId("1");
		try {
			profilDao.update(profilDtoToUpdate);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't update profilDto.", sql), e);
		}
		
		ProfilDTO profilDtoReturned = profilDao.update(profilDtoToUpdate);
		Assert.assertEquals(profilDtoReturned.getId(), "1");
	}
	
	@Test
	public void find() throws SQLException, SqlException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "name", "avatar", 1, 2, 3);
		rs.addRow("2", "name", "avatar", 1, 2, 3);
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rsEmpty, rs);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilDao.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto' est obligatoire."), e);
		}
		
		
		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		try {
			profilDao.find(profilDto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find profilDto.", sql), e);
		}
		
		profilDto = profilDao.find(profilDto);
		Assert.assertNull(profilDto);
		
		profilDto = new ProfilDTO("name", "avatar");
		profilDto = profilDao.find(profilDto);
		Assert.assertNotNull(profilDto);
		
		profilDto.setId("1");
		profilDto = profilDao.find(profilDto);
		Assert.assertNotNull(profilDto);
	}
	
	@Test
	public void findAll() throws SqlException, SQLException {

		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "name", "avatar", 1, 2, 3);
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rs);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilDao.findAll();
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find all profilDto.", sql), e);
		}
		
		ProfilDTO profilDto = profilDao.findAll().get(0);
		Assert.assertEquals( "1", profilDto.getId() );
		Assert.assertEquals( "name", profilDto.getName() );
		Assert.assertEquals( "avatar", profilDto.getAvatar() );
		Assert.assertEquals( 1, profilDto.getPlayedGames() );
		Assert.assertEquals( 2, profilDto.getListenedMusics() );
		Assert.assertEquals( 3, profilDto.getFoundMusics() );
	}
	
	
	
	private SimpleResultSet getResultSet() {
		
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("id", Types.BIGINT, 100, 0);
		rs.addColumn("name", Types.VARCHAR, 255, 0);
		rs.addColumn("avatar", Types.VARCHAR, 255, 0);
		rs.addColumn("playedGames", Types.INTEGER, 100, 0);
		rs.addColumn("listenedMusics", Types.INTEGER, 100, 0);
		rs.addColumn("foundMusics", Types.INTEGER, 100, 0);
		return rs;
	}
	
}
