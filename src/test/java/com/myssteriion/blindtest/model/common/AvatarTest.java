package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.tools.Constant;
import org.junit.Assert;
import org.junit.Test;

public class AvatarTest extends AbstractTest {

    @Test
    public void constructor() {

        String name = "name";

        Avatar avatar = new Avatar(null);
        Assert.assertEquals( Constant.DEFAULT_AVATAR, avatar.getName() );
        Assert.assertFalse( avatar.isFileExists() );
        Assert.assertNull( avatar.getContentType() );

        avatar = new Avatar("");
        Assert.assertEquals( Constant.DEFAULT_AVATAR, avatar.getName() );
        Assert.assertFalse( avatar.isFileExists() );
        Assert.assertNull( avatar.getContentType() );

        avatar = new Avatar(name);
        Assert.assertEquals( name, avatar.getName() );
        Assert.assertFalse( avatar.isFileExists() );
        Assert.assertNull( avatar.getContentType() );
    }

    @Test
    public void getterSetter() {

        String name = "name";
        String newName = "newName";

        Avatar avatar = new Avatar(name);
        Assert.assertEquals( name, avatar.getName() );
        Assert.assertFalse( avatar.isFileExists() );
        Assert.assertNull( avatar.getContentType() );
    }

    @Test
    public void toStringAndEquals() {

        String name = "name";
        Avatar avatarUn = new Avatar(name);

        Assert.assertEquals( "name=name, fileExists=false, contentType=null", avatarUn.toString() );

        Avatar avatarUnIso = new Avatar(name);
        Avatar avatarDeux = new Avatar(name + "1");

        Assert.assertNotEquals(avatarUn, null);
        Assert.assertNotEquals(avatarUn, "bad class");
        Assert.assertEquals(avatarUn, avatarUn);
        Assert.assertEquals(avatarUn, avatarUnIso);
        Assert.assertNotEquals(avatarUn, avatarDeux);

        Assert.assertEquals(avatarUn.hashCode(), avatarUn.hashCode());
        Assert.assertEquals(avatarUn.hashCode(), avatarUnIso.hashCode());
        Assert.assertNotEquals(avatarUn.hashCode(), avatarDeux.hashCode());
    }

    @Test
    public void comparator() {

        String name = "name";
        Avatar avatarUn = new Avatar(name);
        Avatar avatarUnIso = new Avatar(name);
        Avatar avatarDeux = new Avatar(name + "1");

        Assert.assertEquals( 0, Avatar.COMPARATOR.compare(avatarUn, avatarUn) );
        Assert.assertEquals( 0, Avatar.COMPARATOR.compare(avatarUn, avatarUnIso) );
        Assert.assertEquals( -1, Avatar.COMPARATOR.compare(avatarUn, avatarDeux) );
        Assert.assertEquals( 1, Avatar.COMPARATOR.compare(avatarDeux, avatarUn) );

        Assert.assertEquals( 0, Avatar.COMPARATOR.compare(null, null) );
        Assert.assertEquals( 1, Avatar.COMPARATOR.compare(avatarUn, null) );
        Assert.assertEquals( -1, Avatar.COMPARATOR.compare(null, avatarDeux) );
    }
    
}