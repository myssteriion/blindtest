import {Component, Input, OnInit} from '@angular/core';
import {Player} from "../../interfaces/game/player.interface";
import {faMedal} from '@fortawesome/free-solid-svg-icons';

/**
 * Player card.
 */
@Component({
    selector: 'player-card',
    templateUrl: './player-card.component.html',
    styleUrls: ['./player-card.component.css']
})
export class PlayerCardComponent implements OnInit {

    /**
     * The player.
     */
    @Input()
    public player: Player;

    /**
     * True for show rank on right, false for left.
     */
    @Input()
    public rankOnRight: boolean;

    faMedal = faMedal;



    constructor() {
    }

    ngOnInit() {
    }



    /**
     * Test if the medal must be show.
     */
    public showMedal() {

        let rank = this.player.rank;
        return rank === Rank.FIRST || rank === Rank.SECOND || rank === Rank.THIRD || rank === Rank.FOURTH;
    }

    /**
     * Get medal style.
     */
    public getMedalStyle() {

        let colorStyle = "";

        switch (this.player.rank) {
            case Rank.FIRST: colorStyle = "player-card-rank-gold"; break;
            case Rank.SECOND: colorStyle = "player-card-rank-silver"; break;
            case Rank.THIRD: colorStyle = "player-card-rank-bronze"; break;
            default: colorStyle = "player-card-rank-chocolate";
        }

        return colorStyle;
    }

}
