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
import com.myssteriion.blindtest.db.exception.EntityManagerException;

public class EntityMangerTest extends AbstractTest {

	@Mock
	private Connection connection;
	
	@InjectMocks
	private EntityManger em;
	
	
	
	@Test
	public void createStatement() throws SQLException, EntityManagerException {

		Statement statement = Mockito.mock(Statement.class);
		SQLException sqle = new SQLException("fake");
		
		Mockito.when(connection.createStatement()).thenThrow(sqle).thenReturn(statement);
		
		try {
			em.createStatement();
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (EntityManagerException e) {
			verifyException(new EntityManagerException("Can't create statement.", sqle), e);
		}
		
		Assert.assertSame( statement, em.createStatement() );
	}

}
