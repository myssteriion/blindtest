package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.model.common.RoundName;
import com.myssteriion.blindtest.model.round.AbstractRound;

/**
 * The Classic round.
 */
public class Classic extends AbstractRound {
    
    /**
     * Instantiates a new Classic.
     *
     * @param nbMusics   the nb musics
     * @param nbPointWon the nb point won
     */
    public Classic(int nbMusics, int nbPointWon) {
        super(RoundName.CLASSIC, nbMusics, nbPointWon);
    }
    
}
