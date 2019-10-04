package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.dto.game.Game;
import com.myssteriion.blindtest.model.dto.game.MusicResult;
import com.myssteriion.blindtest.model.dto.game.Player;

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
    public Game apply(Game game, MusicResult musicResult) {

        game = super.apply(game, musicResult);

        game.getPlayers().stream()
                .filter(Player::isTurnToChoose)
                .forEach( playerDto -> {
                    int point = musicResult.isWinner(playerDto.getName()) ? nbPointBonus : nbPointMalus;
                    playerDto.addScore(point);
                    playerDto.setTurnToChoose(false);
                });

        if ( !isLastMusic(game) ) {

            // +1 car le gameDTO n'a pas encore subit le next
            int numPlayer = (1 + game.getNbMusicsPlayedInRound()) % game.getPlayers().size();
            game.getPlayers().get(numPlayer).setTurnToChoose(true);
        }

        return game;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointBonus=" + nbPointBonus +
                ", nbPointMalus=" + nbPointMalus;
    }

}
