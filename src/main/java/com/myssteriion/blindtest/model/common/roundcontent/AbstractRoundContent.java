package com.myssteriion.blindtest.model.common.roundcontent;

import com.myssteriion.blindtest.model.dto.game.Game;
import com.myssteriion.blindtest.model.dto.game.MusicResult;
import com.myssteriion.blindtest.tools.Tool;

public abstract class AbstractRoundContent {

    protected int nbMusics;

    protected int nbPointWon;



    public AbstractRoundContent(int nbMusics, int nbPointWon) {
        this.nbMusics = (nbMusics < 0) ? 0 : nbMusics;
        this.nbPointWon = (nbPointWon < 0) ? 0 : nbPointWon;
    }



    public int getNbMusics() {
        return nbMusics;
    }

    public int getNbPointWon() {
        return nbPointWon;
    }


    public Game apply(Game game, MusicResult musicResult) {

        Tool.verifyValue("gameDto", game);
        Tool.verifyValue("musicResultDto", musicResult);

        game.getPlayers().stream()
                            .filter( playerDto -> musicResult.isWinner(playerDto.getName()) )
                            .forEach( playerDto -> playerDto.addScore(nbPointWon) );

        return game;
    }

    public boolean isFinished(Game game) {

        Tool.verifyValue("gameDto", game);

        return game.getNbMusicsPlayedInRound() == nbMusics;
    }

    public boolean isLastMusic(Game game) {

        Tool.verifyValue("gameDto", game);

        return game.getNbMusicsPlayedInRound() == nbMusics - 1;
    }


    @Override
    public String toString() {
        return "nbMusics=" + nbMusics +
                ", nbPointWon=" + nbPointWon;
    }

}
