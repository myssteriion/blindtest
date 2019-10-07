package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

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



    /**
     * Instantiates a new Abstract service.
     *
     * @param dao the dao
     */
    public AbstractCRUDService(DAO dao) {
        this.dao = dao;
    }



    public DTO save(DTO dto) throws ConflictException {

        Tool.verifyValue("dto", dto);
        dto.setId(null);

        if (find(dto) != null)
            throw new ConflictException("Dto already exists.");

        return dao.save(dto);
    }

    /**
     * Update dto.
     *
     * @param dto the dto
     * @return the dto
     * @throws NotFoundException the not found exception
     */
    public DTO update(DTO dto) throws NotFoundException {

        Tool.verifyValue("dto", dto);
        Tool.verifyValue("dto -> id", dto.getId());

        if ( !dao.findById(dto.getId()).isPresent() )
            throw new NotFoundException("Dto not found.");

        return dao.save(dto);
    }

    /**
     * Find dto.
     *
     * @param dto the dto
     * @return the dto
     */
    public DTO find(DTO dto) {

        Tool.verifyValue("dto", dto);
        Tool.verifyValue("dto -> id", dto.getId());

        return dao.findById( dto.getId() ).orElse(null);
    }

    /**
     * Find all dto.
     *
     * @return the dto list
     */
    public Page<DTO> findAll() {

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(0, 1, sort);

        return dao.findAll(pageRequest);
    }

    /**
     * Delete dto.
     *
     * @param dto the dto
     * @throws NotFoundException the not found exception
     */
    public void delete(DTO dto) throws NotFoundException {

        Tool.verifyValue("dto", dto);
        Tool.verifyValue("dto -> id", dto.getId());

        if ( !dao.findById(dto.getId()).isPresent() )
            throw new NotFoundException("Dto not found.");

        dao.deleteById(dto.getId());
    }
    
}
