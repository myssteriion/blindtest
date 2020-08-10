package com.myssteriion.blindtest.service.param;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.blindtest.persistence.dao.SpotifyParamDAO;
import com.myssteriion.utils.cipher.StringCipher;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class SpotifyParamServiceTest extends AbstractTest {
    
    @Autowired
    private StringCipher stringCipher;
    
    @Mock
    private SpotifyParamDAO spotifyParamDAO;
    
    @InjectMocks
    private SpotifyParamService spotifyParamService;
    
    
    
    @Test
    public void update() throws NotFoundException, ConflictException {
    
        String encryptedId = stringCipher.encrypt("id");
        String encryptedPwd = stringCipher.encrypt("pwd");
    
        SpotifyParamDTO spotifyParamEncrypted = new SpotifyParamDTO( encryptedId, encryptedPwd, new HashMap<>() );
        spotifyParamEncrypted.setId(0);
        SpotifyParamDTO spotifyParamDecrypted = new SpotifyParamDTO( "id", "pwd", new HashMap<>() );
        spotifyParamDecrypted.setId(0);
        
        Mockito.when(spotifyParamDAO.findById(Mockito.anyInt())).thenReturn( Optional.of(spotifyParamEncrypted) );
        Mockito.when(spotifyParamDAO.save(Mockito.any(SpotifyParamDTO.class))).thenReturn(spotifyParamEncrypted);
    
        SpotifyParamDTO actual = spotifyParamService.update(spotifyParamDecrypted);
        Assert.assertEquals(spotifyParamDecrypted, actual);
    }
    
    @Test
    public void find() {
    
        String encryptedId = stringCipher.encrypt("id");
        String encryptedPwd = stringCipher.encrypt("pwd");
    
        SpotifyParamDTO spotifyParamEncrypted = new SpotifyParamDTO( encryptedId, encryptedPwd, new HashMap<>() );
        SpotifyParamDTO spotifyParamDecrypted = new SpotifyParamDTO( "id", "pwd", new HashMap<>() );
    
    
        Iterator<SpotifyParamDTO> iterator = Mockito.mock(Iterator.class);
        Mockito.when(iterator.hasNext()).thenReturn(false, true);
        Mockito.when(iterator.next()).thenReturn(spotifyParamEncrypted);
    
        Iterable<SpotifyParamDTO> iterable = Mockito.mock(Iterable.class);
        Mockito.when(iterable.iterator()).thenReturn(iterator);
    
        Mockito.when(spotifyParamDAO.findAll()).thenReturn(iterable);
        Mockito.when(spotifyParamDAO.save(Mockito.any(SpotifyParamDTO.class))).thenReturn(spotifyParamEncrypted);
    
    
        SpotifyParamDTO actual = spotifyParamService.find();
        Assert.assertEquals(spotifyParamDecrypted, actual);
    
        actual = spotifyParamService.find();
        Assert.assertEquals(spotifyParamDecrypted, actual);
    
        actual = spotifyParamService.find();
        Assert.assertEquals(spotifyParamDecrypted, actual);
    
        actual = spotifyParamService.find();
        Assert.assertEquals(spotifyParamDecrypted, actual);
        
    }
    
    @Test
    public void findWithParam() {
        
        String encryptedId = stringCipher.encrypt("id");
        String encryptedPwd = stringCipher.encrypt("pwd");
        
        SpotifyParamDTO spotifyParamEncrypted = new SpotifyParamDTO( encryptedId, encryptedPwd, new HashMap<>() );
        SpotifyParamDTO spotifyParamDecrypted = new SpotifyParamDTO( "id", "pwd", new HashMap<>() );
        
        
        Iterator<SpotifyParamDTO> iterator = Mockito.mock(Iterator.class);
        Mockito.when(iterator.hasNext()).thenReturn(false, true);
        Mockito.when(iterator.next()).thenReturn(spotifyParamEncrypted);
    
        Iterable<SpotifyParamDTO> iterable = Mockito.mock(Iterable.class);
        Mockito.when(iterable.iterator()).thenReturn(iterator);
        
        Mockito.when(spotifyParamDAO.findAll()).thenReturn(iterable);
        Mockito.when(spotifyParamDAO.save(Mockito.any(SpotifyParamDTO.class))).thenReturn(spotifyParamEncrypted);
    
    
        SpotifyParamDTO actual = spotifyParamService.find(null);
        Assert.assertEquals(spotifyParamDecrypted, actual);
        
        actual = spotifyParamService.find(null);
        Assert.assertEquals(spotifyParamDecrypted, actual);
    
        actual = spotifyParamService.find(new SpotifyParamDTO());
        Assert.assertEquals(spotifyParamDecrypted, actual);
    
        actual = spotifyParamService.find(new SpotifyParamDTO("osef", "osef", new HashMap<>()));
        Assert.assertEquals(spotifyParamDecrypted, actual);
    }
    
}