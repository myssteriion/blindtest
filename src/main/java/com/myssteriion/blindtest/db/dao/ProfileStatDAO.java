package com.myssteriion.blindtest.db.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ProfileStatDAO extends AbstractDAO<ProfileStatDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileStatDAO.class);
	
	
	
	public ProfileStatDAO() {
		super("profile_stat");
	}
	
	
	
	@Override
	public ProfileStatDTO save(ProfileStatDTO profileStatDto) throws DaoException {
		
		Tool.verifyValue("profileStatDto", profileStatDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO " + tableName + "(profile_id, played_games, listened_musics, found_musics, best_scores) ");
			sb.append("VALUES (" + profileStatDto.getProfileId() + ", 0, '{}', '{}', '{}')");
			
			statement.execute( sb.toString() );
			
			ProfileStatDTO profileStatDtoSaved = find(profileStatDto);
			LOGGER.info("profileStatDto inserted (" + profileStatDtoSaved.toString() + ").");
			
			return profileStatDtoSaved;
		}
		catch (SQLException e) {
			throw new DaoException("Can't save profileStatDto.", e);
		}
	}
	
	@Override
	public ProfileStatDTO update(ProfileStatDTO profileStatDto) throws DaoException {
		
		Tool.verifyValue("profileStatDto", profileStatDto);
		Tool.verifyValue("profileStatDto -> id", profileStatDto.getId());
		
		try ( Statement statement = em.createStatement() ) {

			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE " + tableName + " ");
			sb.append("SET played_games = " + profileStatDto.getPlayedGames() + ", ");
			sb.append("listened_musics = '" + Tool.MAPPER.writeValueAsString(profileStatDto.getListenedMusics()) + "', ");
			sb.append("found_musics = '" + Tool.MAPPER.writeValueAsString(profileStatDto.getFoundMusics()) + "', ");
			sb.append("best_scores = '" + Tool.MAPPER.writeValueAsString(profileStatDto.getBestScores()) + "' ");
			sb.append("WHERE id = " + profileStatDto.getId());

			statement.execute( sb.toString() );
			LOGGER.info("profileStatDto updated (" + profileStatDto.toString() + ").");
			
			return profileStatDto;
		}
		catch (SQLException e) {
			throw new DaoException("Can't update profileStatDto.", e);
		}
		catch (JsonProcessingException e) {
			throw new DaoException("Can't parse profileStatDto.", e);
		}
	}
	
	@Override
	public ProfileStatDTO find(ProfileStatDTO profileStatDto) throws DaoException {
		
		Tool.verifyValue("profileStatDto", profileStatDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			ProfileStatDTO profileDtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName + " ");
			
			if ( Tool.isNullOrEmpty(profileStatDto.getId()) )
				sb.append("WHERE profile_id = " + profileStatDto.getProfileId() );
			else
				sb.append("WHERE id = " + profileStatDto.getId());

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				if ( rs.next() )
					profileDtoToReturn = transformToDto(rs);
			}

			return profileDtoToReturn;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find profileStatDto.", e);
		}
		catch (IOException e) {
			throw new DaoException("Can't parse profileStatDto.", e);
		}
    }
	
	@Override
	public List<ProfileStatDTO> findAll() throws DaoException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<ProfileStatDTO> profileDtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName);

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				while ( rs.next() )
					profileDtoList.add( transformToDto(rs) );
			}

			return profileDtoList;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find all profileStatDto.", e);
		}
		catch (IOException e) {
			throw new DaoException("Can't parse profileStatDto.", e);
		}
	}


	private ProfileStatDTO transformToDto(ResultSet rs) throws SQLException, IOException {

		ProfileStatDTO profileStatDto;

		HashMap<Theme, Integer> listenedMusics = Tool.MAPPER.readValue(rs.getString("listened_musics"), new TypeReference<HashMap<Theme, Integer>>() {});
		HashMap<Theme, Integer> foundMusics = Tool.MAPPER.readValue(rs.getString("found_musics"), new TypeReference<HashMap<Theme, Integer>>() {});
		HashMap<Duration, Integer> bestScores = Tool.MAPPER.readValue(rs.getString("best_scores"), new TypeReference<HashMap<Duration, Integer>>() {});

		profileStatDto = new ProfileStatDTO(rs.getInt("profile_id"), rs.getInt("played_games"),listenedMusics, foundMusics, bestScores);
		profileStatDto.setId( rs.getInt("id") );

		return profileStatDto;
	}
	
}
