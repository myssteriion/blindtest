package com.myssteriion.blindtest.integrationtest;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.blindtest.service.ProfileStatService;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractIntegrationTest extends AbstractTest {
    
    @Autowired
    protected MusicDAO musicDAO;
    
    @Mock
    protected MusicService musicService;
    
    @Mock
    protected SpotifyService spotifyService;
    
    @Autowired
    protected ConfigProperties configProperties;
    
    @Autowired
    protected ProfileService profileService;
    
    @Autowired
    protected ProfileStatService profileStatService;
    
    @Autowired
    protected GameService gameService;
    
    
    
    protected static final List<MusicDTO> MUSICS_LIST = Arrays.asList(
            new MusicDTO("musicName80", Theme.ANNEES_80, ConnectionMode.OFFLINE),
            new MusicDTO("musicName90", Theme.ANNEES_90, ConnectionMode.OFFLINE),
            new MusicDTO("musicName00", Theme.ANNEES_2000, ConnectionMode.OFFLINE)
    );
    
    protected static final List<ProfileDTO> PROFILES_LIST = Arrays.asList(
            (ProfileDTO) new ProfileDTO("name1").setId(0),
            (ProfileDTO) new ProfileDTO("name2").setId(1),
            (ProfileDTO) new ProfileDTO("name3").setId(2)
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
