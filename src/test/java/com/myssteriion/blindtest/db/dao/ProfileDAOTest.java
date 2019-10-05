package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.EntityManager;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import org.h2.tools.SimpleResultSet;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


public class ProfileDAOTest extends AbstractTest {

	@Mock
	protected EntityManager em;
	
	@InjectMocks
	private ProfileDAO profileDao;
	
	
	
	@Test
	public void save() throws DaoException, SQLException {

		profileDao = Mockito.spy( new ProfileDAO() );
		MockitoAnnotations.initMocks(this);

		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		profileDto.setId(1);
		Mockito.doReturn(profileDto).when(profileDao).find(Mockito.any(ProfileDTO.class));
		
		
		SQLException sql = new SQLException("sql");
		PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		Mockito.when(statement.executeUpdate()).thenThrow(sql).thenReturn(1);
		Mockito.when(em.createPreparedStatement(Mockito.anyString())).thenReturn(statement);
		
		
		try {
			profileDao.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfileDTO profileDtoToSave = new ProfileDTO("name", new Avatar("avatar"));
		try {
			profileDao.save(profileDtoToSave);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't save dto.", sql), e);
		}
		
		ProfileDTO profileDtoReturned = profileDao.save(profileDtoToSave);
		Assert.assertEquals( new Integer(1), profileDtoReturned.getId() );
	}
	
	@Test
	public void update() throws DaoException, SQLException {

		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		profileDto.setId(1);
		

		SQLException sql = new SQLException("sql");
		PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		Mockito.when(statement.executeUpdate()).thenThrow(sql).thenReturn(1);
		Mockito.when(em.createPreparedStatement(Mockito.anyString())).thenReturn(statement);
		
		
		try {
			profileDao.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfileDTO profileDtoToUpdate = new ProfileDTO("name", new Avatar("avatar"));
		try {
			profileDao.update(profileDtoToUpdate);
			Assert.fail("Doit lever une IllegalArgumentException car il manque l'id.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto -> id' est obligatoire."), e);
		}
		
		profileDtoToUpdate.setId(1);
		try {
			profileDao.update(profileDtoToUpdate);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't update dto.", sql), e);
		}
		
		ProfileDTO profileDtoReturned = profileDao.update(profileDtoToUpdate);
		Assert.assertEquals( new Integer(1), profileDtoReturned.getId() );
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
		PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		Mockito.when(statement.executeQuery()).thenThrow(sql).thenReturn(rsEmpty, rs, rs2);
		Mockito.when(em.createPreparedStatement(Mockito.anyString())).thenReturn(statement);
		
		
		try {
			profileDao.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		try {
			profileDao.find(profileDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't find dto.", sql), e);
		}
		
		profileDto = profileDao.find(profileDto);
		Assert.assertNull(profileDto);
		
		profileDto = new ProfileDTO("name", new Avatar("avatar"));
		profileDto = profileDao.find(profileDto);
		Assert.assertNotNull(profileDto);
		
		profileDto.setId(1);
		profileDto = profileDao.find(profileDto);
		Assert.assertNotNull(profileDto);
		Assert.assertEquals( new Integer(1), profileDto.getId() );
		Assert.assertEquals( "name", profileDto.getName() );
		Assert.assertEquals( "avatar", profileDto.getAvatar().getName() );
	}
	
	@Test
	public void findAll() throws DaoException, SQLException {

		SimpleResultSet rs = getResultSet();
		rs.addRow(1, "name", "avatar");
		
		
		SQLException sql = new SQLException("sql");
		PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		Mockito.when(statement.executeQuery()).thenThrow(sql).thenReturn(rs);
		Mockito.when(em.createPreparedStatement(Mockito.anyString())).thenReturn(statement);
		
		
		try {
			profileDao.findAll();
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't findAll dto.", sql), e);
		}
		
		ProfileDTO profileDto = profileDao.findAll().get(0);
		Assert.assertEquals( new Integer(1), profileDto.getId() );
		Assert.assertEquals( "name", profileDto.getName() );
		Assert.assertEquals( "avatar", profileDto.getAvatar().getName() );
	}

	@Test
	public void delete() throws DaoException, SQLException {

		profileDao = Mockito.spy( new ProfileDAO() );
		MockitoAnnotations.initMocks(this);

		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		profileDto.setId(1);
		Mockito.doReturn(profileDto).when(profileDao).find(Mockito.any(ProfileDTO.class));


		SQLException sql = new SQLException("sql");
		PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		Mockito.when(statement.executeUpdate()).thenThrow(sql).thenReturn(1);
		Mockito.when(em.createPreparedStatement(Mockito.anyString())).thenReturn(statement);


		try {
			profileDao.delete(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}


		ProfileDTO profileDtoToDelete = new ProfileDTO("name", new Avatar("avatar"));
		try {
			profileDao.delete(profileDtoToDelete);
			Assert.fail("Doit lever une IllegalArgumentException car il manque l'id.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto -> id' est obligatoire."), e);
		}

		profileDtoToDelete.setId(1);
		try {
			profileDao.delete(profileDtoToDelete);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't delete dto.", sql), e);
		}

		profileDao.delete(profileDtoToDelete);
	}
	
	
	private SimpleResultSet getResultSet() {
		
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("id", Types.BIGINT, 100, 0);
		rs.addColumn("name", Types.VARCHAR, 255, 0);
		rs.addColumn("avatar_name", Types.VARCHAR, 255, 0);
		return rs;
	}
	
}
