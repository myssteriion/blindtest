package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.persistence.dao.ProfileDAO;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.rest.exception.ConflictException;
import com.myssteriion.utils.rest.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for ProfileDTO.
 */
@Service
public class ProfileService extends AbstractCRUDService<ProfileDTO, ProfileDAO> {
    
    private ProfileStatService profileStatService;
    
    private AvatarService avatarService;
    
    
    
    @Autowired
    public ProfileService(ProfileDAO profileDao, ProfileStatService profileStatService, AvatarService avatarService) {
        super(profileDao);
        this.profileStatService = profileStatService;
        this.avatarService = avatarService;
    }
    
    
    
    @Override
    public ProfileDTO save(ProfileDTO dto) throws ConflictException {
        
        ProfileDTO profile = super.save(dto);
        createProfileAvatarFlux(profile);
        profileStatService.save( new ProfileStatDTO(profile.getId()) );
        return profile;
    }
    
    @Override
    public ProfileDTO update(ProfileDTO dto) throws NotFoundException, ConflictException {
        
        ProfileDTO profile = super.update(dto);
        createProfileAvatarFlux(profile);
        return profile;
    }
    
    @Override
    public ProfileDTO find(ProfileDTO dto) {
        
        CommonUtils.verifyValue("entity", dto);
        
        ProfileDTO profile;
        if ( CommonUtils.isNullOrEmpty(dto.getId()) )
            profile = dao.findByName(dto.getName()).orElse(null);
        else
            profile = super.find(dto);
        
        if (profile != null)
            createProfileAvatarFlux(profile);
        
        return profile;
    }
    
    /**
     * Find a pageNumber of Profile filtered by a search name.
     *
     * @param searchName  the search name
     * @param pageNumber  the page number
     * @param itemPerPage the item per page
     * @return the page of profiles filtered by search name
     */
    public Page<ProfileDTO> findAllBySearchName(String searchName, int pageNumber, int itemPerPage) {
        
        if (searchName == null)
            searchName = "";
        
        itemPerPage = Math.max(itemPerPage, 1);
        itemPerPage = Math.min(itemPerPage, Constant.ITEM_PER_PAGE_MAX);
        
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
        Pageable pageable = PageRequest.of( pageNumber, itemPerPage, Sort.by(order) );
        
        Page<ProfileDTO> page = dao.findAllByNameContainingIgnoreCase(searchName, pageable);
        page.forEach(this::createProfileAvatarFlux);
        
        return page;
    }
    
    @Override
    public void delete(ProfileDTO profile) throws NotFoundException {
        
        CommonUtils.verifyValue("profile", profile);
        CommonUtils.verifyValue("profile -> id", profile.getId());
        
        profileStatService.delete( profileStatService.findByProfile(profile) );
        super.delete(profile);
    }
    
    
    /**
     * Create profile avatar flux.
     *
     * @param profile the profile
     */
    private void createProfileAvatarFlux(ProfileDTO profile) {
        
        // setter is useful for create avatar inside profile
        profile.setAvatarName( profile.getAvatarName() );
        avatarService.createAvatarFlux( profile.getAvatar() );
    }
    
}
