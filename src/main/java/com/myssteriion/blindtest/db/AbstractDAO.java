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
 * Abstract class for all DAO.
 *
 * @param <T> the AbstractDTO
 */
public abstract class AbstractDAO<T extends AbstractDTO> {

	/**
	 * The constant LOGGER.
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAO.class);

	/**
	 * The EntityManager.
	 */
	@Autowired
	protected EntityManager em;

	/**
	 * "Insert" PreparedStatement.
	 */
	protected PreparedStatement create;

	/**
	 * "Update" PreparedStatement.
	 */
	protected PreparedStatement update;

	/**
	 * "Select one" PreparedStatement.
	 */
	protected PreparedStatement find;

	/**
	 * "Select all" PreparedStatement.
	 */
	protected PreparedStatement findAll;

	/**
	 * "Delete" PreparedStatement.
	 */
	protected PreparedStatement delete;



	/**
	 * Initialize all PreparedStatement (if need it).
	 *
	 * @throws DaoException DB exception
	 */
	protected abstract void initQueryIfNeedIt() throws DaoException;


	/**
	 * Insert dto in DB.
	 *
	 * @param dto the dto
	 * @return the dto saved
	 * @throws DaoException DB exception
	 * @see #create
	 * @see #fillSave(AbstractDTO)
	 */
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

	/**
	 * Update dto in DB.
	 *
	 * @param dto the dto
	 * @return the dto edited
	 * @throws DaoException DB exception
	 * @see #update
	 * @see #fillUpdate(AbstractDTO)
	 */
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

	/**
	 * Select dto in DB.
	 *
	 * @param dto the dto
	 * @return the dto or NULL if dto doesn't exist
	 * @throws DaoException DB exception
	 * @see #find
	 * @see #fillFind(AbstractDTO)
	 */
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

	/**
	 * Select all dto in DB.
	 *
	 * @return a dto list
	 * @throws DaoException DB exception
	 * @see #findAll
	 */
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

	/**
	 * Delete dto in DB.
	 *
	 * @throws DaoException DB exception
	 * @see #delete
	 */
	public void delete(T dto) throws DaoException {

		Tool.verifyValue("dto", dto);
		Tool.verifyValue("dto -> id", dto.getId());

		try {

			initQueryIfNeedIt();

			delete.clearParameters();
			delete.setInt( 1, dto.getId() );
			delete.executeUpdate();

			LOGGER.info("dto deleted (" + dto.toString() + ").");
		}
		catch (SQLException e) {
			throw new DaoException("Can't delete dto.", e);
		}
	}



	/**
	 * Fill "create" PreparedStatement with dto.
	 *
	 * @param dto the dto
	 * @throws DaoException DB exception
	 * @see #save(AbstractDTO)
	 */
	protected abstract void fillSave(T dto) throws DaoException;

	/**
	 * Fill "update" PreparedStatement with dto.
	 *
	 * @param dto the dto
	 * @throws DaoException DB exception
	 * @see #update(AbstractDTO)
	 */
	protected abstract void fillUpdate(T dto) throws DaoException;

	/**
	 * Fill "find" PreparedStatement with dto.
	 *
	 * @param dto the dto
	 * @throws DaoException DB exception
	 * @see #find(AbstractDTO)
	 */
	protected abstract void fillFind(T dto) throws DaoException;


	/**
	 * Transform ResultSet to dto.
	 *
	 * @param rs the ResultSet
	 * @return a dto
	 * @throws DaoException DB exception
	 */
	protected abstract T transformToDto(ResultSet rs) throws DaoException;

}
