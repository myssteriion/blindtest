package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;

public class ChoiceContent extends AbstractRoundContent {

    private int nbPointBonus;

    private int nbPointMalus;



    public ChoiceContent(int nbMusics, int nbPointWon, int nbPointBonus, int nbPointMalus) {

        super(nbMusics, nbPointWon);
        this.nbPointBonus = (nbPointBonus < 0) ? 0 : nbPointBonus;
        this.nbPointMalus = (nbPointMalus > 0) ? 0 : nbPointMalus;
    }



    public int getNbPointBonus() {
        return nbPointBonus;
    }

    public int getNbPointMalus() {
        return nbPointMalus;
    }


    @Override
    public GameDTO apply(GameDTO gameDto, MusicResultDTO musicResultDto) {

        gameDto = super.apply(gameDto, musicResultDto);

        gameDto.getPlayers().stream()
                .filter(PlayerDTO::isTurnToChoose)
                .forEach( playerDto -> {
                    int point = musicResultDto.isWinner(playerDto.getName()) ? nbPointBonus : nbPointMalus;
                    playerDto.addScore(point);
                    playerDto.setTurnToChoose(false);
                });

        if ( !isLastMusic(gameDto) ) {

            // +1 car le gameDTO n'a pas encore subit le next
            int numPlayer = (1 + gameDto.getNbMusicsPlayedInRound()) % gameDto.getPlayers().size();
            gameDto.getPlayers().get(numPlayer).setTurnToChoose(true);
        }

        return gameDto;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointBonus=" + nbPointBonus +
                ", nbPointMalus=" + nbPointMalus;
    }

}
