package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO for ProfileDTO.
 */
@Component
public class ProfileDAO extends AbstractDAO<ProfileDTO> {

	/**
	 * "Select one by id" PreparedStatement.
	 */
	private PreparedStatement findById;

	/**
	 * "Select one by name" PreparedStatement.
	 */
	private PreparedStatement findByName;



	@Override
	protected void initQueryIfNeedIt() throws DaoException {

		if ( Tool.isNullOrEmpty(create) ) {

			create = em.createPreparedStatement("INSERT INTO profile (name, avatar_name) VALUES (?, ?)");
			update = em.createPreparedStatement("UPDATE profile SET name = ?, avatar_name = ? WHERE id = ?");
			findAll = em.createPreparedStatement("SELECT * FROM profile ORDER BY name ASC");
			delete = em.createPreparedStatement("DELETE FROM profile WHERE id = ?");

			findById = em.createPreparedStatement("SELECT * FROM profile WHERE id = ?");
			findByName = em.createPreparedStatement("SELECT * FROM profile WHERE name = ?");
		}
	}



	@Override
	protected void fillSave(ProfileDTO dto) throws DaoException {

		try {

			create.clearParameters();
			create.setString( 1, dto.getName() );
			create.setString( 2, dto.getAvatar().getName() );
		}
		catch (SQLException e) {

			String message = "Can't fill save query.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

	@Override
	protected void fillUpdate(ProfileDTO dto) throws DaoException {

		try {

			update.clearParameters();
			update.setString( 1, dto.getName() );
			update.setString( 2, dto.getAvatar().getName() );
			update.setInt( 3, dto.getId() );
		}
		catch (SQLException e) {

			String message = "Can't fill update query.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

	@Override
	protected void fillFind(ProfileDTO dto) throws DaoException {

		try {

			if ( Tool.isNullOrEmpty(dto.getId()) ) {

				findByName.clearParameters();
				findByName.setString( 1, dto.getName() );
				find = findByName;
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
	protected ProfileDTO transformToDto(ResultSet rs) throws DaoException {

		try {

			ProfileDTO dtoToReturn;

			Avatar avatar = new Avatar(rs.getString("avatar_name"));
			dtoToReturn = new ProfileDTO(rs.getString("name"), avatar);
			dtoToReturn.setId(rs.getInt("id"));

			return dtoToReturn;
		}
		catch (SQLException e) {

			String message = "Can't transform dto.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

}
