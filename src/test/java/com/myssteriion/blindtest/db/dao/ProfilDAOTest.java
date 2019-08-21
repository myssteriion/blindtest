package com.myssteriion.blindtest.db.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.myssteriion.blindtest.db.exception.DaoException;
import org.h2.tools.SimpleResultSet;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.EntityManager;
import com.myssteriion.blindtest.model.dto.ProfilDTO;


public class ProfilDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private ProfilDAO profilDao;
	
	
	
	@Test
	public void save() throws DaoException, SQLException {

		profilDao = Mockito.spy( new ProfilDAO() );
		MockitoAnnotations.initMocks(this);

		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		profilDto.setId(1);
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
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't save profilDto.", sql), e);
		}
		
		ProfilDTO profilDtoReturned = profilDao.save(profilDtoToSave);
		Assert.assertEquals( new Integer(1), profilDtoReturned.getId() );
	}
	
	@Test
	public void update() throws DaoException, SQLException {

		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		profilDto.setId(1);
		

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
		
		profilDtoToUpdate.setId(1);
		try {
			profilDao.update(profilDtoToUpdate);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't update profilDto.", sql), e);
		}
		
		ProfilDTO profilDtoReturned = profilDao.update(profilDtoToUpdate);
		Assert.assertEquals( new Integer(1), profilDtoReturned.getId() );
	}
	
	@Test
	public void find() throws SQLException, DaoException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow(1, "name", "avatar");
		rs.addRow(2, "name", "avatar");

		SimpleResultSet rs2 = getResultSet();
		rs2.addRow(1, "name", "avatar");
		rs2.addRow(2, "name", "avatar");
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rsEmpty, rs, rs2);
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
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't find profilDto.", sql), e);
		}
		
		profilDto = profilDao.find(profilDto);
		Assert.assertNull(profilDto);
		
		profilDto = new ProfilDTO("name", "avatar");
		profilDto = profilDao.find(profilDto);
		Assert.assertNotNull(profilDto);
		
		profilDto.setId(1);
		profilDto = profilDao.find(profilDto);
		Assert.assertNotNull(profilDto);
		Assert.assertEquals( new Integer(1), profilDto.getId() );
		Assert.assertEquals( "name", profilDto.getName() );
		Assert.assertEquals( "avatar", profilDto.getAvatar() );
	}
	
	@Test
	public void findAll() throws DaoException, SQLException {

		SimpleResultSet rs = getResultSet();
		rs.addRow(1, "name", "avatar");
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rs);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			profilDao.findAll();
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't find all profilDto.", sql), e);
		}
		
		ProfilDTO profilDto = profilDao.findAll().get(0);
		Assert.assertEquals( new Integer(1), profilDto.getId() );
		Assert.assertEquals( "name", profilDto.getName() );
		Assert.assertEquals( "avatar", profilDto.getAvatar() );
	}
	
	
	
	private SimpleResultSet getResultSet() {
		
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("id", Types.BIGINT, 100, 0);
		rs.addColumn("name", Types.VARCHAR, 255, 0);
		rs.addColumn("avatar", Types.VARCHAR, 255, 0);
		return rs;
	}
	
}
