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
import com.myssteriion.blindtest.db.exception.SqlException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;


public class MusicDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private MusicDAO dao;
	
	
	
	@Test
	public void save() throws SqlException, SQLException {

		dao = Mockito.spy( new MusicDAO() );
		MockitoAnnotations.initMocks(this);

		MusicDTO dto = new MusicDTO("name", Theme.ANNEES_80);
		dto.setId("1");
		Mockito.doReturn(dto).when(dao).find(Mockito.any(MusicDTO.class));
		
		
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
		
		
		MusicDTO dtoToSave = new MusicDTO("name", Theme.ANNEES_80);
		try {
			dao.save(dtoToSave);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't save dto.", sql), e);
		}
		
		MusicDTO dtoReturned = dao.save(dtoToSave);
		Assert.assertEquals(dtoReturned.getId(), "1");
	}
	
	@Test
	public void update() throws SqlException, SQLException {

		MusicDTO dto = new MusicDTO("name", Theme.ANNEES_80);
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
		
		
		MusicDTO dtoToUpdate = new MusicDTO("name", Theme.ANNEES_80);
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
		
		MusicDTO dtoReturned = dao.update(dtoToUpdate);
		Assert.assertEquals(dtoReturned.getId(), "1");
	}
	
	@Test
	public void find() throws SQLException, SqlException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "name", "ANNEES_80", 1);
		
		
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
		
		
		MusicDTO dto = new MusicDTO("name", Theme.ANNEES_80);
		try {
			dao.find(dto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find dto.", sql), e);
		}
		
		MusicDTO emDto = dao.find(dto);
		Assert.assertNull(emDto);
		
		dto = new MusicDTO("name", Theme.ANNEES_80);
		emDto = dao.find(dto);
		Assert.assertNotNull(emDto);
	}
	
	@Test
	public void findAll() throws SqlException, SQLException {

		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "name", "ANNEES_80", 1);
		
		
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
		
		MusicDTO dto = dao.findAll().get(0);
		Assert.assertEquals( "1", dto.getId() );
		Assert.assertEquals( "name", dto.getName() );
		Assert.assertEquals( Theme.ANNEES_80, dto.getTheme() );
	}
	
	
	
	private SimpleResultSet getResultSet() {
		
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("id", Types.BIGINT, 100, 0);
		rs.addColumn("name", Types.VARCHAR, 255, 0);
		rs.addColumn("theme", Types.VARCHAR, 255, 0);
		rs.addColumn("played", Types.INTEGER, 100, 0);
		return rs;
	}
	
}
