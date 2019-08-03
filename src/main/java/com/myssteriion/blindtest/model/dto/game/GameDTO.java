package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;

public class GameDTO extends AbstractDTO {

    private List<PlayerDTO> players;



    public GameDTO(List<PlayerDTO> players) {

        Tool.verifyValue("players", players);
        this.players = players;
    }



    public List<PlayerDTO> getPlayers() {
        return players;
    }

}
