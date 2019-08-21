package com.myssteriion.blindtest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.tools.Constant;

@Component
public class EntityManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManager.class);
	
	private static final String URL = "jdbc:h2:file:" + Constant.BASE_DIR + "/data/blindtest";
	
	private Connection connection;
	
	
	
	@PostConstruct
	private void init() throws SqlException {
		
		try {
			
			connection = DriverManager.getConnection(URL);
			initTableIfNeedIt();
		}
		catch (SQLException e) {
			
			String message = "Can't init DataBase.";
			LOGGER.error(message, e);
			throw new SqlException(message, e);
		}
	}

	private void initTableIfNeedIt() throws SqlException {
		
		StringBuilder sb;
		
		try ( Statement statement = connection.createStatement() ) {
			
			// music
			sb = new StringBuilder();
			sb.append("CREATE SEQUENCE IF NOT EXISTS music_seq ");
			sb.append("START WITH 0 ");
			sb.append("INCREMENT BY 1 ");
			statement.execute( sb.toString() );
			
			sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS music (");
			sb.append("id BIGINT DEFAULT music_seq.nextval PRIMARY KEY,");
			sb.append("name VARCHAR2 NOT NULL,");
			sb.append("theme VARCHAR2 NOT NULL,");
			sb.append("played INT NOT NULL");
			sb.append(")");
			statement.execute( sb.toString() );
			
			sb = new StringBuilder();
			sb.append("ALTER TABLE music ADD CONSTRAINT IF NOT EXISTS name_theme_unique UNIQUE(name, theme)");
			statement.execute( sb.toString() );
			
			
			// profil
			sb = new StringBuilder();
			sb.append("CREATE SEQUENCE IF NOT EXISTS profil_seq ");
			sb.append("START WITH 0 ");
			sb.append("INCREMENT BY 1 ");
			statement.execute( sb.toString() );
			
			sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS profil (");
			sb.append("id BIGINT DEFAULT profil_seq.nextval PRIMARY KEY,");
			sb.append("name VARCHAR2 NOT NULL UNIQUE,");
			sb.append("avatar VARCHAR2 NOT NULL");
			sb.append(")");
			statement.execute( sb.toString() );
			
			
			// profil stat
			sb = new StringBuilder();
			sb.append("CREATE SEQUENCE IF NOT EXISTS profil_stat_seq ");
			sb.append("START WITH 0 ");
			sb.append("INCREMENT BY 1 ");
			statement.execute( sb.toString() );
			
			sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS profil_stat (");
			sb.append("id BIGINT DEFAULT profil_stat_seq.nextval PRIMARY KEY,");
			sb.append("profil_id BIGINT NOT NULL UNIQUE,");
			sb.append("played_games INT NOT NULL,");
			sb.append("listened_musics INT NOT NULL,");
			sb.append("found_musics INT NOT NULL,");
			sb.append("best_scores VARCHAR2 NOT NULL,");
			sb.append("FOREIGN KEY (profil_id) REFERENCES profil(id)");
			sb.append(")");
			statement.execute( sb.toString() );
		}
		catch (SQLException e) {
			
			String message = "Can't create tables.";
			LOGGER.error(message, e);
			throw new SqlException(message, e);
		}
	}
	
	
	
	public Statement createStatement() throws SqlException {
		
		try {
			return connection.createStatement();
		}
		catch (SQLException e) {
			
			String message = "Can't create statement.";
			LOGGER.error(message, e);
			throw new SqlException(message, e);
		}
	}
	
}
