package com.myssteriion.blindtest.model.music;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.Assert;
import org.junit.Test;

public class ThemeInfoTest extends AbstractTest {

    @Test
    public void constructor() {

        Theme theme = Theme.ANNEES_80;

        try {
            new ThemeInfo(null, 10, 10);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'theme' est obligatoire."), e);
        }

        ThemeInfo themeInfo = new ThemeInfo(theme, 5, 10);
        Assert.assertEquals( theme, themeInfo.getTheme() );
        Assert.assertEquals( new Integer(5), themeInfo.getOfflineNbMusics() );
        Assert.assertEquals( new Integer(10), themeInfo.getOnlineNbMusics() );

        themeInfo = new ThemeInfo(theme, -2, -4);
        Assert.assertEquals( theme, themeInfo.getTheme() );
        Assert.assertEquals( new Integer(0), themeInfo.getOfflineNbMusics() );
        Assert.assertEquals( new Integer(0), themeInfo.getOnlineNbMusics() );
    }

    @Test
    public void getterSetter() {

        Theme theme = Theme.ANNEES_80;

        ThemeInfo themeInfo = new ThemeInfo(theme, 5, 10);
        Assert.assertEquals( theme, themeInfo.getTheme() );
        Assert.assertEquals( new Integer(5), themeInfo.getOfflineNbMusics() );
        Assert.assertEquals( new Integer(10), themeInfo.getOnlineNbMusics() );
    }

    @Test
    public void toStringAndEquals() {

        Theme theme = Theme.ANNEES_80;
        ThemeInfo themeInfoUn = new ThemeInfo(theme, 5, 10);

        Assert.assertEquals( "theme=ANNEES_80, offlineNbMusics=5, onlineNbMusics=10", themeInfoUn.toString() );

        ThemeInfo themeInfoUnIso = themeInfoUn = new ThemeInfo(theme, 5, 10);
        ThemeInfo themeInfoDeux = new ThemeInfo(Theme.ANNEES_60, 5, 10);
        ThemeInfo themeInfoTrois = new ThemeInfo(theme, 6, 10);
        ThemeInfo themeInfoQuatre = new ThemeInfo(theme, 5, 11);
        ThemeInfo themeInfoCinq = new ThemeInfo(Theme.ANNEES_60, 6, 11);


        Assert.assertNotEquals(themeInfoUn, null);
        Assert.assertNotEquals(themeInfoUn, "bad class");
        Assert.assertEquals(themeInfoUn, themeInfoUn);
        Assert.assertEquals(themeInfoUn, themeInfoUnIso);
        Assert.assertNotEquals(themeInfoUn, themeInfoDeux);
        Assert.assertNotEquals(themeInfoUn, themeInfoTrois);
        Assert.assertNotEquals(themeInfoUn, themeInfoQuatre);
        Assert.assertNotEquals(themeInfoUn, themeInfoCinq);


        Assert.assertEquals(themeInfoUn.hashCode(), themeInfoUn.hashCode());
        Assert.assertEquals(themeInfoUn.hashCode(), themeInfoUnIso.hashCode());
        Assert.assertNotEquals(themeInfoUn.hashCode(), themeInfoDeux.hashCode());
        Assert.assertNotEquals(themeInfoUn.hashCode(), themeInfoTrois.hashCode());
        Assert.assertNotEquals(themeInfoUn.hashCode(), themeInfoQuatre.hashCode());
        Assert.assertNotEquals(themeInfoUn.hashCode(), themeInfoCinq.hashCode());
    }

}