package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.entity.AvatarEntity;
import com.myssteriion.blindtest.service.AvatarService;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.model.Empty;
import com.myssteriion.utils.rest.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Avatar.
 */
@RestController
@RequestMapping(path = "avatars")
public class AvatarController {
    
    private AvatarService avatarService;
    
    
    
    /**
     * Instantiates a new Avatar controller.
     *
     * @param avatarService the avatar service
     */
    @Autowired
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }
    
    
    
    /**
     * Refresh avatars.
     *
     * @return empty response
     */
    @PostMapping(path = "/refresh")
    public ResponseEntity<Empty> refresh() {
        avatarService.init();
        return RestUtils.create204();
    }
    
    /**
     * Find pageable of avatar filtered by name.
     *
     * @param name        the name
     * @param pageNumber  the page number
     * @param itemPerPage the item per page
     * @return the pageable of avatars
     */
    @GetMapping
    public ResponseEntity< Page<AvatarEntity> > findAllByName(
            @RequestParam(value = Constant.NAME, required = false, defaultValue = Constant.NAME_DEFAULT_VALUE) String name,
            @RequestParam(value = CommonConstant.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = CommonConstant.ITEM_PER_PAGE) Integer itemPerPage) {
        
        return RestUtils.create200( avatarService.findAllByName(name, pageNumber, itemPerPage) );
    }
    
}
