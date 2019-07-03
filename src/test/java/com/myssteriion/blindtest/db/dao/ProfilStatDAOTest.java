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
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;


public class ProfilStatDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private ProfilStatDAO profilStatDao;
	
	
	
	@Test
	public void save() throws SqlException, SQLException {

		profilStatDao = Mockito.spy( new ProfilStatDAO() );
		MockitoAnnotations.initMocks(this);

		ProfilStatDTO profilStatDto = new ProfilStatDTO("1");
		profilStatDto.setId("1");
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
		
		
		ProfilStatDTO profilStatDtoToSave = new ProfilStatDTO("1");
		try {
			profilStatDao.save(profilStatDtoToSave);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't save profilStatDto.", sql), e);
		}
		
		ProfilStatDTO profilStatDtoReturned = profilStatDao.save(profilStatDtoToSave);
		Assert.assertEquals(profilStatDtoReturned.getId(), "1");
	}
	
	@Test
	public void update() throws SqlException, SQLException {

		ProfilStatDTO profilStatDto = new ProfilStatDTO("1");
		profilStatDto.setId("1");
		

		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilStatDao.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto' est obligatoire."), e);
		}
		
		
		ProfilStatDTO profilStatDtoToUpdate = new ProfilStatDTO("1");
		try {
			profilStatDao.update(profilStatDtoToUpdate);
			Assert.fail("Doit lever une IllegalArgumentException car il manque l'id.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto -> id' est obligatoire."), e);
		}
		
		profilStatDtoToUpdate.setId("1");
		try {
			profilStatDao.update(profilStatDtoToUpdate);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't update profilStatDto.", sql), e);
		}
		
		ProfilStatDTO profilStatDtoReturned = profilStatDao.update(profilStatDtoToUpdate);
		Assert.assertEquals(profilStatDtoReturned.getId(), "1");
	}
	
	@Test
	public void find() throws SQLException, SqlException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "1", 1, 2, 3);
		rs.addRow("2", "1", 1, 2, 3);
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rsEmpty, rs);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilStatDao.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto' est obligatoire."), e);
		}
		
		
		ProfilStatDTO profilStatDto = new ProfilStatDTO("1");
		try {
			profilStatDao.find(profilStatDto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find profilStatDto.", sql), e);
		}
		
		profilStatDto = profilStatDao.find(profilStatDto);
		Assert.assertNull(profilStatDto);
		
		profilStatDto = new ProfilStatDTO("1");
		profilStatDto = profilStatDao.find(profilStatDto);
		Assert.assertNotNull(profilStatDto);
		
		profilStatDto.setId("1");
		profilStatDto = profilStatDao.find(profilStatDto);
		Assert.assertNotNull(profilStatDto);
	}
	
	@Test
	public void findAll() throws SqlException, SQLException {

		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "1", 1, 2, 3);
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rs);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilStatDao.findAll();
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find all profilStatDto.", sql), e);
		}
		
		ProfilStatDTO profilStatDto = profilStatDao.findAll().get(0);
		Assert.assertEquals( "1", profilStatDto.getId() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );
		Assert.assertEquals( 2, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 3, profilStatDto.getFoundMusics() );
	}
	
	
	
	private SimpleResultSet getResultSet() {
		
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("id", Types.BIGINT, 100, 0);
		rs.addColumn("profil_id", Types.BIGINT, 100, 0);
		rs.addColumn("played_games", Types.INTEGER, 100, 0);
		rs.addColumn("listened_musics", Types.INTEGER, 100, 0);
		rs.addColumn("found_musics", Types.INTEGER, 100, 0);
		return rs;
	}
	
}
