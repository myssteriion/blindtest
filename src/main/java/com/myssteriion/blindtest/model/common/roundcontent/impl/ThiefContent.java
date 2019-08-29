package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;

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
    public GameDTO apply(GameDTO gameDto, MusicResultDTO musicResultDto) {

        gameDto = super.apply(gameDto, musicResultDto);

        gameDto.getPlayers().stream()
                .filter( playerDto -> musicResultDto.isLooser(playerDto.getName()) )
                .forEach( playerDto -> playerDto.addScore(nbPointLoose) );

        return gameDto;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointLoose=" + nbPointLoose;
    }

}
