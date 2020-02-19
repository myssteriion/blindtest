package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * The Choice round content.
 */
public class ChoiceContent extends AbstractRoundContent {

    /**
     * The number of bonus points.
     */
    private int nbPointBonus;

    /**
     * The number of malus points.
     */
    private int nbPointMalus;

    /**
     * Index players order.
     */
    private List<Integer> order;



    /**
     * Instantiates a new Choice content.
     *
     * @param nbMusics     the nb musics
     * @param nbPointWon   the nb point won
     * @param nbPointBonus the nb point bonus
     * @param nbPointMalus the nb point malus
     */
    public ChoiceContent(int nbMusics, int nbPointWon, int nbPointBonus, int nbPointMalus) {

        super(nbMusics, nbPointWon);
        this.nbPointBonus = Math.max(nbPointBonus, 0);
        this.nbPointMalus = Math.min(nbPointMalus, 0);
        this.order = new ArrayList<>();
    }



    /**
     * Gets nb point bonus.
     *
     * @return the nb point bonus
     */
    public int getNbPointBonus() {
        return nbPointBonus;
    }

    /**
     * Gets nb point malus.
     *
     * @return the nb point malus
     */
    public int getNbPointMalus() {
        return nbPointMalus;
    }

    /**
     * Gets order.
     *
     * @return The order.
     */
    public List<Integer> getOrder() {
        return order;
    }


    @Override
    public void prepare(Game game) {

        super.prepare(game);

        int nbPlayers = game.getPlayers().size();
        List<Integer> currentIndexPlayers = initCurrentIndexPlayers(nbPlayers);

        for (int i = 0; i < this.nbMusics; i++) {

            int indexToAdd = Tools.RANDOM.nextInt( currentIndexPlayers.size() );
            order.add( currentIndexPlayers.remove(indexToAdd) );

            if ( currentIndexPlayers.isEmpty() )
                currentIndexPlayers = initCurrentIndexPlayers(nbPlayers);
        }

        game.getPlayers().get( order.remove(0) ).setTurnToChoose(true);
    }

    /**
     * Init list with all index players.
     *
     * @param nbPlayers the number of players
     * @return A list with all index players.
     */
    private List<Integer> initCurrentIndexPlayers(int nbPlayers) {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nbPlayers; i++)
            list.add(i);

        return list;
    }

    @Override
    public Game apply(Game game, MusicResult musicResult) {

        game = super.apply(game, musicResult);

        game.getPlayers().stream()
                .filter(Player::isTurnToChoose)
                .forEach( player -> {

                    String profileName = player.getProfile().getName();

                    int point = nbPointMalus;
                    if ( musicResult.isAuthorAndTitleWinner(profileName) )
                        point = nbPointBonus*2;
                    else if ( musicResult.isAuthorWinner(profileName) || musicResult.isTitleWinner(profileName) )
                        point = nbPointBonus;

                    player.addScore(point);
                    player.setTurnToChoose(false);
                });

        if ( !isLastMusic(game) )
            game.getPlayers().get( order.remove(0) ).setTurnToChoose(true);

        return game;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointBonus=" + nbPointBonus +
                ", nbPointMalus=" + nbPointMalus +
                ", order=" + order;
    }

}
