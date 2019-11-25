package com.myssteriion.blindtest.model.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The enum connection mode.
 */
public enum ConnectionMode {

    OFFLINE (false),
    ONLINE(true),
    BOTH(true);



    private boolean needConnection;

    ConnectionMode(boolean needConnection) {
        this.needConnection = needConnection;
    }

    /**
     * Is need connection boolean.
     *
     * @return the boolean
     */
    public boolean isNeedConnection() {
        return needConnection;
    }

    /**
     * Transform connection mode for search music.
     *
     * @return the connection modes list
     */
    public List<ConnectionMode> transformForSearchMusic() {
        return (this == ConnectionMode.BOTH)
                ? Arrays.asList(ConnectionMode.OFFLINE, ConnectionMode.ONLINE)
                : Collections.singletonList(this);
    }
}
