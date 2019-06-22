package com.myssteriion.blindtest.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.AbstractDTO;

public abstract class AbstractDAO<T extends AbstractDTO> {

	@Autowired
	protected EntityManager em;
	
	
	
	public abstract T save(T dto) throws SqlException;
	
	public abstract T update(T dto) throws SqlException;
	
	public abstract T find(T dto) throws SqlException;
	
	public abstract List<T> findAll() throws SqlException;
	
}
