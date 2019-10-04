package com.myssteriion.blindtest.db;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for all DAO (only for AbstractDTO class).
 *
 * @param <T>
 */
public abstract class AbstractDAO<T extends AbstractDTO> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAO.class);

	@Autowired
	protected EntityManager em;
	
	protected PreparedStatement create;

	protected PreparedStatement update;

	protected PreparedStatement find;

	protected PreparedStatement findAll;



	protected abstract void initQueryIfNeedIt() throws DaoException;



	public T save(T dto) throws DaoException {

		Tool.verifyValue("dto", dto);

		try {

			initQueryIfNeedIt();
			fillSave(dto);
			create.executeUpdate();

			T dtoSaved = find(dto);
			LOGGER.info("dto inserted (" + dtoSaved.toString() + ").");

			return dtoSaved;
		}
		catch (SQLException e) {
			throw new DaoException("Can't save dto.", e);
		}
	}

	public T update(T dto) throws DaoException {

		Tool.verifyValue("dto", dto);
		Tool.verifyValue("dto -> id", dto.getId());

		try {

			initQueryIfNeedIt();
			fillUpdate(dto);
			update.executeUpdate();

			LOGGER.info("dto updated (" + dto.toString() + ").");

			return dto;
		}
		catch (SQLException e) {
			throw new DaoException("Can't update dto.", e);
		}
	}

	public T find(T dto) throws DaoException {

		Tool.verifyValue("dto", dto);

		try {

			initQueryIfNeedIt();
			fillFind(dto);

			T dtoToReturn = null;
			try ( ResultSet rs = find.executeQuery() ) {
				if ( rs.next() )
					dtoToReturn = transformToDto(rs);
			}

			return dtoToReturn;
 		}
		catch (SQLException e) {
			throw new DaoException("Can't find dto.", e);
		}
	}

	public List<T> findAll() throws DaoException {

		try {

			initQueryIfNeedIt();

			List<T> dtoList = new ArrayList<>();
			try ( ResultSet rs = findAll.executeQuery() ) {
				while ( rs.next() )
					dtoList.add( transformToDto(rs) );
			}

			return dtoList;
		}
		catch (SQLException e) {
			throw new DaoException("Can't findAll dto.", e);
		}
	}


	protected abstract void fillSave(T dto) throws DaoException;

	protected abstract void fillUpdate(T dto) throws DaoException;

	protected abstract void fillFind(T dto) throws DaoException;


	protected abstract T transformToDto(ResultSet rs) throws DaoException;

}
