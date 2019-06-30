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
	public MusicDTO save(MusicDTO musicDto) throws SqlException {
		
		Tool.verifyValue("musicDto", musicDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO music(name, theme, played) ");
			sb.append("VALUES ('" + escapeValue( musicDto.getName() ) + "', '" + musicDto.getTheme() + "', 0)");
			
			statement.execute( sb.toString() );
			
			MusicDTO dtoSaved = find(musicDto);
			LOGGER.info("musicDto inserted (" + dtoSaved.toString() + ").");
			
			return dtoSaved;
		}
		catch (SQLException e) {
			
			String message = "Can't save musicDto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public MusicDTO update(MusicDTO musicDto) throws SqlException {
		
		Tool.verifyValue("musicDto", musicDto);
		Tool.verifyValue("musicDto -> id", musicDto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE music ");
			sb.append("SET played = " + musicDto.getPlayed() + " ");
			sb.append("WHERE id = " + musicDto.getId());
			
			statement.execute( sb.toString() );
			LOGGER.info("musicDto updated (" + musicDto.toString() + ").");
			
			return musicDto;
		}
		catch (SQLException e) {
			
			String message = "Can't update musicDto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public MusicDTO find(MusicDTO musicDto) throws SqlException {
		
		Tool.verifyValue("musicDto", musicDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			MusicDTO musicDtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM music ");
			
			if ( Tool.isNullOrEmpty(musicDto.getId()) )
				sb.append("WHERE name = '" + escapeValue( musicDto.getName() ) + "' AND theme = '" + musicDto.getTheme() + "'");
			else
				sb.append("WHERE id = '" + musicDto.getId() + "'");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			if ( rs.next() ) {
				
				musicDtoToReturn = new MusicDTO( rs.getString("name"), Theme.valueOf(rs.getString("theme")), rs.getInt("played") );
				musicDtoToReturn.setId( rs.getString("id") );
			}

			return musicDtoToReturn;
		}
		catch (SQLException e) {
			
			String message = "Can't find musicDto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public List<MusicDTO> findAll() throws SqlException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<MusicDTO> musicDtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM music");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {
				
				MusicDTO musicDto = new MusicDTO( rs.getString("name"), Theme.valueOf(rs.getString("theme")), rs.getInt("played") );
				musicDto.setId( rs.getString("id") );
				musicDtoList.add(musicDto);
			}
			
			return musicDtoList;
		}
		catch (SQLException e) {
			
			String message = "Can't find all musicDto.";
			throw new SqlException(message, e);
		}
	}
	
}
