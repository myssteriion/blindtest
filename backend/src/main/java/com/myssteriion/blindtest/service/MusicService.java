package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.blindtest.spotify.SpotifyMusic;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for MusicDTO.
 */
@Service
public class MusicService extends AbstractCRUDService<MusicDTO, MusicDAO> {
    
    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MusicService.class);
    
    /**
     * The ConfigProperties.
     */
    private ConfigProperties configProperties;
    
    /**
     * The spotify service.
     */
    private SpotifyService spotifyService;
    
    /**
     * The music folder path.
     */
    private static String musicsFolderPath;
    
    
    
    /**
     * Instantiates a new Music service.
     *
     * @param musicDao         the music dao
     * @param spotifyService   the spotify service
     */
    @Autowired
    public MusicService(MusicDAO musicDao, SpotifyService spotifyService, ConfigProperties configProperties) {
        super(musicDao);
        this.spotifyService = spotifyService;
        this.configProperties = configProperties;
        initFolderPath();
    }
    
    private void initFolderPath() {
        MusicService.musicsFolderPath = Paths.get( configProperties.getMusicsFolderPath() ).toFile().getAbsolutePath();
    }
    
    
    
    /**
     * Scan offline and online musics and insert musics in DB.
     */
    @PostConstruct
    private void init() {
        
        boolean onlineMode = spotifyService.isConnected();
        if (!onlineMode)
            LOGGER.warn("Can't load online musics.");
        
        for ( Theme theme : Theme.values() ) {
            
            offlineInit(theme);
            if (onlineMode)
                onlineInit(theme);
        }
        
        for ( MusicDTO music : dao.findAll() ) {
            
            if ( (onlineMode && music.getConnectionMode() == ConnectionMode.ONLINE && !onlineMusicExists(music))
                    || (music.getConnectionMode() == ConnectionMode.OFFLINE && !offlineMusicExists(music)) ) {
                
                dao.deleteById( music.getId() );
            }
        }
    }
    
    /**
     * Scan offline musics and insert musics in DB.
     *
     * @param theme the theme
     */
    private void offlineInit(Theme theme) {
        
        String themeFolder = theme.getFolderName();
        Path path = Paths.get(musicsFolderPath, themeFolder);
        
        File themeDirectory = path.toFile();
        for ( File file : CommonUtils.getChildren(themeDirectory) ) {
            
            MusicDTO musicDto = new MusicDTO().setName( file.getName() ).setTheme(theme).setConnectionMode(ConnectionMode.OFFLINE);
            Optional<MusicDTO> optionalMusic = dao.findByNameAndThemeAndConnectionMode( musicDto.getName(), musicDto.getTheme(), musicDto.getConnectionMode() );
            if ( file.isFile() && CommonUtils.hadAudioExtension(file.getName()) && !optionalMusic.isPresent() )
                dao.save(musicDto);
        }
    }
    
    /**
     * Test if the music match with an existing file.
     *
     * @param music the music
     * @return TRUE if the music match with an existing file, FALSE otherwise
     */
    private boolean offlineMusicExists(MusicDTO music) {
        return Paths.get(musicsFolderPath, music.getTheme().getFolderName(), music.getName()).toFile().exists();
    }
    
    
    /**
     * Scan online musics and insert musics in DB.
     *
     * @param theme the theme
     */
    private void onlineInit(Theme theme) {
        
        try {
            List<SpotifyMusic> spotifyMusics = spotifyService.getMusicsByTheme(theme);
            for (SpotifyMusic spotifyMusic : spotifyMusics) {
                
                String musicName = spotifyMusic.getArtists() + CommonConstant.HYPHEN_WITH_SPACE + spotifyMusic.getName();
                MusicDTO musicDto = new MusicDTO( musicName, theme, ConnectionMode.ONLINE, spotifyMusic.getTrackId(), spotifyMusic.getPreviewUrl(), spotifyMusic.getTrackUrl() );
                Optional<MusicDTO> optionalMusic = dao.findByNameAndThemeAndConnectionMode( musicDto.getName(), musicDto.getTheme(), musicDto.getConnectionMode() );
                if ( !optionalMusic.isPresent() )
                    dao.save(musicDto);
            }
        }
        catch (SpotifyException e) {
            LOGGER.warn("Can't load online musics.", e);
        }
    }
    
    /**
     * Test if the music match with an online track.
     *
     * @param music the music
     * @return TRUE if the music match with an existing file OR the connection is KO, OR the test is KO, FALSE otherwise
     */
    private boolean onlineMusicExists(MusicDTO music) {
        
        boolean exists;
        
        try {
            exists = spotifyService.trackExistsInTheme( music.getSpotifyTrackId(), music.getTheme() );
        }
        catch (SpotifyException e) {
            exists = true;
            LOGGER.warn("Can't test if the track exists.", e);
        }
        
        return exists;
    }
    
    
    /**
     * Scan music folder and refresh the DB.
     */
    public void refresh() {
        init();
    }
    
    
    
    @Override
    public MusicDTO find(MusicDTO dto) {
        
        CommonUtils.verifyValue(CommonConstant.ENTITY, dto);
        
        if ( CommonUtils.isNullOrEmpty(dto.getId()) ) {
            checkAndFillDTO(dto);
            return dao.findByNameAndThemeAndConnectionMode(dto.getName(), dto.getTheme(), dto.getConnectionMode()).orElse(null);
        }
        else
            return super.find(dto);
    }
    
    @Override
    public void checkAndFillDTO(MusicDTO music) {
        
        super.checkAndFillDTO(music);
        
        CommonUtils.verifyValue("music -> name", music.getName() );
        CommonUtils.verifyValue("music -> theme", music.getTheme() );
        CommonUtils.verifyValue("music -> connectionMode", music.getConnectionMode() );
        
        music.setPlayed(Objects.requireNonNullElse(music.getPlayed(), 0) );
    }
    
    
    /**
     * Count by theme and connection mode
     *
     * @param theme          the theme
     * @param connectionMode the connection mode
     * @return the number of music
     */
    public Integer getMusicNumber(Theme theme, ConnectionMode connectionMode) {
        
        CommonUtils.verifyValue("theme", theme);
        CommonUtils.verifyValue("connectionMode", connectionMode);
        
        Integer nbMusic = 0;
        for (ConnectionMode mode : connectionMode.transformForSearchMusic() )
            nbMusic += dao.countByThemeAndConnectionMode(theme, mode);
        
        return nbMusic;
    }
    
    /**
     * Get nb musics by themes by connection mode.
     *
     * @return the themes info list
     */
    public List<ThemeInfo> computeThemesInfo() {
        
        List<ThemeInfo> themesInfo = new ArrayList<>();
        
        for ( Theme theme : Theme.getSortedTheme() ) {
            
            Integer offlineNbMusics = getMusicNumber(theme, ConnectionMode.OFFLINE);
            Integer onlineNbMusics = getMusicNumber(theme, ConnectionMode.ONLINE);
            
            themesInfo.add( new ThemeInfo(theme, offlineNbMusics, onlineNbMusics) );
        }
        
        return themesInfo;
    }
    
    /**
     * Randomly choose a music.
     *
     * @param themes     	    the themes filter (optional)
     * @param effects     	    the effects filter (optional)
     * @param connectionMode    the connection mode
     * @return the music dto
     * @throws NotFoundException the not found exception
     * @throws IOException       the io exception
     */
    public MusicDTO random(List<Theme> themes, List<Effect> effects, ConnectionMode connectionMode) throws NotFoundException, IOException, SpotifyException {
        
        CommonUtils.verifyValue("connectionMode", connectionMode);
        
        List<Theme> searchThemes = (CommonUtils.isNullOrEmpty(themes)) ? Theme.getSortedTheme() : CommonUtils.removeDuplicate(themes);
        List<ConnectionMode> connectionModes = connectionMode.transformForSearchMusic();
        
        List<MusicDTO> allMusics = new ArrayList<>( dao.findByThemeInAndConnectionModeIn(searchThemes, connectionModes) );
        
        if ( CommonUtils.isNullOrEmpty(allMusics) )
            throw new NotFoundException("No music found for themes (" + searchThemes.toString() + ").");
        
        List<Double> coefficients = computeCoefficients(allMusics);
        double ratio = 100 / (coefficients.stream().mapToDouble(Double::doubleValue).sum());
        List<Double> cumulativePercent = computeCumulativePercentByCoefficients(coefficients, ratio);
        Theme foundTheme = foundThemeByCumulativePercent(cumulativePercent);
        MusicDTO music = foundMusic(allMusics, foundTheme);
        
        if ( music.getConnectionMode().isNeedConnection() ) {
            spotifyService.testConnection();
        }
        else {
            
            Path path = Paths.get(musicsFolderPath, music.getTheme().getFolderName(), music.getName());
            music.setFlux( new Flux(path.toFile()) );
            
            List<Effect> searchEffects = (CommonUtils.isNullOrEmpty(effects)) ? Effect.getSortedEffect() : CommonUtils.removeDuplicate(effects);
            music.setEffect( foundEffect(searchEffects) );
        }
        
        return music;
    }
    
    /**
     * Compute coefficients by themes (for a theme : musics size / nbPlayed sum)
     *
     * @param allMusics all musics
     * @return coefficients by themes
     */
    private List<Double> computeCoefficients(List<MusicDTO> allMusics) {
        
        List<Double> coefficients = new ArrayList<>();
        
        for ( Theme theme : Theme.getSortedTheme() ) {
            
            List<MusicDTO> allMusicsInTheme = allMusics.stream()
                    .filter(music -> music.getTheme() == theme)
                    .collect( Collectors.toList() );
            
            double nbMusics = allMusicsInTheme.size();
            double nbPlayedSum = allMusicsInTheme.stream().mapToDouble(MusicDTO::getPlayed).sum();
            nbPlayedSum = (nbPlayedSum == 0) ? 1 : nbPlayedSum;
            coefficients.add(nbMusics / nbPlayedSum);
        }
        
        return coefficients;
    }
    
    /**
     * Compute cumulative percent from coefficients and ratio.
     *
     * @param coefficients the coefficients
     * @param ratio        the ration
     * @return the cumulative percent
     */
    private List<Double> computeCumulativePercentByCoefficients(List<Double> coefficients, double ratio) {
        
        List<Double> cumulativePercent = new ArrayList<>();
        
        cumulativePercent.add( coefficients.get(0) * ratio );
        for (int i = 1; i < coefficients.size(); i++)
            cumulativePercent.add( cumulativePercent.get(i-1) + (coefficients.get(i) * ratio) );
        
        return cumulativePercent;
    }
    
    /**
     * Found randomly theme in cumulative percent.
     *
     * @param cumulativePercent the cumulative percent
     * @return the randomly theme
     */
    private Theme foundThemeByCumulativePercent(List<Double> cumulativePercent) {
        
        Theme foundTheme = null;
        
        double random = Constant.RANDOM.nextDouble() * 100;
        int index = 0;
        
        while (foundTheme == null) {
            
            double cumul = cumulativePercent.get(index);
            if (random < cumul)
                foundTheme = Theme.getSortedTheme().get(index);
            else
                index++;
        }
        
        return foundTheme;
    }
    
    /**
     * Found the music which has been played the fewest times in the theme.
     *
     * @param allMusics all musics
     * @param theme     the theme
     * @return a music int the theme
     */
    private MusicDTO foundMusic(List<MusicDTO> allMusics, Theme theme) {
        
        List<MusicDTO> allMusicsTheme = allMusics.stream()
                .filter(music -> music.getTheme() == theme)
                .collect( Collectors.toList() );
        
        int min = allMusicsTheme.stream()
                .mapToInt(MusicDTO::getPlayed)
                .min()
                .getAsInt();
        
        List<MusicDTO> potentialMusics = allMusicsTheme.stream()
                .filter(music -> music.getPlayed() == min)
                .collect( Collectors.toList() );
        
        int random = Constant.RANDOM.nextInt( potentialMusics.size() );
        return potentialMusics.get(random);
    }
    
    /**
     * Found randomly effect.
     *
     * @param effects the effects list
     * @return the effect
     */
    private Effect foundEffect(List<Effect> effects) {
        return effects.get( Constant.RANDOM.nextInt(effects.size()) );
    }
    
}
