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
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;


public class MusicDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private MusicDAO musicDao;
	
	
	
	@Test
	public void save() throws SqlException, SQLException {

		musicDao = Mockito.spy( new MusicDAO() );
		MockitoAnnotations.initMocks(this);

		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
		musicDto.setId(1);
		Mockito.doReturn(musicDto).when(musicDao).find(Mockito.any(MusicDTO.class));
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			musicDao.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
		}
		
		
		MusicDTO musidcDtoToSave = new MusicDTO("name", Theme.ANNEES_80);
		try {
			musicDao.save(musidcDtoToSave);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't save musicDto.", sql), e);
		}
		
		MusicDTO musicDtoReturned = musicDao.save(musidcDtoToSave);
		Assert.assertEquals( new Integer(1), musicDtoReturned.getId() );
	}
	
	@Test
	public void update() throws SqlException, SQLException {

		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
		musicDto.setId(1);
		

		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenThrow(sql).thenReturn(true);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			musicDao.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
		}
		
		
		MusicDTO musicDtoToUpdate = new MusicDTO("name", Theme.ANNEES_80);
		try {
			musicDao.update(musicDtoToUpdate);
			Assert.fail("Doit lever une IllegalArgumentException car il manque l'id.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicDto -> id' est obligatoire."), e);
		}
		
		musicDtoToUpdate.setId(1);
		try {
			musicDao.update(musicDtoToUpdate);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't update musicDto.", sql), e);
		}
		
		MusicDTO musicDtoReturned = musicDao.update(musicDtoToUpdate);
		Assert.assertEquals( new Integer(1), musicDtoReturned.getId() );
	}
	
	@Test
	public void find() throws SQLException, SqlException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow(1, "name", "ANNEES_80", 1);
		rs.addRow(2, "name", "ANNEES_80", 1);
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rsEmpty, rs);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			musicDao.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
		}
		
		
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
		try {
			musicDao.find(musicDto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find musicDto.", sql), e);
		}
		
		musicDto = musicDao.find(musicDto);
		Assert.assertNull(musicDto);
		
		musicDto = new MusicDTO("name", Theme.ANNEES_80);
		musicDto = musicDao.find(musicDto);
		Assert.assertNotNull(musicDto);
		
		musicDto.setId(1);
		musicDto = musicDao.find(musicDto);
		Assert.assertNotNull(musicDto);
	}
	
	@Test
	public void findAll() throws SqlException, SQLException {

		SimpleResultSet rs = getResultSet();
		rs.addRow(1, "name", "ANNEES_80", 1);
		
		
		SQLException sql = new SQLException("sql");
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(sql).thenReturn(rs);
		Mockito.when(em.createStatement()).thenReturn(statement);
		
		
		try {
			musicDao.findAll();
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't find all musicDto.", sql), e);
		}
		
		MusicDTO musicDto = musicDao.findAll().get(0);
		Assert.assertEquals( new Integer(1), musicDto.getId() );
		Assert.assertEquals( "name", musicDto.getName() );
		Assert.assertEquals( Theme.ANNEES_80, musicDto.getTheme() );
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
