package com.myssteriion.blindtest.integrationtest;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.entity.ProfileStatEntity;
import com.myssteriion.blindtest.persistence.dao.AvatarDAO;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
import com.myssteriion.blindtest.persistence.dao.ProfileDAO;
import com.myssteriion.blindtest.service.AvatarService;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractIntegrationTest extends AbstractTest {
    
    @Autowired
    protected AvatarDAO avatarDAO;
    
    @Autowired
    protected MusicDAO musicDAO;
    
    @Autowired
    protected ProfileDAO profileDAO;
    
    
    protected AvatarService avatarService;
    
    protected MusicService musicService;
    
    protected ProfileService profileService;
    
    protected GameService gameService;
    
    
    
    @Before
    public void before() throws ConflictException, NotFoundException {
        
        avatarService = new AvatarService(avatarDAO, configProperties);
        
        musicService = Mockito.spy( new MusicService(musicDAO, configProperties) );
        
        profileService = new ProfileService(profileDAO, avatarService);
        
        gameService = new GameService(musicService, profileService, configProperties, roundContentProperties);
        
        clearDataBase();
        insertData();
    }
    
    
    
    protected static final List<MusicEntity> MUSICS_LIST = Arrays.asList(
            new MusicEntity("musicName80", Theme.ANNEES_80),
            new MusicEntity("musicName90", Theme.ANNEES_90),
            new MusicEntity("musicName00", Theme.ANNEES_2000)
    );
    
    protected static final List<ProfileEntity> PROFILES_LIST = Arrays.asList(
            new ProfileEntity().setName("name1").setBackground(0).setProfileStat(new ProfileStatEntity().setBestScores(new HashMap<>() ).setWonGames( new HashMap<>() ).setFoundMusics( new HashMap<>() ).setListenedMusics( new HashMap<>() ).setPlayedGames( new HashMap<>() )),
            new ProfileEntity().setName("name2").setBackground(0).setProfileStat(new ProfileStatEntity().setBestScores(new HashMap<>() ).setWonGames( new HashMap<>() ).setFoundMusics( new HashMap<>() ).setListenedMusics( new HashMap<>() ).setPlayedGames( new HashMap<>() )),
            new ProfileEntity().setName("name3").setBackground(0).setProfileStat(new ProfileStatEntity().setBestScores(new HashMap<>() ).setWonGames( new HashMap<>() ).setFoundMusics( new HashMap<>() ).setListenedMusics( new HashMap<>() ).setPlayedGames( new HashMap<>() ))
    );
    
    
    
    protected void clearDataBase() throws NotFoundException {
        
        for (MusicEntity music : MUSICS_LIST) {
            
            MusicEntity m = musicService.find(music);
            if (m != null)
                musicService.delete(m);
        }
        
        for (ProfileEntity profile : PROFILES_LIST) {
            
            ProfileEntity p = profileService.find(profile);
            if (p != null)
                profileService.delete(p);
        }
    }
    
    protected void insertData() throws ConflictException {
        
        for (MusicEntity music : MUSICS_LIST)
            musicService.save(music);
        
        for (ProfileEntity profile : PROFILES_LIST)
            profileService.save(profile);
    }
    
}
