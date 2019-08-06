package com.myssteriion.blindtest.model.common;

public enum Duration {

    VERY_SHORT(0.5),
    SHORT(0.75),
    NORMAL(1),
    LONG(1.5),
    VERY_LONG(2);



    private double ratio;



    Duration(double ratio) {
        this.ratio = ratio;
    }



    public double getRatio() {
        return ratio;
    }

}
