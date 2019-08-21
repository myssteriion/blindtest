package com.myssteriion.blindtest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:round-content.properties")
public class RoundContentProperties {

    @Value("${classic.nbMusics}")
    private Integer classicNbMusics;

    @Value("${classic.nbPointWon}")
    private Integer classicNbPointWon;



    @Value("${choice.nbMusics}")
    private Integer choiceNbMusics;

    @Value("${choice.nbPointWon}")
    private Integer choiceNbPointWon;

    @Value("${choice.nbPointBonusWon}")
    private Integer choiceNbPointBonusWon;

    @Value("${choice.nbPointMalusLoose}")
    private Integer choicenNPointMalusLoose;



    @Value("${thief.nbMusics}")
    private Integer thiefNbMusics;

    @Value("${thief.nbPointWon}")
    private Integer thiefNbPointWon;

    @Value("${thief.nbPointLoose}")
    private Integer thiefNbPointLoose;



    public Integer getClassicNbMusics() {
        return classicNbMusics;
    }

    public Integer getClassicNbPointWon() {
        return classicNbPointWon;
    }

    public Integer getChoiceNbMusics() {
        return choiceNbMusics;
    }

    public Integer getChoiceNbPointWon() {
        return choiceNbPointWon;
    }

    public Integer getChoiceNbPointBonusWon() {
        return choiceNbPointBonusWon;
    }

    public Integer getChoicenNPointMalusLoose() {
        return choicenNPointMalusLoose;
    }

    public Integer getThiefNbMusics() {
        return thiefNbMusics;
    }

    public Integer getThiefNbPointWon() {
        return thiefNbPointWon;
    }

    public Integer getThiefNbPointLoose() {
        return thiefNbPointLoose;
    }

}
