package com.myssteriion.blindtest.model.common.roundcontent;

import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;

public abstract class AbstractRoundContent {

    public abstract boolean isFinished(GameDTO gameDto);

    public abstract GameDTO apply(GameDTO gameDto, MusicResultDTO musicResultDto);

}
