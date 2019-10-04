package com.myssteriion.blindtest.db;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EntityManagerTest extends AbstractTest {

	@Mock
	private Connection connection;
	
	@InjectMocks
	private EntityManager em;
	
	
	
	@Test
	public void createStatement() throws SQLException, DaoException {

		Statement statement = Mockito.mock(Statement.class);
		SQLException sqle = new SQLException("fake");
		
		Mockito.when(connection.createStatement()).thenThrow(sqle).thenReturn(statement);
		
		try {
			em.createStatement();
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't create statement.", sqle), e);
		}
		
		Assert.assertSame( statement, em.createStatement() );
	}

	@Test
	public void createPreparedStatement() throws SQLException, DaoException {

		PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		SQLException sqle = new SQLException("fake");

		Mockito.when(connection.prepareStatement(Mockito.anyString())).thenThrow(sqle).thenReturn(statement);

		String query = "query";

		try {
			em.createPreparedStatement(null);
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'query' est obligatoire."), e);
		}

		try {
			em.createPreparedStatement("");
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'query' est obligatoire."), e);
		}

		try {
			em.createPreparedStatement(query);
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (DaoException e) {
			verifyException(new DaoException("Can't create prepareStatement.", sqle), e);
		}

		Assert.assertSame( statement, em.createPreparedStatement(query) );
	}

}
