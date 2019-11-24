package com.myssteriion.blindtest.model.common;

/**
 * The enum Game mode.
 */
public enum GameMode {

    OFFLINE (false),
    ONLINE(true),
    BOTH(true);



    private boolean needConnection;

    GameMode(boolean needConnection) {
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

}
