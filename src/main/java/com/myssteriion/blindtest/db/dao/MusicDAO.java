package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class MusicDAO extends AbstractDAO<MusicDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MusicDAO.class);
	
	
	
	public MusicDAO() {
		super("music");
	}
	
	
	
	@Override
	public MusicDTO save(MusicDTO musicDto) throws DaoException {
		
		Tool.verifyValue("musicDto", musicDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO " + tableName + "(name, theme, played) ");
			sb.append("VALUES ('" + escapeValue( musicDto.getName() ) + "', '" + musicDto.getTheme() + "', 0)");
			
			statement.execute( sb.toString() );
			
			MusicDTO musicDtotoSaved = find(musicDto);
			LOGGER.info("musicDto inserted (" + musicDtotoSaved.toString() + ").");
			
			return musicDtotoSaved;
		}
		catch (SQLException e) {
			throw new DaoException("Can't save musicDto.", e);
		}
	}
	
	@Override
	public MusicDTO update(MusicDTO musicDto) throws DaoException {
		
		Tool.verifyValue("musicDto", musicDto);
		Tool.verifyValue("musicDto -> id", musicDto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE " + tableName + " ");
			sb.append("SET played = " + musicDto.getPlayed() + " ");
			sb.append("WHERE id = " + musicDto.getId());
			
			statement.execute( sb.toString() );
			LOGGER.info("musicDto updated (" + musicDto.toString() + ").");
			
			return musicDto;
		}
		catch (SQLException e) {
			throw new DaoException("Can't update musicDto.", e);
		}
	}
	
	@Override
	public MusicDTO find(MusicDTO musicDto) throws DaoException {
		
		Tool.verifyValue("musicDto", musicDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			MusicDTO musicDtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName + " ");
			
			if ( Tool.isNullOrEmpty(musicDto.getId()) )
				sb.append("WHERE name = '" + escapeValue( musicDto.getName() ) + "' AND theme = '" + musicDto.getTheme() + "'");
			else
				sb.append("WHERE id = " + musicDto.getId());
			
			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				if ( rs.next() )
					musicDtoToReturn = transformToDto(rs);
			}

			return musicDtoToReturn;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find musicDto.", e);
		}
	}
	
	@Override
	public List<MusicDTO> findAll() throws DaoException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<MusicDTO> musicDtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName);

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				while ( rs.next() )
					musicDtoList.add( transformToDto(rs) );
			}
			
			return musicDtoList;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find all musicDto.", e);
		}
	}


	private MusicDTO transformToDto(ResultSet rs) throws SQLException {

		MusicDTO musicDtoToReturn;

		musicDtoToReturn = new MusicDTO(rs.getString("name"), Theme.valueOf(rs.getString("theme")), rs.getInt("played"));
		musicDtoToReturn.setId( rs.getInt("id") );

		return musicDtoToReturn;
	}

}
