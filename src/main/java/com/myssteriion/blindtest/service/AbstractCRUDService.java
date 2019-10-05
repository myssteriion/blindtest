package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;

/**
 * Abstract class for CRUD service.
 *
 * @param <DTO> the type parameter
 * @param <DAO> the type parameter
 */
public abstract class AbstractCRUDService< DTO extends AbstractDTO, DAO extends AbstractDAO<DTO> > {

    /**
     * The Dao.
     */
    protected DAO dao;



    /**
     * Instantiates a new Abstract service.
     *
     * @param dao the dao
     */
    public AbstractCRUDService(DAO dao) {
        this.dao = dao;
    }



    /**
     * Save dto.
     *
     * @param dto the dto
     * @return the dto
     * @throws DaoException      the dao exception
     * @throws ConflictException the conflict exception
     */
    public DTO save(DTO dto) throws DaoException, ConflictException {

        Tool.verifyValue("dto", dto);

        dto.setId(null);
        if ( !Tool.isNullOrEmpty( dao.find(dto) ) )
            throw new ConflictException("Dto already exists.");

        return dao.save(dto);
    }

    /**
     * Update dto.
     *
     * @param dto the dto
     * @return the dto
     * @throws DaoException      the dao exception
     * @throws NotFoundException the not found exception
     */
    public DTO update(DTO dto) throws DaoException, NotFoundException {

        Tool.verifyValue("dto", dto);
        Tool.verifyValue("dto -> id", dto.getId());

        if ( Tool.isNullOrEmpty( dao.find(dto) ) )
            throw new NotFoundException("Dto not found.");

        return dao.update(dto);
    }

    /**
     * Find dto.
     *
     * @param dto the dto
     * @return the dto
     * @throws DaoException the dao exception
     */
    public DTO find(DTO dto) throws DaoException {

        Tool.verifyValue("dto", dto);
        return dao.find(dto);
    }

    /**
     * Find all dto.
     *
     * @return the dto list
     * @throws DaoException the dao exception
     */
    public List<DTO> findAll() throws DaoException {
        return dao.findAll();
    }

    /**
     * Delete dto.
     *
     * @param dto the dto
     * @return TRUE if the dto was deleted, FALSE otherwise
     * @throws DaoException      the dao exception
     * @throws NotFoundException the not found exception
     */
    public boolean delete(DTO dto) throws DaoException, NotFoundException {

        Tool.verifyValue("dto", dto);
        Tool.verifyValue("dto -> id", dto.getId());

        if ( Tool.isNullOrEmpty( dao.find(dto) ) )
            throw new NotFoundException("Dto not found.");

        return dao.delete(dto);
    }
    
}
