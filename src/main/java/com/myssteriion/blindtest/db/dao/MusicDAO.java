package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MusicDAO extends AbstractDAO<MusicDTO> {

	private PreparedStatement findById;

	private PreparedStatement findByNameAndTheme;



	@Override
	protected void initQueryIfNeedIt() throws DaoException {

		if ( Tool.isNullOrEmpty(create) ) {

			create = em.createPreparedStatement("INSERT INTO music (name, theme, played) VALUES (?, ?, 0)");
			update = em.createPreparedStatement("UPDATE music SET played = ? WHERE id = ?");
			findAll = em.createPreparedStatement("SELECT * FROM music");

			findById = em.createPreparedStatement("SELECT * FROM music WHERE id = ?");
			findByNameAndTheme = em.createPreparedStatement("SELECT * FROM music WHERE name = ? and theme = ?");
		}
	}



	@Override
	protected void fillSave(MusicDTO dto) throws DaoException {

		try {

			create.clearParameters();
			create.setString( 1, dto.getName() );
			create.setString( 2, dto.getTheme().toString() );
		}
		catch (SQLException e) {

			String message = "Can't fill save query.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

	@Override
	protected void fillUpdate(MusicDTO dto) throws DaoException {

		try {

			update.clearParameters();
			update.setInt( 1, dto.getPlayed() );
			update.setInt( 2, dto.getId() );
		}
		catch (SQLException e) {

			String message = "Can't fill update query.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

	@Override
	protected void fillFind(MusicDTO dto) throws DaoException {

		try {

			if ( Tool.isNullOrEmpty(dto.getId()) ) {

				findByNameAndTheme.clearParameters();
				findByNameAndTheme.setString( 1, dto.getName() );
				findByNameAndTheme.setString( 2, dto.getTheme().toString() );
				find = findByNameAndTheme;
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
	protected MusicDTO transformToDto(ResultSet rs) throws DaoException {

		try {

			MusicDTO dtoToReturn;

			dtoToReturn = new MusicDTO(rs.getString("name"), Theme.valueOf(rs.getString("theme")), rs.getInt("played"));
			dtoToReturn.setId( rs.getInt("id") );

			return dtoToReturn;
		}
		catch (SQLException e) {

			String message = "Can't transform dto.";
			LOGGER.error(message, e);
			throw new DaoException(message, e);
		}
	}

}
