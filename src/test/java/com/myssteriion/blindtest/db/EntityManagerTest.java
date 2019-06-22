package com.myssteriion.blindtest.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.exception.SqlException;

public class EntityManagerTest extends AbstractTest {

	@Mock
	private Connection connection;
	
	@InjectMocks
	private EntityManager em;
	
	
	
	@Test
	public void createStatement() throws SQLException, SqlException {

		Statement statement = Mockito.mock(Statement.class);
		SQLException sqle = new SQLException("fake");
		
		Mockito.when(connection.createStatement()).thenThrow(sqle).thenReturn(statement);
		
		try {
			em.createStatement();
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("Can't create statement.", sqle), e);
		}
		
		Assert.assertSame( statement, em.createStatement() );
	}

}
