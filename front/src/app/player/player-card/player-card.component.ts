import {Component, Input} from '@angular/core';
import {Player} from "../../interfaces/game/player.interface";
import {faCookieBite, faMedal, faPoo} from '@fortawesome/free-solid-svg-icons';
import {TranslateService} from '@ngx-translate/core';
import {RANK_ICON_ANIMATION} from "../../tools/constant";

/**
 * Player card.
 */
@Component({
    selector: 'player-card',
    templateUrl: './player-card.component.html',
    styleUrls: ['./player-card.component.css'],
    animations: [
        RANK_ICON_ANIMATION
    ]
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
     * If the 1st medal must be show.
     */
    private showFirstMedal(): boolean {
        return this.displayMedal && this.player.rank === Rank.FIRST;
    }

    /**
     * If the 1st medal must be show.
     */
    private showSecondMedal(): boolean {
        return this.displayMedal && this.player.rank === Rank.SECOND;
    }

    /**
     * If the 1st medal must be show.
     */
    private showThirdMedal(): boolean {
        return this.displayMedal && this.player.rank === Rank.THIRD;
    }

    /**
     * Test if the cookie must be show.
     */
    private showCookie(): boolean {
        return this.displayMedal && this.player.rank === Rank.FOURTH;
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
        return this.showFirstMedal() || this.showSecondMedal() || this.showThirdMedal() || this.showCookie() || this.showPoop();
    }

    /**
     * Get player.
     */
    public getPlayer(): Player {
        return this.player;
    }

    /**
     * Update player.
     *
     * @param player tht player
     */
    public updatePLayer(player: Player): void {

        this.player.rank = player.rank;
        this.player.last = player.last;
        this.player.turnToChoose = player.turnToChoose;

        if (this.player.score !== player.score)
            this.player.score = player.score;
    }

}
