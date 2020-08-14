package com.myssteriion.blindtest.model.common;

import java.util.Arrays;
import java.util.List;

/**
 * The Effect enum.
 */
public enum Effect {
    
    NONE, SLOW, SPEED, MIX;
    
    
    
    /**
     * Gets sorted effect.
     *
     * @return the sorted effect
     */
    public static List<Effect> getSortedEffect() {
        return Arrays.asList(NONE, SLOW, SPEED, MIX);
    }
    
}
