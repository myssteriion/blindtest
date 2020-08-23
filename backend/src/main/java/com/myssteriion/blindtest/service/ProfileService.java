package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.persistence.dao.ProfileDAO;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service for profile.
 */
@Service
public class ProfileService extends AbstractCRUDService<ProfileEntity, ProfileDAO> {
    
    private AvatarService avatarService;
    
    @Autowired
    public ProfileService(ProfileDAO profileDao, AvatarService avatarService) {
        super(profileDao, "profile");
        this.avatarService = avatarService;
    }
    
    
    
    @Override
    public ProfileEntity save(ProfileEntity entity) throws ConflictException {
        
        ProfileEntity profile = super.save(entity);
        createAvatarFlux(profile);
        return profile;
    }
    
    @Override
    public ProfileEntity update(ProfileEntity entity) throws NotFoundException, ConflictException {
        
        ProfileEntity profile = super.update(entity);
        createAvatarFlux(profile);
        return profile;
    }
    
    @Override
    public ProfileEntity find(ProfileEntity entity) {
        
        super.checkEntity(entity);
        
        ProfileEntity profile;
        if ( CommonUtils.isNullOrEmpty(entity.getId()) ) {
            checkEntity(entity);
            profile = dao.findByName(entity.getName()).orElse(null);
        }
        else
            profile = super.find(entity);
        
        if (profile != null)
            createAvatarFlux(profile);
        
        return profile;
    }
    
    /**
     * Find a pageNumber of Profile filtered by a name.
     *
     * @param name        the name
     * @param pageNumber  the page number
     * @param itemPerPage the item per page
     * @return the page of profiles filtered by search name
     */
    public Page<ProfileEntity> findAllByName(String name, int pageNumber, int itemPerPage) {
        
        name = Objects.requireNonNullElse(name, "");
        
        itemPerPage = Math.max(itemPerPage, 1);
        itemPerPage = Math.min(itemPerPage, Constant.ITEM_PER_PAGE_MAX);
        
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
        Pageable pageable = PageRequest.of( pageNumber, itemPerPage, Sort.by(order) );
        
        Page<ProfileEntity> page = dao.findAllByNameContainingIgnoreCase(name, pageable);
        page.forEach(this::createAvatarFlux);
        
        return page;
    }
    
    
    /**
     * Create profile avatar flux.
     *
     * @param profile the profile
     */
    private void createAvatarFlux(ProfileEntity profile) {
        if ( !CommonUtils.isNullOrEmpty(profile.getAvatar()) )
            profile.setAvatar( avatarService.find(profile.getAvatar()) );
    }
    
    @Override
    public void checkEntity(ProfileEntity profile) {
        super.checkEntity(profile);
        CommonUtils.verifyValue( formatMessage(CommonConstant.ENTITY_NAME), profile.getName() );
    }
    
}
