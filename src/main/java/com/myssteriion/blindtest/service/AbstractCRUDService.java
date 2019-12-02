package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Abstract class for CRUD service.
 *
 * @param <DTO> the type parameter
 * @param <DAO> the type parameter
 */
public abstract class AbstractCRUDService< DTO extends AbstractDTO, DAO extends PagingAndSortingRepository<DTO, Integer> > {

    /**
     * The Dao.
     */
    protected DAO dao;

    protected ConfigProperties configProperties;



    /**
     * Instantiates a new Abstract service.
     *
     * @param dao the dao
     */
    public AbstractCRUDService(DAO dao, ConfigProperties configProperties) {
        this.dao = dao;
        this.configProperties = configProperties;
    }



    public DTO save(DTO dto) throws ConflictException {

        Tool.verifyValue("entity", dto);
        dto.setId(null);

        if (find(dto) != null)
            throw new ConflictException("Entity already exists.");

        return dao.save(dto);
    }

    /**
     * Update dto.
     *
     * @param dto the dto
     * @return the dto
     * @throws NotFoundException the not found exception
     * @throws ConflictException the conflict exception
     */
    public DTO update(DTO dto) throws NotFoundException, ConflictException {

        Tool.verifyValue("entity", dto);
        Tool.verifyValue("entity -> id", dto.getId());

        if ( !dao.findById(dto.getId()).isPresent() )
            throw new NotFoundException("Entity not found.");

        try {
            return dao.save(dto);
        }
        catch (DataIntegrityViolationException e) {
            throw new ConflictException("Entity already exists.");
        }
    }

    /**
     * Find dto.
     *
     * @param dto the dto
     * @return the dto
     */
    public DTO find(DTO dto) {

        Tool.verifyValue("entity", dto);
        Tool.verifyValue("entity -> id", dto.getId());

        return dao.findById( dto.getId() ).orElse(null);
    }

    /**
     * Delete dto.
     *
     * @param dto the dto
     * @throws NotFoundException the not found exception
     */
    public void delete(DTO dto) throws NotFoundException {

        Tool.verifyValue("entity", dto);
        Tool.verifyValue("entity -> id", dto.getId());

        if ( !dao.findById(dto.getId()).isPresent() )
            throw new NotFoundException("Entity not found.");

        dao.deleteById(dto.getId());
    }

}
