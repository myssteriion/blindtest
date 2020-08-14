package com.myssteriion.blindtest.service.param;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.blindtest.persistence.dao.SpotifyParamDAO;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.cipher.StringCipher;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
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
     * The stringCipher.
     */
    private StringCipher stringCipher;
    
    
    
    /**
     * Instantiates a new Abstract service.
     *
     * @param spotifyParamDAO the dao
     * @param stringCipher    the stringCipher
     */
    @Autowired
    public SpotifyParamService(SpotifyParamDAO spotifyParamDAO, StringCipher stringCipher) {
        super(spotifyParamDAO);
        this.stringCipher = stringCipher;
    }
    
    
    
    @Override
    public SpotifyParamDTO update(SpotifyParamDTO dto) throws NotFoundException, ConflictException {
        
        CommonUtils.verifyValue(CommonConstant.ENTITY, dto);
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
    
    
    private SpotifyParamDTO encrypt(SpotifyParamDTO spotifyParam) {
        
        SpotifyParamDTO spotifyParamEncrypted = new SpotifyParamDTO();
        spotifyParamEncrypted.setId( spotifyParam.getId() );
        
        spotifyParamEncrypted.setClientId( stringCipher.encrypt(spotifyParam.getClientId()) );
        spotifyParamEncrypted.setClientSecret( stringCipher.encrypt(spotifyParam.getClientSecret()) );
        
        Map<Theme, String> map = spotifyParam.getPlaylistIds();
        for ( Theme theme : map.keySet() )
            spotifyParamEncrypted.getPlaylistIds().put( theme, stringCipher.encrypt(map.get(theme)) );
        
        return spotifyParamEncrypted;
    }
    
    private SpotifyParamDTO decrypt(SpotifyParamDTO spotifyParam) {
        
        SpotifyParamDTO spotifyParamDecrypted = new SpotifyParamDTO();
        spotifyParamDecrypted.setId( spotifyParam.getId() );
        
        spotifyParamDecrypted.setClientId( stringCipher.decrypt(spotifyParam.getClientId()) );
        spotifyParamDecrypted.setClientSecret( stringCipher.decrypt(spotifyParam.getClientSecret()) );
        
        Map<Theme, String> map = spotifyParam.getPlaylistIds();
        for ( Theme theme : map.keySet() )
            spotifyParamDecrypted.getPlaylistIds().put( theme, stringCipher.decrypt(map.get(theme)) );
        
        return spotifyParamDecrypted;
    }
    
}
