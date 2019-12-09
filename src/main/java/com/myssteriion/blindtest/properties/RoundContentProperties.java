package com.myssteriion.blindtest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The mapping with round-content.properties.
 */
@Configuration
@PropertySource("${SPRING_CONFIG_LOCATION}/round-content.properties")
public class RoundContentProperties {

    @Value("${classic.nbMusics}")
    private Integer classicNbMusics;

    @Value("${classic.nbPointWon}")
    private Integer classicNbPointWon;



    @Value("${choice.nbMusics}")
    private Integer choiceNbMusics;

    @Value("${choice.nbPointWon}")
    private Integer choiceNbPointWon;

    @Value("${choice.nbPointBonus}")
    private Integer choiceNbPointBonus;

    @Value("${choice.nbPointMalus}")
    private Integer choiceNbPointMalus;



    @Value("${lucky.nbMusics}")
    private Integer luckyNbMusics;

    @Value("${lucky.nbPointWon}")
    private Integer luckyNbPointWon;

    @Value("${lucky.nbPointBonus}")
    private Integer luckyNbPointBonus;



    @Value("${thief.nbMusics}")
    private Integer thiefNbMusics;

    @Value("${thief.nbPointWon}")
    private Integer thiefNbPointWon;

    @Value("${thief.nbPointLoose}")
    private Integer thiefNbPointLoose;



    @Value("${recovery.nbMusics}")
    private Integer recoveryNbMusics;

    @Value("${recovery.nbPointWon}")
    private Integer recoveryNbPointWon;



    /**
     * Gets classic nb musics.
     *
     * @return the classic nb musics
     */
    public Integer getClassicNbMusics() {
        return classicNbMusics;
    }

    /**
     * Gets classic nb point won.
     *
     * @return the classic nb point won
     */
    public Integer getClassicNbPointWon() {
        return classicNbPointWon;
    }

    /**
     * Gets choice nb musics.
     *
     * @return the choice nb musics
     */
    public Integer getChoiceNbMusics() {
        return choiceNbMusics;
    }

    /**
     * Gets choice nb point won.
     *
     * @return the choice nb point won
     */
    public Integer getChoiceNbPointWon() {
        return choiceNbPointWon;
    }

    /**
     * Gets choice nb point bonus.
     *
     * @return the choice nb point bonus
     */
    public Integer getChoiceNbPointBonus() {
        return choiceNbPointBonus;
    }

    /**
     * Gets choice nb point malus.
     *
     * @return the choice nb point malus
     */
    public Integer getChoiceNbPointMalus() {
        return choiceNbPointMalus;
    }

    /**
     * Gets lucky nb musics.
     *
     * @return the lucky nb musics
     */
    public Integer getLuckyNbMusics() {
        return luckyNbMusics;
    }

    /**
     * Gets lucky nb point won.
     *
     * @return the lucky nb point won
     */
    public Integer getLuckyNbPointWon() {
        return luckyNbPointWon;
    }

    /**
     * Gets lucky nb point bonus.
     *
     * @return the lucky nb point bonus
     */
    public Integer getLuckyNbPointBonus() {
        return luckyNbPointBonus;
    }

    /**
     * Gets thief nb musics.
     *
     * @return the thief nb musics
     */
    public Integer getThiefNbMusics() {
        return thiefNbMusics;
    }

    /**
     * Gets thief nb point won.
     *
     * @return the thief nb point won
     */
    public Integer getThiefNbPointWon() {
        return thiefNbPointWon;
    }

    /**
     * Gets thief nb point loose.
     *
     * @return the thief nb point loose
     */
    public Integer getThiefNbPointLoose() {
        return thiefNbPointLoose;
    }

    /**
     * Gets recoveryNbMusics.
     *
     * @return The recoveryNbMusics.
     */
    public Integer getRecoveryNbMusics() {
        return recoveryNbMusics;
    }

    /**
     * Gets recoveryNbPointWon.
     *
     * @return The recoveryNbPointWon.
     */
    public Integer getRecoveryNbPointWon() {
        return recoveryNbPointWon;
    }

}
