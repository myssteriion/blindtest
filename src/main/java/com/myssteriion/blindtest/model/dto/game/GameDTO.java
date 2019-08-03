package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.tools.Tool;

import java.util.List;
import java.util.stream.Collectors;

public class GameDTO extends AbstractDTO {

    private List<PlayerDTO> players;



    public GameDTO(List<String> playersNames) {

        Tool.verifyValue("players", playersNames);
        this.players = playersNames.stream().map(PlayerDTO::new).collect(Collectors.toList());
    }



    public List<PlayerDTO> getPlayers() {
        return players;
    }

}
