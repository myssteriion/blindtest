package com.myssteriion.blindtest.model.common;

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

}
