package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;

import java.util.List;

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

        List<PlayerDTO> players = gameDto.getPlayers();
        List<String> loosers = musicResultDto.getLoosers();

        for (PlayerDTO playerDto : players) {
            if ( loosers.stream().anyMatch(name -> name.equals(playerDto.getName())) )
                playerDto.addScore(nbPointLoose);
        }

        return gameDto;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointLoose=" + nbPointLoose;
    }

}
