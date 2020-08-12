package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.dto.AvatarDTO;
import com.myssteriion.blindtest.persistence.dao.AvatarDAO;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.CustomRuntimeException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service for Avatar.
 */
@Service
public class AvatarService extends AbstractCRUDService<AvatarDTO, AvatarDAO> {
    
    /**
     * The ConfigProperties.
     */
    private ConfigProperties configProperties;
    
    /**
     * The avatars folder path.
     */
    private static String avatarsFolderPath;
    
    
    
    /**
     * Instantiates a new Abstract service.
     *
     * @param avatarDAO the dao
     */
    @Autowired
    public AvatarService(AvatarDAO avatarDAO, ConfigProperties configProperties) {
        super(avatarDAO);
        this.configProperties = configProperties;
        initFolderPath();
    }
    
    private void initFolderPath() {
        avatarsFolderPath = Paths.get( configProperties.getAvatarsFolderPath() ).toFile().getAbsolutePath();
    }
    
    
    
    @PostConstruct
    private void init() {
        
        Path path = Paths.get(avatarsFolderPath);
        
        File avatarDirectory = path.toFile();
        for ( File file : CommonUtils.getChildren(avatarDirectory) ) {
            
            AvatarDTO avatarDTO = new AvatarDTO( file.getName() );
            if ( file.isFile() && CommonUtils.hadImageExtension(file.getName()) && !dao.findByName(file.getName()).isPresent() )
                dao.save(avatarDTO);
        }
        
        for ( AvatarDTO avatar : dao.findAll() ) {
            if ( !avatarFileExists(avatar) )
                dao.deleteById( avatar.getId() );
        }
    }
    
    /**
     * Test if the avatar match with an existing file.
     *
     * @param avatar the avatar
     * @return TRUE if the avatar match with an existing file, FALSE otherwise
     */
    private boolean avatarFileExists(AvatarDTO avatar) {
        return avatar != null && Paths.get(avatarsFolderPath, avatar.getName()).toFile().exists();
    }
    
    /**
     * Refresh avatarDTOS list (cache).
     */
    public void refresh() {
        init();
    }
    
    /**
     * Test if the repo needs to be refresh.
     *
     * @return TRUE if the repo needs to be refresh, FALSE otherwise
     */
    public boolean needRefresh() {
        
        File avatarDirectory = Paths.get(avatarsFolderPath).toFile();
        if ( CommonUtils.getChildren(avatarDirectory).size() != dao.count() )
            return true;
        
        
        boolean needRefresh = false;
        
        Page<AvatarDTO> page = dao.findAll(Pageable.unpaged());
        int i = 0;
        while (!needRefresh && i < page.getContent().size()) {
            
            AvatarDTO avatar = page.getContent().get(i);
            createAvatarFlux(avatar);
            if (!avatar.getFlux().isFileExists())
                needRefresh = true;
            
            i++;
        }
        
        return needRefresh;
    }
    
    
    @Override
    public AvatarDTO save(AvatarDTO dto) throws ConflictException {
        
        AvatarDTO avatar = super.save(dto);
        createAvatarFlux(avatar);
        return avatar;
    }
    
    @Override
    public AvatarDTO update(AvatarDTO dto) throws NotFoundException, ConflictException {
        
        AvatarDTO avatar = super.update(dto);
        createAvatarFlux(avatar);
        return avatar;
    }
    
    @Override
    public AvatarDTO find(AvatarDTO dto) {
        
        CommonUtils.verifyValue(CommonConstant.ENTITY, dto);
        
        AvatarDTO avatar;
        if ( CommonUtils.isNullOrEmpty(dto.getId()) )
            avatar = dao.findByName(dto.getName()).orElse(null);
        else
            avatar = super.find(dto);
        
        if (avatar != null)
            createAvatarFlux(avatar);
        
        return avatar;
    }
    
    /**
     * Find a page of Avatar filtered by a search name.
     *
     * @param searchName  the search name
     * @param pageNumber  the page number
     * @param itemPerPage the item per page
     * @return the page of avatars filtered by search name
     */
    public Page<AvatarDTO> findAllBySearchName(String searchName, int pageNumber, int itemPerPage) {
        
        if (searchName == null)
            searchName = "";
        
        itemPerPage = Math.max(itemPerPage, 1);
        itemPerPage = Math.min(itemPerPage, Constant.ITEM_PER_PAGE_MAX);
        
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
        Pageable pageable = PageRequest.of( pageNumber, itemPerPage, Sort.by(order) );
        
        Page<AvatarDTO> page = dao.findAllByNameContainingIgnoreCase(searchName, pageable);
        page.forEach(this::createAvatarFlux);
        
        return page;
    }
    
    
    /**
     * Create avatar flux on avatar.
     *
     * @param avatar the avatar
     */
    public void createAvatarFlux(AvatarDTO avatar) {
        
        CommonUtils.verifyValue("avatar", avatar);
        
        try {
            
            Path path = Paths.get(avatarsFolderPath, avatar.getName() );
            avatar.setFlux( new Flux(path.toFile()) );
        }
        catch (IOException e) {
            throw new CustomRuntimeException("Can't create avatar flux.", e);
        }
    }
    
}
