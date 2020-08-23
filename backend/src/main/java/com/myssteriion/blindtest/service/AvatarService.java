package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.entity.AvatarEntity;
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
import java.util.Objects;

/**
 * Service for Avatar.
 */
@Service
public class AvatarService extends AbstractCRUDService<AvatarEntity, AvatarDAO> {
    
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
        super(avatarDAO, "avatar");
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
            
            AvatarEntity avatar = new AvatarEntity(file.getName() );
            if ( file.isFile() && CommonUtils.hadImageExtension(file.getName()) && !dao.findByName(file.getName()).isPresent() )
                dao.save(avatar);
        }
        
        for ( AvatarEntity avatar : dao.findAll() ) {
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
    private boolean avatarFileExists(AvatarEntity avatar) {
        return avatar != null && Paths.get(avatarsFolderPath, avatar.getName()).toFile().exists();
    }
    
    /**
     * Refresh avatars list.
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
        
        Page<AvatarEntity> page = dao.findAll(Pageable.unpaged());
        int i = 0;
        while (!needRefresh && i < page.getContent().size()) {
            
            AvatarEntity avatar = page.getContent().get(i);
            createFlux(avatar);
            if (!avatar.getFlux().isFileExists())
                needRefresh = true;
            
            i++;
        }
        
        return needRefresh;
    }
    
    
    @Override
    public AvatarEntity save(AvatarEntity entity) throws ConflictException {
        
        AvatarEntity avatar = super.save(entity);
        createFlux(avatar);
        return avatar;
    }
    
    @Override
    public AvatarEntity update(AvatarEntity entity) throws NotFoundException, ConflictException {
        
        AvatarEntity avatar = super.update(entity);
        createFlux(avatar);
        return avatar;
    }
    
    @Override
    public AvatarEntity find(AvatarEntity entity) {
        
        super.checkEntity(entity);
        
        AvatarEntity avatar;
        if ( CommonUtils.isNullOrEmpty(entity.getId()) ) {
            checkEntity(entity);
            avatar = dao.findByName(entity.getName()).orElse(null);
        }
        else
            avatar = super.find(entity);
        
        if (avatar != null)
            createFlux(avatar);
        
        return avatar;
    }
    
    /**
     * Find a page of Avatar filtered by a name.
     *
     * @param name        the name
     * @param pageNumber  the page number
     * @param itemPerPage the item per page
     * @return the page of avatars filtered by search name
     */
    public Page<AvatarEntity> findAllByName(String name, int pageNumber, int itemPerPage) {
        
        name = Objects.requireNonNullElse(name, CommonConstant.EMPTY);
        
        itemPerPage = Math.max(itemPerPage, 1);
        itemPerPage = Math.min(itemPerPage, Constant.ITEM_PER_PAGE_MAX);
        
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
        Pageable pageable = PageRequest.of( pageNumber, itemPerPage, Sort.by(order) );
        
        Page<AvatarEntity> page = dao.findAllByNameContainingIgnoreCase(name, pageable);
        page.forEach(this::createFlux);
        
        return page;
    }
    
    
    /**
     * Create avatar flux on avatar.
     *
     * @param avatar the avatar
     */
    private void createFlux(AvatarEntity avatar) {
        
        try {
            
            Path path = Paths.get( avatarsFolderPath, avatar.getName() );
            avatar.setFlux( new Flux(path.toFile()) );
        }
        catch (IOException e) {
            throw new CustomRuntimeException("Can't create flux.", e);
        }
    }
    
    @Override
    public void checkEntity(AvatarEntity avatar) {
        super.checkEntity(avatar);
        CommonUtils.verifyValue( formatMessage(CommonConstant.ENTITY_NAME), avatar.getName() );
    }
    
}
