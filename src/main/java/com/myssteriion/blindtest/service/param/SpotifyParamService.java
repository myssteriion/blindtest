package com.myssteriion.blindtest.service.param;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.blindtest.persistence.dao.SpotifyParamDAO;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.rest.exception.ConflictException;
import com.myssteriion.utils.rest.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

/**
 * Service for SpotifyParamDTO.
 */
@Service
public class SpotifyParamService extends AbstractCRUDService<SpotifyParamDTO, SpotifyParamDAO> {
    
    /**
     * Instantiates a new Abstract service.
     *
     * @param spotifyParamDAO the dao
     */
    @Autowired
    public SpotifyParamService(SpotifyParamDAO spotifyParamDAO) {
        super(spotifyParamDAO);
    }
    
    
    
    @Override
    public SpotifyParamDTO update(SpotifyParamDTO dto) throws NotFoundException, ConflictException {
        
        CommonUtils.verifyValue("entity", dto);
        dto.setId(null);
        
        /*
         * le paramétrage est unique
         * peut importe le IN on récupère d'abord le seul de la DB pour ensuite le mettre à jour
         */
        SpotifyParamDTO paramInDb = find();
        dto.setId( paramInDb.getId() );
        
        return decrypt( super.update(encrypt(dto)) );
    }
    
    /**
     * The SpotifyParam is unique.
     *
     * @return the SpotifyParam
     */
    public SpotifyParamDTO find() {
        return find(null);
    }
    
    @Override
    public SpotifyParamDTO find(SpotifyParamDTO dto) {
        
        /*
         * le paramétrage est unique
         * peu importe le IN, on récupère le seul de la DB
         */
        Iterator<SpotifyParamDTO> ite = dao.findAll().iterator();
        return ( ite.hasNext() ) ? decrypt(ite.next()) : decrypt( dao.save( encrypt(new SpotifyParamDTO()) ) );
    }
    
    
    private SpotifyParamDTO encrypt(SpotifyParamDTO dto) {
        
        dto.setClientId( CommonUtils.STRING_CIPHER.encrypt(dto.getClientId()) );
        dto.setClientSecret( CommonUtils.STRING_CIPHER.encrypt(dto.getClientSecret()) );
        
        Map<Theme, String> map = dto.getPlaylistIds();
        for ( Theme theme : map.keySet() )
            map.put( theme, CommonUtils.STRING_CIPHER.encrypt(map.get(theme)) );
        
        return dto;
    }
    
    private SpotifyParamDTO decrypt(SpotifyParamDTO dto) {
        
        dto.setClientId( CommonUtils.STRING_CIPHER.decrypt(dto.getClientId()) );
        dto.setClientSecret( CommonUtils.STRING_CIPHER.decrypt(dto.getClientSecret()) );
        
        Map<Theme, String> map = dto.getPlaylistIds();
        for ( Theme theme : map.keySet() )
            map.put( theme, CommonUtils.STRING_CIPHER.decrypt(map.get(theme)) );
        
        return dto;
    }
    
}
