package com.myssteriion.blindtest.integrationtest;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.persistence.dao.AvatarDAO;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
import com.myssteriion.blindtest.persistence.dao.ProfileDAO;
import com.myssteriion.blindtest.persistence.dao.ProfileStatDAO;
import com.myssteriion.blindtest.service.AvatarService;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.blindtest.service.ProfileStatService;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractIntegrationTest extends AbstractTest {
    
    @Autowired
    protected AvatarDAO avatarDAO;
    
    @Autowired
    protected MusicDAO musicDAO;
    
    @Autowired
    protected ProfileStatDAO profileStatDAO;
    
    @Autowired
    protected ProfileDAO profileDAO;
    
    
    protected AvatarService avatarService;
    
    protected MusicService musicService;
    
    protected ProfileStatService profileStatService;
    
    protected ProfileService profileService;
    
    @Mock
    protected SpotifyService spotifyService;
    
    protected GameService gameService;
    
    
    
    @Before
    public void before() throws ConflictException, NotFoundException {
        
        avatarService = new AvatarService(avatarDAO, configProperties);
        
        musicService = Mockito.spy( new MusicService(musicDAO, spotifyService, configProperties) );
        Mockito.doNothing().when(musicService).refresh();
        
        profileStatService = new ProfileStatService(profileStatDAO);
        
        profileService = new ProfileService(profileDAO, profileStatService, avatarService);
        
        gameService = new GameService(musicService, profileService, profileStatService, spotifyService, configProperties, roundContentProperties);
        
        clearDataBase();
        insertData();
    }
    
    
    
    protected static final List<MusicDTO> MUSICS_LIST = Arrays.asList(
            new MusicDTO("musicName80", Theme.ANNEES_80, ConnectionMode.OFFLINE),
            new MusicDTO("musicName90", Theme.ANNEES_90, ConnectionMode.OFFLINE),
            new MusicDTO("musicName00", Theme.ANNEES_2000, ConnectionMode.OFFLINE)
    );
    
    protected static final List<ProfileDTO> PROFILES_LIST = Arrays.asList(
            new ProfileDTO().setId(0).setName("name1").setAvatarName("avatarName").setBackground(0),
            new ProfileDTO().setId(1).setName("name2").setAvatarName("avatarName").setBackground(0),
            new ProfileDTO().setId(2).setName("name3").setAvatarName("avatarName").setBackground(0)
    );
    
    
    
    protected void clearDataBase() throws NotFoundException {
        
        for (MusicDTO musicDto : MUSICS_LIST) {
            
            MusicDTO m = musicService.find(musicDto);
            if (m != null)
                musicService.delete(m);
        }
        
        for (ProfileDTO profileDTO : PROFILES_LIST) {
            
            ProfileDTO p = profileService.find(profileDTO);
            if (p != null)
                profileService.delete(p);
        }
    }
    
    protected void insertData() throws ConflictException {
        
        for (MusicDTO musicDto : MUSICS_LIST)
            musicService.save(musicDto);
        
        for (ProfileDTO profileDTO : PROFILES_LIST)
            profileService.save(profileDTO);
    }
    
}
