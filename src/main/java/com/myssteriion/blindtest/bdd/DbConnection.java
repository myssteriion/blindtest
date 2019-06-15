package com.myssteriion.blindtest.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.myssteriion.blindtest.tools.Constant;

@Component
public class DbConnection {
	
	private static final String URL = "jdbc:h2:file:" + Constant.BASE_DIRE + "/data/blindtest";
	
	private Connection connection;
	

	
	@PostConstruct
	private void init() throws SQLException {
		connection = DriverManager.getConnection(URL);
	}
	
}
