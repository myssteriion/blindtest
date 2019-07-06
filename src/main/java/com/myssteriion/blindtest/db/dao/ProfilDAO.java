package com.myssteriion.blindtest.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

@Component
public class ProfilDAO extends AbstractDAO<ProfilDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilDAO.class);
	
	
	
	public ProfilDAO() {
		super("profil");
	}
	
	
	
	@Override
	public ProfilDTO save(ProfilDTO profilDto) throws SqlException {
		
		Tool.verifyValue("profilDto", profilDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO " + tableName + "(name, avatar) ");
			sb.append("VALUES ('" + escapeValue( profilDto.getName() ) + "', '" + profilDto.getAvatar() + "')");
			
			statement.execute( sb.toString() );
			
			ProfilDTO profilDtoSaved = find(profilDto);
			LOGGER.info("profilDto inserted (" + profilDtoSaved.toString() + ").");
			
			return profilDtoSaved;
		}
		catch (SQLException e) {
			throw new SqlException("Can't save profilDto.", e);
		}
	}
	
	@Override
	public ProfilDTO update(ProfilDTO profilDto) throws SqlException {
		
		Tool.verifyValue("profilDto", profilDto);
		Tool.verifyValue("profilDto -> id", profilDto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE " + tableName + " ");
			sb.append("SET name = '" + escapeValue( profilDto.getName() ) + "', avatar = '" + profilDto.getAvatar() + "'" );
			sb.append("WHERE id = " + profilDto.getId());
			
			statement.execute( sb.toString() );
			LOGGER.info("profilDto updated (" + profilDto.toString() + ").");
			
			return profilDto;
		}
		catch (SQLException e) {
			throw new SqlException("Can't update profilDto.", e);
		}
	}
	
	@Override
	public ProfilDTO find(ProfilDTO profilDto) throws SqlException {
		
		Tool.verifyValue("profilDto", profilDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			ProfilDTO profilDtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName + " ");
			
			if ( Tool.isNullOrEmpty(profilDto.getId()) )
				sb.append("WHERE name = '" + escapeValue( profilDto.getName() ) + "'");
			else
				sb.append("WHERE id = " + profilDto.getId());
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			if ( rs.next() ) {
				
				profilDtoToReturn = new ProfilDTO( rs.getString("name"), rs.getString("avatar") );
				profilDtoToReturn.setId( rs.getInt("id") );
			}

			return profilDtoToReturn;
		}
		catch (SQLException e) {
			throw new SqlException("Can't find profilDto.", e);
		}
	}
	
	@Override
	public List<ProfilDTO> findAll() throws SqlException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<ProfilDTO> profilDtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName);
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {

				ProfilDTO profilDto = new ProfilDTO( rs.getString("name"), rs.getString("avatar") );
				profilDto.setId( rs.getInt("id") );
				profilDtoList.add(profilDto);
			}
			
			return profilDtoList;
		}
		catch (SQLException e) {
			throw new SqlException("Can't find all profilDto.", e);
		}
	}
	
}
