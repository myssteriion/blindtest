package com.myssteriion.blindtest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.myssteriion.blindtest.tools.Constant;

@Component
public class DbConnection {
	
	private static final String URL = "jdbc:h2:file:" + Constant.BASE_DIRE + "/data/blindtest";
	
	private Connection connection;
	
	
	
	public Connection getConnection() {
		return connection;
	}

	
	
	
	@PostConstruct
	private void init() throws SQLException {
		
		connection = DriverManager.getConnection(URL);
		initTableIfNeedIt();
	}

	private void initTableIfNeedIt() throws SQLException {
		
		try ( Statement statement = connection.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS musique (");
			sb.append("id INT,");
			sb.append("name VARCHAR2,");
			sb.append("theme VARCHAR2,");
			sb.append("nbPlayed INT");
			sb.append(")");
			
			statement.execute( sb.toString() );
		}
	}
	
}
