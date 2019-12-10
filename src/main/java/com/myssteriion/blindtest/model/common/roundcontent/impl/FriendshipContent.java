package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.game.Game;

/**
 * The Friendship round content.
 */
public class FriendshipContent extends AbstractRoundContent {

    /**
     * Teams number.
     */
    private int nbTeams;



    /**
     * Instantiates a new Friendship content.
     *
     * @param nbMusics   the nb musics
     * @param nbPointWon the nb point won
     */
    public FriendshipContent(int nbMusics, int nbPointWon) {
        super(nbMusics, nbPointWon);
    }



    /**
     * Gets nbTeams.
     *
     * @return The nbTeams.
     */
    public int getNbTeams() {
        return nbTeams;
    }



    @Override
    public void prepare(Game game) {

        super.prepare(game);

        int nbPlayers = game.getPlayers().size();
        nbTeams = (nbPlayers % 2 == 0) ? nbPlayers / 2 : (nbPlayers / 2) + 1;

        for (int i = 0; i < nbTeams; i++) {

            int iBis = i;

            game.getPlayers().stream()
                    .filter( player -> player.getTeamNumber() == -1 )
                    .limit(2)
                    .forEach( player -> player.setTeamNumber(iBis) );
        }
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbTeams=" + nbTeams;
    }

}
