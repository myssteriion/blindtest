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
import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;


public class MusicDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private MusicDAO dao;
	
	
	
	@Test
	public void find() throws SQLException, EntityManagerException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "name", Theme.ANNEES_80, 3);
		
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenReturn(rsEmpty, rs);
		
		EntityManagerException eme = new EntityManagerException("fake");
		Mockito.when(em.createStatement()).thenThrow(eme).thenReturn(statement);
		
		
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
			Assert.fail("Doit lever une EntityManagerException car le mock throw.");
		}
		catch (EntityManagerException e) {
			verifyException(new EntityManagerException("fake"), eme);
		}
		
		MusicDTO emDto = dao.find(dto);
		Assert.assertNull(emDto);
		
		dto = new MusicDTO("name", Theme.ANNEES_80);
		emDto = dao.find(dto);
		Assert.assertNotNull(emDto);
	}
	
	@Test
	public void findAll() throws EntityManagerException, SQLException {

		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "name", Theme.ANNEES_80, 3);
		
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenReturn(rs);
		
		EntityManagerException eme = new EntityManagerException("fake");
		Mockito.when(em.createStatement()).thenThrow(eme).thenReturn(statement);
		
		
		try {
			dao.findAll();
			Assert.fail("Doit lever une EntityManagerException car le mock throw.");
		}
		catch (EntityManagerException e) {
			verifyException(new EntityManagerException("fake"), eme);
		}
		
		MusicDTO dto = dao.findAll().get(0);
		Assert.assertEquals( "1", dto.getId() );
		Assert.assertEquals( "name", dto.getName() );
		Assert.assertEquals( Theme.ANNEES_80, dto.getTheme() );
		Assert.assertEquals( 3, dto.getNbPlayed() );
	}

	@Test
	public void saveOrUpdate() throws EntityManagerException, SQLException {

		dao = Mockito.spy( new MusicDAO() );
		MockitoAnnotations.initMocks(this);
		
		MusicDTO dto = new MusicDTO("name", Theme.ANNEES_80);
		dto.setId("1");
		Mockito.doReturn(null).doReturn(null).doReturn(dto).when(dao).find(Mockito.any(MusicDTO.class));
		
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenReturn(true);
		
		EntityManagerException eme = new EntityManagerException("fake");
		Mockito.when(em.createStatement()).thenThrow(eme).thenReturn(statement).thenThrow(eme).thenReturn(statement);
		
		
		try {
			dao.saveOrUpdate(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		MusicDTO dtoToSave = new MusicDTO("name", Theme.ANNEES_80);
		try {
			dao.saveOrUpdate(dtoToSave);
			Assert.fail("Doit lever une EntityManagerException car le mock throw.");
		}
		catch (EntityManagerException e) {
			verifyException(new EntityManagerException("Can't save dto.", eme), e);
		}
		
		MusicDTO dtoReturned = dao.saveOrUpdate(dtoToSave);
		Assert.assertEquals(dtoReturned.getId(), "1");
		
		
		dtoToSave.setId("1");
		try {
			dao.saveOrUpdate(dtoToSave);
			Assert.fail("Doit lever une EntityManagerException car le mock throw.");
		}
		catch (EntityManagerException e) {
			verifyException(new EntityManagerException("Can't update dto.", eme), e);
		}
		
		dtoReturned = dao.saveOrUpdate(dtoToSave);
		Assert.assertEquals(dtoReturned.getId(), "1");
	}
	
	
	
	private SimpleResultSet getResultSet() {
		
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("id", Types.BIGINT, 100, 0);
		rs.addColumn("name", Types.VARCHAR, 255, 0);
		rs.addColumn("theme", Types.VARCHAR, 255, 0);
		rs.addColumn("nbPlayed", Types.INTEGER, 100, 0);
		return rs;
	}
	
}
