package com.myssteriion.blindtest.service.param;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.blindtest.persistence.dao.SpotifyParamDAO;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class SpotifyParamServiceTest extends AbstractTest {
    
    @Mock
    private SpotifyParamDAO dao;
    
    private SpotifyParamService spotifyParamService;
    
    
    
    @Before
    public void before() {
        spotifyParamService = new SpotifyParamService(dao, stringCipher);
    }
    
    
    
    @Test
    public void update() throws NotFoundException, ConflictException {
        String encryptedId = stringCipher.encrypt("id");
        String encryptedPwd = stringCipher.encrypt("pwd");
        
        SpotifyParamDTO spotifyParamEncrypted = new SpotifyParamDTO( encryptedId, encryptedPwd, new HashMap<>() );
        spotifyParamEncrypted.setId(0);
        SpotifyParamDTO spotifyParamDecrypted = new SpotifyParamDTO( "id", "pwd", new HashMap<>() );
        spotifyParamDecrypted.setId(0);
        
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn( Optional.of(spotifyParamEncrypted) );
        Mockito.when(dao.findAll()).thenReturn( Collections.singletonList(spotifyParamEncrypted) );
        Mockito.when(dao.save(Mockito.any(SpotifyParamDTO.class))).thenReturn(spotifyParamEncrypted);
        
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
        
        Mockito.when(dao.findAll()).thenReturn(iterable);
        Mockito.when(dao.save(Mockito.any(SpotifyParamDTO.class))).thenReturn(spotifyParamEncrypted);
        
        
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
        
        Mockito.when(dao.findAll()).thenReturn(iterable);
        Mockito.when(dao.save(Mockito.any(SpotifyParamDTO.class))).thenReturn(spotifyParamEncrypted);
        
        
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