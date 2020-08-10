package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.persistence.dao.ProfileStatDAO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for ProfileStatDTO.
 */
@Service
public class ProfileStatService extends AbstractCRUDService<ProfileStatDTO, ProfileStatDAO> {
    
    @Autowired
    public ProfileStatService(ProfileStatDAO profileStatDao) {
        super(profileStatDao);
    }
    
    
    
    @Override
    public ProfileStatDTO find(ProfileStatDTO dto) {
        
        CommonUtils.verifyValue("entity", dto);
        
        if ( CommonUtils.isNullOrEmpty(dto.getId()) )
            return dao.findByProfileId(dto.getProfileId()).orElse(null);
        else
            return super.find(dto);
    }
    
    /**
     * Find profile stat dto by profileId.
     *
     * @param profile the profile dto
     * @return the profile stat dto
     * @throws NotFoundException the not found exception
     */
    public ProfileStatDTO findByProfile(ProfileDTO profile) throws NotFoundException {
        
        CommonUtils.verifyValue("profile", profile);
        CommonUtils.verifyValue("profile -> id", profile.getId());
        
        ProfileStatDTO profileStatDto = new ProfileStatDTO( profile.getId() );
        ProfileStatDTO foundProfileStatDTO = find(profileStatDto);
        
        if ( CommonUtils.isNullOrEmpty(foundProfileStatDTO) )
            throw new NotFoundException("Profile stat not found.");
        
        return foundProfileStatDTO;
    }
    
}
