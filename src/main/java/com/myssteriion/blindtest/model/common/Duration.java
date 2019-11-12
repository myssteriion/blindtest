package com.myssteriion.blindtest.model.common;

/**
 * The Duration enum.
 */
public enum Duration {

    SHORT(0.5),
    NORMAL(1),
    LONG(2);



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
