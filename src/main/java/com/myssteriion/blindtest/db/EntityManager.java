package com.myssteriion.blindtest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.myssteriion.blindtest.db.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.myssteriion.blindtest.tools.Constant;

@Component
public class EntityManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManager.class);
	
	private static final String URL = "jdbc:h2:file:" + Constant.BASE_DIR + "/data/blindtest";
	
	private Connection connection;
	
	
	
	@PostConstruct
	private void init() throws DaoException {
		
		try {
			
			connection = DriverManager.getConnection(URL);
			initTableIfNeedIt();
		}
		catch (SQLException e) {
			
			String message = "Can't init DataBase.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

	@PreDestroy
	private void destroy() {

		try {
			if ( connection != null && !connection.isClosed() )
				connection.close();
		}
		catch (SQLException e) {
			String message = "Can't close connection.";
			LOGGER.warn(message, e);
		}
	}


	private void initTableIfNeedIt() throws DaoException {
		
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
			
			
			// profile
			sb = new StringBuilder();
			sb.append("CREATE SEQUENCE IF NOT EXISTS profile_seq ");
			sb.append("START WITH 0 ");
			sb.append("INCREMENT BY 1 ");
			statement.execute( sb.toString() );
			
			sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS profile (");
			sb.append("id BIGINT DEFAULT profile_seq.nextval PRIMARY KEY,");
			sb.append("name VARCHAR2 NOT NULL UNIQUE,");
			sb.append("avatar_name VARCHAR2 NOT NULL");
			sb.append(")");
			statement.execute( sb.toString() );
			
			
			// profile stat
			sb = new StringBuilder();
			sb.append("CREATE SEQUENCE IF NOT EXISTS profile_stat_seq ");
			sb.append("START WITH 0 ");
			sb.append("INCREMENT BY 1 ");
			statement.execute( sb.toString() );
			
			sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS profile_stat (");
			sb.append("id BIGINT DEFAULT profile_stat_seq.nextval PRIMARY KEY,");
			sb.append("profile_id BIGINT NOT NULL UNIQUE,");
			sb.append("played_games INT NOT NULL,");
			sb.append("listened_musics VARCHAR2 NOT NULL,");
			sb.append("found_musics VARCHAR2 NOT NULL,");
			sb.append("best_scores VARCHAR2 NOT NULL,");
			sb.append("FOREIGN KEY (profile_id) REFERENCES profile(id)");
			sb.append(")");
			statement.execute( sb.toString() );
		}
		catch (SQLException e) {
			
			String message = "Can't create tables.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}
	
	
	
	public Statement createStatement() throws DaoException {
		
		try {
			return connection.createStatement();
		}
		catch (SQLException e) {
			
			String message = "Can't create statement.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}
	
}
