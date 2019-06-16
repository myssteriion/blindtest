package com.myssteriion.blindtest.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.AbstractDTO;

public abstract class AbstractDAO<T extends AbstractDTO> {

	@Autowired
	protected EntityManger em;
	
	
	
	public abstract List<T> findAll() throws EntityManagerException;
	
	public abstract T saveOrUpdate(T dto) throws EntityManagerException;
	
	public abstract T exists(T dto) throws EntityManagerException;
	
}
