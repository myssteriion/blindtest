package com.myssteriion.blindtest.db.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * DAO for ProfileStatDTO.
 */
@Component
public class ProfileStatDAO extends AbstractDAO<ProfileStatDTO> {

	/**
	 * "Select one by id" PreparedStatement.
	 */
	private PreparedStatement findById;

	/**
	 * "Select one by ProfileId" PreparedStatement.
	 */
	private PreparedStatement findByProfileId;



	@Override
	protected void initQueryIfNeedIt() throws DaoException {

		if ( Tool.isNullOrEmpty(create) ) {

			create = em.createPreparedStatement("INSERT INTO profile_stat (profile_id, played_games, listened_musics, " +
					"found_musics, best_scores) VALUES (?, 0, '{}', '{}', '{}')");

			update = em.createPreparedStatement("UPDATE profile_stat SET played_games = ?, listened_musics = ?, " +
					"found_musics = ?, best_scores = ? WHERE id = ?");

			findAll = em.createPreparedStatement("SELECT * FROM profile_stat");

			findById = em.createPreparedStatement("SELECT * FROM profile_stat WHERE id = ?");
			findByProfileId = em.createPreparedStatement("SELECT * FROM profile_stat WHERE profile_id = ?");
		}
	}



	@Override
	protected void fillSave(ProfileStatDTO dto) throws DaoException {

		try {

			create.clearParameters();
			create.setInt( 1, dto.getProfileId() );
		}
		catch (SQLException e) {

			String message = "Can't fill save query.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

	@Override
	protected void fillUpdate(ProfileStatDTO dto) throws DaoException {

		try {

			update.clearParameters();
			update.setInt( 1, dto.getPlayedGames() );
			update.setString( 2, Tool.MAPPER.writeValueAsString(dto.getListenedMusics()) );
			update.setString( 3, Tool.MAPPER.writeValueAsString(dto.getFoundMusics()) );
			update.setString( 4, Tool.MAPPER.writeValueAsString(dto.getBestScores()) );
			update.setInt( 5, dto.getId() );
		}
		catch (SQLException | JsonProcessingException e) {

			String message = "Can't fill update query.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

	@Override
	protected void fillFind(ProfileStatDTO dto) throws DaoException {

		try {

			if ( Tool.isNullOrEmpty(dto.getId()) ) {

				findByProfileId.clearParameters();
				findByProfileId.setInt( 1, dto.getProfileId() );
				find = findByProfileId;
			}
			else {

				findById.clearParameters();
				findById.setInt( 1, dto.getId() );
				find = findById;
			}
		}
		catch (SQLException e) {

			String message = "Can't fill find query.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}


	@Override
	protected ProfileStatDTO transformToDto(ResultSet rs) throws DaoException {

		try {

			ProfileStatDTO dtoToReturn;

			HashMap<Theme, Integer> listenedMusics = Tool.MAPPER.readValue(rs.getString("listened_musics"), new TypeReference<HashMap<Theme, Integer>>() {});
			HashMap<Theme, Integer> foundMusics = Tool.MAPPER.readValue(rs.getString("found_musics"), new TypeReference<HashMap<Theme, Integer>>() {});
			HashMap<Duration, Integer> bestScores = Tool.MAPPER.readValue(rs.getString("best_scores"), new TypeReference<HashMap<Duration, Integer>>() {});

			dtoToReturn = new ProfileStatDTO(rs.getInt("profile_id"), rs.getInt("played_games"),listenedMusics, foundMusics, bestScores);
			dtoToReturn.setId( rs.getInt("id") );

			return dtoToReturn;
		}
		catch (SQLException | IOException e) {

			String message = "Can't transform dto.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}
	
}
