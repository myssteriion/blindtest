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
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.tools.Tool;

@Component
public class MusicDAO extends AbstractDAO<MusicDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MusicDAO.class);
	
	
	
	@Override
	public MusicDTO save(MusicDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO music(name, theme, played) ");
			sb.append("VALUES ('" + escapeValue( dto.getName() ) + "', '" + dto.getTheme() + "', 0)");
			
			statement.execute( sb.toString() );
			
			MusicDTO dtoSaved = find(dto);
			LOGGER.info("DTO inserted (" + dtoSaved.toString() + ").");
			
			return dtoSaved;
		}
		catch (SQLException e) {
			
			String message = "Can't save dto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public MusicDTO update(MusicDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		Tool.verifyValue("dto -> id", dto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE music ");
			sb.append("SET played = " + dto.getPlayed() + " ");
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
	public MusicDTO find(MusicDTO dto) throws SqlException {
		
		Tool.verifyValue("dto", dto);
		
		try ( Statement statement = em.createStatement() ) {
			
			MusicDTO dtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM music ");
			
			if ( Tool.isNullOrEmpty(dto.getId()) )
				sb.append("WHERE name = '" + escapeValue( dto.getName() ) + "' AND theme = '" + dto.getTheme() + "'");
			else
				sb.append("WHERE id = '" + dto.getId() + "'");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			if ( rs.next() ) {
				
				dtoToReturn = new MusicDTO( rs.getString("name"), Theme.valueOf(rs.getString("theme")), rs.getInt("played") );
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
	public List<MusicDTO> findAll() throws SqlException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<MusicDTO> dtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM music");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {
				
				MusicDTO dto = new MusicDTO( rs.getString("name"), Theme.valueOf(rs.getString("theme")), rs.getInt("played") );
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
