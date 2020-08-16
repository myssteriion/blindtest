package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class NewGameTest extends AbstractTest {
    
    @Test
    public void getterSetter() {
        
        List<Integer> profilesId = Collections.singletonList(0);
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);
        
        NewGame newGame = new NewGame().setProfilesId(new HashSet<>(profilesId)).setDuration(duration).setConnectionMode(ConnectionMode.OFFLINE);
        Assert.assertEquals( new HashSet<>(profilesId), newGame.getProfilesId() );
        Assert.assertEquals( duration, newGame.getDuration() );
        Assert.assertNull( newGame.getThemes() );
        Assert.assertEquals( ConnectionMode.OFFLINE, newGame.getConnectionMode() );
        
        newGame = new NewGame().setProfilesId(new HashSet<>(profilesId)).setDuration(duration).setThemes(themes).setEffects(effects).setConnectionMode(ConnectionMode.ONLINE);
        Assert.assertEquals( new HashSet<>(profilesId), newGame.getProfilesId() );
        Assert.assertEquals( duration, newGame.getDuration() );
        Assert.assertEquals( themes, newGame.getThemes() );
        Assert.assertEquals( ConnectionMode.ONLINE, newGame.getConnectionMode() );
    }
    
    @Test
    public void toStringAndEquals() {
        
        List<Integer> profilesId = Arrays.asList(0);
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);
        
        NewGame gameDtoUn = new NewGame().setProfilesId(new HashSet<>(profilesId)).setDuration(duration).setThemes(themes).setEffects(effects).setConnectionMode(ConnectionMode.OFFLINE);
        Assert.assertEquals( "profilesId=[0], duration=NORMAL, themes=[ANNEES_60, ANNEES_70], effects=[NONE, SPEED], connectionMode=OFFLINE", gameDtoUn.toString() );
    }
    
}