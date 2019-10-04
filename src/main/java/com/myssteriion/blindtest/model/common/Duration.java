package com.myssteriion.blindtest.model.common;

/**
 * The enum Duration.
 */
public enum Duration {

    /**
     * Very short duration.
     */
    VERY_SHORT(0.5),

    /**
     * Short duration.
     */
    SHORT(0.75),

    /**
     * Normal duration.
     */
    NORMAL(1),

    /**
     * Long duration.
     */
    LONG(1.25),

    /**
     * Very long duration.
     */
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
