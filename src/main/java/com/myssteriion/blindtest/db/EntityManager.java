package com.myssteriion.blindtest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.tools.Constant;

@Component
public class EntityManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManager.class);
	
	private static final String URL = "jdbc:h2:file:" + Constant.BASE_DIR + "/data/blindtest";
	
	private Connection connection;
	
	
	
	@PostConstruct
	private void init() throws EntityManagerException {
		
		try {
			
			connection = DriverManager.getConnection(URL);
			initTableIfNeedIt();
		}
		catch (Exception e) {
			
			String message = "Can't init DataBase";
			LOGGER.error(message, e);
			throw new EntityManagerException(message, e);
		}
	}

	private void initTableIfNeedIt() throws EntityManagerException {
		
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
			sb.append("name VARCHAR2 NOT NULL UNIQUE,");
			sb.append("theme VARCHAR2 NOT NULL,");
			sb.append("nbPlayed INT NOT NULL");
			sb.append(")");
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
			sb.append("avatar VARCHAR2 NOT NULL,");
			sb.append("playedGames INT NOT NULL,");
			sb.append("listenedMusics INT NOT NULL,");
			sb.append("foundMusics INT NOT NULL");
			sb.append(")");
			statement.execute( sb.toString() );
		}
		catch (Exception e) {
			
			String message = "Can't create tables.";
			LOGGER.error(message, e);
			throw new EntityManagerException(message, e);
		}
	}
	
	
	
	public Statement createStatement() throws EntityManagerException {
		
		try {
			return connection.createStatement();
		}
		catch (Exception e) {
			
			String message = "Can't create statement.";
			LOGGER.error(message, e);
			throw new EntityManagerException(message, e);
		}
	}
	
}
