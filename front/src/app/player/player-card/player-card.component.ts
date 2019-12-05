import {Component, Input} from '@angular/core';
import {Player} from "../../interfaces/game/player.interface";
import {faCookieBite, faMedal, faPoo} from '@fortawesome/free-solid-svg-icons';
import {TranslateService} from '@ngx-translate/core';

/**
 * Player card.
 */
@Component({
    selector: 'player-card',
    templateUrl: './player-card.component.html',
    styleUrls: ['./player-card.component.css']
})
export class PlayerCardComponent {

    /**
     * The player.
     */
    @Input()
    private player: Player;

    /**
     * True for show rank on right, false for left.
     */
    @Input()
    private rankOnRight: boolean;

    /**
     * True to display medal, false to force to hide.
     */
    @Input()
    private displayMedal: boolean;

    faMedal = faMedal;
    faPoo = faPoo;
    faCookieBite = faCookieBite;



    constructor(private _translate: TranslateService) {
    }



    /**
     * Test if the medal must be show.
     */
    private showMedal(): boolean {
        let rank = this.player.rank;
        return this.displayMedal && (rank === Rank.FIRST || rank === Rank.SECOND || rank === Rank.THIRD);
    }

    /**
     * Test if the cookie must be show.
     */
    private showCookie(): boolean {
        let rank = this.player.rank;
        return this.displayMedal && rank === Rank.FOURTH;
    }

    /**
     * Test if the poop must be show.
     */
    private showPoop(): boolean {
        return this.displayMedal && this.player.last;
    }

    /**
     * If show/hide icon(s).
     */
    private showIcon(): boolean {
        return this.showMedal() || this.showCookie() || this.showPoop();
    }

    /**
     * Get medal style.
     */
    private getMedalStyle(): string {

        let colorStyle = "";

        switch (this.player.rank) {
            case Rank.FIRST: colorStyle = "player-card-rank-gold"; break;
            case Rank.SECOND: colorStyle = "player-card-rank-silver"; break;
            default: colorStyle = "player-card-rank-bronze";
        }

        return colorStyle;
    }

    /**
     * Get medal tooltip.
     */
    private getMedalTooltip(): string {
        return this._translate.instant("RANK." + this.player.rank);
    }

}
