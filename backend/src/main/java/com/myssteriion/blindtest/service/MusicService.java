package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.MusicFilter;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for music.
 */
@Service
public class MusicService extends AbstractCRUDService<MusicEntity, MusicDAO> {
    
    /**
     * The ConfigProperties.
     */
    private ConfigProperties configProperties;
    
    /**
     * The music folder path.
     */
    private static String musicsFolderPath;
    
    
    
    /**
     * Instantiates a new Music service.
     *
     * @param musicDao the music dao
     */
    @Autowired
    public MusicService(MusicDAO musicDao, ConfigProperties configProperties) {
        super(musicDao, "music");
        this.configProperties = configProperties;
        initFolderPath();
    }
    
    private void initFolderPath() {
        MusicService.musicsFolderPath = Paths.get( configProperties.getMusicsFolderPath() ).toFile().getAbsolutePath();
    }
    
    
    
    /**
     * Scan musics and insert musics in DB.
     */
    @PostConstruct
    public void init() {
        
        List<MusicEntity> musics = new ArrayList<>();
        
        for ( MusicEntity music : dao.findAll() ) {
            
            musics.add(music);
            if ( !Paths.get(musicsFolderPath, music.getTheme().getFolderName(), music.getName()).toFile().exists() )
                dao.deleteById( music.getId() );
        }
        
        for ( Theme theme : Theme.values() ) {
            
            String themeFolder = theme.getFolderName();
            Path path = Paths.get(musicsFolderPath, themeFolder);
            
            for ( File file : CommonUtils.getChildren(path.toFile()) ) {
                
                MusicEntity music = new MusicEntity().setName( file.getName() ).setTheme(theme);
                if ( file.isFile() && CommonUtils.hadAudioExtension(file.getName()) && !musics.contains(music) )
                    dao.save(music);
            }
        }
    }
    
    
    
    @Override
    public MusicEntity find(MusicEntity entity) {
        
        super.checkEntity(entity);
        
        if ( CommonUtils.isNullOrEmpty(entity.getId()) ) {
            checkEntity(entity);
            return dao.findByNameAndTheme(entity.getName(), entity.getTheme()).orElse(null);
        }
        else
            return super.find(entity);
    }
    
    
    /**
     * Count by theme.
     *
     * @param theme the theme
     * @return the number of music
     */
    public Integer getMusicNumber(Theme theme) {
        
        CommonUtils.verifyValue("theme", theme);
        return dao.countByTheme(theme);
    }
    
    /**
     * Get nb musics by themes by connection mode.
     *
     * @return the themes info list
     */
    public List<ThemeInfo> computeThemesInfo() {
        
        List<ThemeInfo> themesInfo = new ArrayList<>();
        
        for ( Theme theme : Theme.getSortedTheme() ) {
            Integer nbMusics = getMusicNumber(theme);
            themesInfo.add( new ThemeInfo(theme, nbMusics) );
        }
        
        return themesInfo;
    }
    
    /**
     * Randomly choose a music.
     *
     * @param musicFilter  musicFilter
     * @return the music
     * @throws NotFoundException the not found exception
     * @throws IOException       the io exception
     */
    public MusicEntity random(MusicFilter musicFilter) throws NotFoundException, IOException {
    
        List<Theme> searchThemes = ( CommonUtils.isNullOrEmpty(musicFilter) || CommonUtils.isNullOrEmpty(musicFilter.getThemes()) )
                ? Theme.getSortedTheme() : CommonUtils.removeDuplicate(musicFilter.getThemes());
        
        List<MusicEntity> allMusics = new ArrayList<>( dao.findByThemeIn(searchThemes) );
        
        if ( CommonUtils.isNullOrEmpty(allMusics) )
            throw new NotFoundException("No music found for themes (" + searchThemes.toString() + ").");
        
        List<Double> coefficients = computeCoefficients(allMusics);
        double ratio = 100 / (coefficients.stream().mapToDouble(Double::doubleValue).sum());
        List<Double> cumulativePercent = computeCumulativePercentByCoefficients(coefficients, ratio);
        Theme foundTheme = foundThemeByCumulativePercent(cumulativePercent);
        MusicEntity music = foundMusic(allMusics, foundTheme);
        
        Path path = Paths.get(musicsFolderPath, music.getTheme().getFolderName(), music.getName());
        music.setFlux( new Flux(path.toFile()) );
        
        List<Effect> searchEffects = ( CommonUtils.isNullOrEmpty(musicFilter) || CommonUtils.isNullOrEmpty(musicFilter.getEffects()) )
                ? Effect.getSortedEffect() : CommonUtils.removeDuplicate(musicFilter.getEffects());
        
        music.setEffect( foundEffect(searchEffects) );
        
        return music;
    }
    
    /**
     * Compute coefficients by themes (for a theme : musics size / nbPlayed sum)
     *
     * @param allMusics all musics
     * @return coefficients by themes
     */
    private List<Double> computeCoefficients(List<MusicEntity> allMusics) {
        
        List<Double> coefficients = new ArrayList<>();
        
        for ( Theme theme : Theme.getSortedTheme() ) {
            
            List<MusicEntity> allMusicsInTheme = allMusics.stream()
                    .filter(music -> music.getTheme() == theme)
                    .collect( Collectors.toList() );
            
            double nbMusics = allMusicsInTheme.size();
            double nbPlayedSum = allMusicsInTheme.stream().mapToDouble(MusicEntity::getPlayed).sum();
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
    private MusicEntity foundMusic(List<MusicEntity> allMusics, Theme theme) {
        
        List<MusicEntity> allMusicsTheme = allMusics.stream()
                .filter(music -> music.getTheme() == theme)
                .collect( Collectors.toList() );
        
        int min = allMusicsTheme.stream()
                .mapToInt(MusicEntity::getPlayed)
                .min()
                .getAsInt();
        
        List<MusicEntity> potentialMusics = allMusicsTheme.stream()
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
    
    
    @Override
    public void checkEntity(MusicEntity music) {
        
        super.checkEntity(music);
        CommonUtils.verifyValue( formatMessage(CommonConstant.ENTITY_NAME), music.getName() );
        CommonUtils.verifyValue(entityName + " -> theme", music.getTheme() );
    }
    
}
