package com.myssteriion.blindtest.db;

import java.util.List;

import com.myssteriion.blindtest.db.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;

/**
 * Abstract class for all DAO
 * @param <T>
 */
public abstract class AbstractDAO<T extends AbstractDTO> {

	@Autowired
	protected EntityManager em;
	
	protected String tableName;
	
	
	
	protected AbstractDAO(String tableName) {
		this.tableName = tableName;
	}

	
	
	public abstract T save(T dto) throws DaoException;
	
	public abstract T update(T dto) throws DaoException;
	
	public abstract T find(T dto) throws DaoException;
	
	public abstract List<T> findAll() throws DaoException;
	
	
	protected String escapeValue(String value) {
		
		String escapedValue = "";
		
		if ( !Tool.isNullOrEmpty(value) )
			escapedValue = value.replace(Constant.QUOTE, Constant.DOUBLE_QUOTE);
		
		return escapedValue;
	}
	
}
