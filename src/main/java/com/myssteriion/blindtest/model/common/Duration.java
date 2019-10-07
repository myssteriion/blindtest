package com.myssteriion.blindtest.model.common;

/**
 * The enum Duration.
 */
public enum Duration {

    VERY_SHORT(0.5),
    SHORT(0.75),
    NORMAL(1),
    LONG(1.25),
    VERY_LONG(1.5);



    private double ratio;

    Duration(double ratio) {
        this.ratio = ratio;
    }

    /**
     * Gets ratio.
     *
     * @return the ratio
     */
    public double getRatio() {
        return ratio;
    }

}
