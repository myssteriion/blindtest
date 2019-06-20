package com.myssteriion.blindtest.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.AbstractDTO;

public abstract class AbstractDAO<T extends AbstractDTO> {

	@Autowired
	protected EntityManager em;
	
	
	
	public abstract T save(T dto) throws EntityManagerException;
	
	public abstract T update(T dto) throws EntityManagerException;
	
	public abstract T find(T dto) throws EntityManagerException;
	
	public abstract List<T> findAll() throws EntityManagerException;
	
}
