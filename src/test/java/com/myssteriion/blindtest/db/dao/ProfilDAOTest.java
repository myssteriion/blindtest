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
import com.myssteriion.blindtest.model.dto.ProfilDTO;


public class ProfilDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private ProfilDAO dao;
	
	
	
	@Test
	public void save() throws EntityManagerException, SQLException {

		dao = Mockito.spy( new ProfilDAO() );
		MockitoAnnotations.initMocks(this);

		ProfilDTO dto = new ProfilDTO("name", "avatar");
		dto.setId("1");
		Mockito.doReturn(dto).when(dao).find(Mockito.any(ProfilDTO.class));
		
		
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenReturn(true);
		
		EntityManagerException eme = new EntityManagerException("fake");
		Mockito.when(em.createStatement()).thenThrow(eme).thenReturn(statement);
		
		
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
			Assert.fail("Doit lever une EntityManagerException car le mock throw.");
		}
		catch (EntityManagerException e) {
			verifyException(new EntityManagerException("Can't save dto.", eme), e);
		}
		
		ProfilDTO dtoReturned = dao.save(dtoToSave);
		Assert.assertEquals(dtoReturned.getId(), "1");
	}
	
	@Test
	public void update() throws EntityManagerException, SQLException {

		ProfilDTO dto = new ProfilDTO("name", "avatar");
		dto.setId("1");
		
		
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(statement.execute(Mockito.anyString())).thenReturn(true);
		
		EntityManagerException eme = new EntityManagerException("fake");
		Mockito.when(em.createStatement()).thenThrow(eme).thenReturn(statement);
		
		
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
			Assert.fail("Doit lever une EntityManagerException car le mock throw.");
		}
		catch (EntityManagerException e) {
			verifyException(new EntityManagerException("Can't update dto.", eme), e);
		}
		
		ProfilDTO dtoReturned = dao.update(dtoToUpdate);
		Assert.assertEquals(dtoReturned.getId(), "1");
	}
	
	@Test
	public void find() throws SQLException, EntityManagerException {

		SimpleResultSet rsEmpty = getResultSet();
		
		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "name", "avatar", 1, 2, 3);
		
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
		
		
		ProfilDTO dto = new ProfilDTO("name", "avatar");
		try {
			dao.find(dto);
			Assert.fail("Doit lever une EntityManagerException car le mock throw.");
		}
		catch (EntityManagerException e) {
			verifyException(new EntityManagerException("fake"), eme);
		}
		
		ProfilDTO emDto = dao.find(dto);
		Assert.assertNull(emDto);
		
		dto = new ProfilDTO("name", "avatar");
		emDto = dao.find(dto);
		Assert.assertNotNull(emDto);
	}
	
	@Test
	public void findAll() throws EntityManagerException, SQLException {

		SimpleResultSet rs = getResultSet();
		rs.addRow("1", "name", "avatar", 1, 2, 3);
		
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
