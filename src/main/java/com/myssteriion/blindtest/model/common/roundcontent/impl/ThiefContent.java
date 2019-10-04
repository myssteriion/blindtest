package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.dto.game.Game;
import com.myssteriion.blindtest.model.dto.game.MusicResult;

public class ThiefContent extends AbstractRoundContent {

    private int nbPointLoose;



    public ThiefContent(int nbMusics, int nbPointWon, int nbPointLoose) {

        super(nbMusics, nbPointWon);
        this.nbPointLoose = (nbPointLoose > 0) ? 0 : nbPointLoose;
    }



    public int getNbPointLoose() {
        return nbPointLoose;
    }


    @Override
    public Game apply(Game game, MusicResult musicResult) {

        game = super.apply(game, musicResult);

        game.getPlayers().stream()
                .filter( playerDto -> musicResult.isLooser(playerDto.getName()) )
                .forEach( playerDto -> playerDto.addScore(nbPointLoose) );

        return game;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointLoose=" + nbPointLoose;
    }

}
