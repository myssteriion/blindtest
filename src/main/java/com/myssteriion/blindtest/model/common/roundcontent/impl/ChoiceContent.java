package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;

import java.util.List;

public class ChoiceContent extends AbstractRoundContent {

    private int nbPointBonusWon;

    private int nbPointMalusLoose;



    public ChoiceContent(int nbMusics, int nbPointWon, int nbPointBonusWon, int nbPointMalusLoose) {

        super(nbMusics, nbPointWon);
        this.nbPointBonusWon = (nbPointBonusWon < 0) ? 0 : nbPointBonusWon;
        this.nbPointMalusLoose = (nbPointMalusLoose > 0) ? 0 : nbPointMalusLoose;
    }



    public int getNbPointBonusWon() {
        return nbPointBonusWon;
    }

    public int getNbPointMalusLoose() {
        return nbPointMalusLoose;
    }


    @Override
    public GameDTO apply(GameDTO gameDto, MusicResultDTO musicResultDto) {

        gameDto = super.apply(gameDto, musicResultDto);

        List<PlayerDTO> players = gameDto.getPlayers();
        List<String> winnersBonus = musicResultDto.getWinnersBonus();
        List<String> loosersMalus = musicResultDto.getLoosersMalus();

        for (PlayerDTO playerDto : players) {
            if ( winnersBonus.stream().anyMatch(name -> name.equals(playerDto.getName())) )
                playerDto.addScore(nbPointBonusWon);

            if ( loosersMalus.stream().anyMatch(name -> name.equals(playerDto.getName())) )
                playerDto.addScore(nbPointMalusLoose);
        }

        return gameDto;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointBonusWon=" + nbPointBonusWon +
                ", nbPointMalusLoose=" + nbPointMalusLoose;
    }

}
