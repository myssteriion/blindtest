package com.myssteriion.blindtest.model.common.roundcontent;

import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;

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


    public GameDTO apply(GameDTO gameDto, MusicResultDTO musicResultDto) {

        Tool.verifyValue("gameDto", gameDto);
        Tool.verifyValue("musicResultDto", musicResultDto);

        List<PlayerDTO> players = gameDto.getPlayers();
        List<String> winners = musicResultDto.getWinners();

        for (PlayerDTO playerDto : players) {
            if ( winners.stream().anyMatch(name -> name.equals(playerDto.getName())) )
                playerDto.addScore(nbPointWon);
        }

        return gameDto;
    }

    public boolean isFinished(GameDTO gameDto) {

        Tool.verifyValue("gameDto", gameDto);

        return gameDto.getNbMusicsPlayedInRound() == nbMusics;
    }

    public boolean isLastMusic(GameDTO gameDto) {

        Tool.verifyValue("gameDto", gameDto);

        return gameDto.getNbMusicsPlayedInRound() == nbMusics - 1;
    }


    @Override
    public String toString() {
        return "nbMusics=" + nbMusics +
                ", nbPointWon=" + nbPointWon;
    }

}
