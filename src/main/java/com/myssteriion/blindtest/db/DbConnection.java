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
		
		StringBuilder sb;
		
		try ( Statement statement = connection.createStatement() ) {
			
			sb = new StringBuilder();
			sb.append("CREATE SEQUENCE IF NOT EXISTS music_seq ");
			sb.append("START WITH 0 ");
			sb.append("INCREMENT BY 1 ");
			statement.execute( sb.toString() );
			
			sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS musique (");
			sb.append("id BIGINT DEFAULT music_seq.nextval PRIMARY KEY,");
			sb.append("name VARCHAR2 NOT NULL UNIQUE,");
			sb.append("theme VARCHAR2 NOT NULL,");
			sb.append("nbPlayed INT NOT NULL");
			sb.append(")");
			statement.execute( sb.toString() );
		}
	}
	
}
