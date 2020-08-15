package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.model.Empty;
import com.myssteriion.utils.rest.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for ProfileDTO.
 */
@RestController
@RequestMapping(path = "profiles")
public class ProfileController {
    
    private ProfileService profileService;
    
    
    
    /**
     * Instantiates a new Profile controller.
     *
     * @param profileService the profile service
     */
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    
    
    /**
     * Save a new ProfileDTO.
     *
     * @param profileDto the ProfileDTO
     * @return the ProfileDTO saved
     * @throws ConflictException Conflict exception
     */
    @PostMapping
    public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDto) throws ConflictException {
        return RestUtils.create201( profileService.save(profileDto) );
    }
    
    /**
     * Update an existing ProfileDTO.
     *
     * @param id         the ProfileDTO id
     * @param profileDto the 'new' ProfileDTO
     * @return the ProfileDTO modified
     * @throws NotFoundException the not found exception
     * @throws ConflictException the conflict exception
     */
    @PutMapping(path = CommonConstant.ID_PATH_PARAM)
    public ResponseEntity<ProfileDTO> update(@PathVariable(CommonConstant.ID) Integer id, @RequestBody ProfileDTO profileDto) throws NotFoundException, ConflictException {
        
        profileDto.setId(id);
        return RestUtils.create200( profileService.update(profileDto) );
    }
    
    /**
     * Find pageable of profile filtered by search name.
     *
     * @param searchName  the search name
     * @param pageNumber  the page number
     * @param itemPerPage the item per page
     * @return the pageable of profiles filtered by search name
     */
    @GetMapping
    public ResponseEntity< Page<ProfileDTO> > findAllBySearchName(
            @RequestParam(value = Constant.SEARCH_NAME, required = false, defaultValue = Constant.SEARCH_NAME_DEFAULT_VALUE) String searchName,
            @RequestParam(value = CommonConstant.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = CommonConstant.ITEM_PER_PAGE) Integer itemPerPage) {
        
        return RestUtils.create200( profileService.findAllBySearchName(searchName, pageNumber, itemPerPage) );
    }
    
    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws NotFoundException the not found exception
     */
    @DeleteMapping(path = CommonConstant.ID_PATH_PARAM)
    public ResponseEntity<Empty> delete(@PathVariable(CommonConstant.ID) Integer id) throws NotFoundException {
        
        ProfileDTO profileDto = (ProfileDTO) new ProfileDTO("ANY").setId(id);
        
        profileService.delete(profileDto);
        
        return RestUtils.create204();
    }
    
}
