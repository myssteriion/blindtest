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
	
	
	
	@Override
	public ProfilDTO save(ProfilDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO profil(name, avatar, playedGames, listenedMusics, foundMusics) ");
			sb.append("VALUES ('" + escapeValue( dto.getName() ) + "', '" + dto.getAvatar() + "', 0, 0, 0)");
			
			statement.execute( sb.toString() );
			
			ProfilDTO dtoSaved = find(dto);
			LOGGER.info("DTO inserted (" + dtoSaved.toString() + ").");
			
			return dtoSaved;
		}
		catch (SQLException e) {
			
			String message = "Can't save dto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public ProfilDTO update(ProfilDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		Tool.verifyValue("dto -> id", dto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE profil ");
			sb.append("SET name = '" + escapeValue( dto.getName() ) + "', avatar = '" + dto.getAvatar() + "', playedGames = " + dto.getPlayedGames() + ", ");
			sb.append("listenedMusics = " + dto.getListenedMusics() + ", foundMusics = " + dto.getFoundMusics() + " ");
			sb.append("WHERE id = " + dto.getId());
			
			statement.execute( sb.toString() );
			LOGGER.info("DTO updated (" + dto.toString() + ").");
			
			return dto;
		}
		catch (SQLException e) {
			
			String message = "Can't update dto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public ProfilDTO find(ProfilDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		try ( Statement statement = em.createStatement() ) {
			
			ProfilDTO dtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM profil ");
			sb.append("WHERE name = '" + escapeValue( dto.getName() ) + "'");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {
				
				dtoToReturn = new ProfilDTO( rs.getString("name"), rs.getString("avatar"), rs.getInt("playedGames"), rs.getInt("listenedMusics"), rs.getInt("foundMusics") );
				dtoToReturn.setId( rs.getString("id") );
			}

			return dtoToReturn;
		}
		catch (SQLException e) {
			
			String message = "Can't find dto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public List<ProfilDTO> findAll() throws SqlException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<ProfilDTO> dtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM profil");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {

				ProfilDTO dto = new ProfilDTO( rs.getString("name"), rs.getString("avatar"), rs.getInt("playedGames"), rs.getInt("listenedMusics"), rs.getInt("foundMusics") );
				dto.setId( rs.getString("id") );
				dtoList.add(dto);
			}
			
			return dtoList;
		}
		catch (SQLException e) {
			
			String message = "Can't find all dto.";
			throw new SqlException(message, e);
		}
	}
	
}
