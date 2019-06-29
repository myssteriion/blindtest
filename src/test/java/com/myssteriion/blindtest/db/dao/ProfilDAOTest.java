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
	private ProfilDAO dao;
	
	
	
	@Test
	public void save() throws SqlException, SQLException {

		dao = Mockito.spy( new ProfilDAO() );
		MockitoAnnotations.initMocks(this);

		ProfilDTO dto = new ProfilDTO("name", "avatar");
		dto.setId("1");
		Mockito.doReturn(dto).when(dao).find(Mockito.any(ProfilDTO.class));
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			dao.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfilDTO dtoToSave = new ProfilDTO("name", "avatar");
		try {
			dao.save(dtoToSave);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't save dto.", sql), e);
		}
		
		ProfilDTO dtoReturned = dao.save(dtoToSave);
		Assert.assertEquals(dtoReturned.getId(), "1");
	}
	
	@Test
	public void update() throws SqlException, SQLException {

		ProfilDTO dto = new ProfilDTO("name", "avatar");
		dto.setId("1");
		

		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			dao.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfilDTO dtoToUpdate = new ProfilDTO("name", "avatar");
		try {
			dao.update(dtoToUpdate);
			Assert.fail("Doit lever une IllegalArgumentException car il manque l'id.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto -> id' est obligatoire."), e);
		}
		
		dtoToUpdate.setId("1");
		try {
			dao.update(dtoToUpdate);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't update dto.", sql), e);
		}
		
		ProfilDTO dtoReturned = dao.update(dtoToUpdate);
		Assert.assertEquals(dtoReturned.getId(), "1");
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
			dao.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfilDTO dto = new ProfilDTO("name", "avatar");
		try {
			dao.find(dto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find dto.", sql), e);
		}
		
		ProfilDTO emDto = dao.find(dto);
		Assert.assertNull(emDto);
		
		dto = new ProfilDTO("name", "avatar");
		emDto = dao.find(dto);
		Assert.assertNotNull(emDto);
		
		dto.setId("1");
		emDto = dao.find(dto);
		Assert.assertNotNull(emDto);
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
			dao.findAll();
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find all dto.", sql), e);
		}
		
		ProfilDTO dto = dao.findAll().get(0);
		Assert.assertEquals( "1", dto.getId() );
		Assert.assertEquals( "name", dto.getName() );
		Assert.assertEquals( "avatar", dto.getAvatar() );
		Assert.assertEquals( 1, dto.getPlayedGames() );
		Assert.assertEquals( 2, dto.getListenedMusics() );
		Assert.assertEquals( 3, dto.getFoundMusics() );
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
