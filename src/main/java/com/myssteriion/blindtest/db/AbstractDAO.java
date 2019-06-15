package com.myssteriion.blindtest.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.myssteriion.blindtest.db.exception.DbException;
import com.myssteriion.blindtest.model.AbstractDTO;

public abstract class AbstractDAO<T extends AbstractDTO> {

	@Autowired
	protected DbConnection dbConnection;
	
	
	
	public abstract List<T> findAll() throws DbException;
	
	public abstract boolean saveOrUpdate(T dto) throws DbException;
	
}
