import {Component, Input, OnInit} from '@angular/core';
import {Player} from "../../interfaces/game/player.interface";
import {faCookieBite, faMedal, faPoo, faUserFriends} from '@fortawesome/free-solid-svg-icons';
import {TranslateService} from '@ngx-translate/core';
import {ADD_SCORE_ANIMATION, RANK_ICON_ANIMATION} from "../../tools/constant";
import {ToolsService} from "../../tools/tools.service";

/**
 * Player card.
 */
@Component({
    selector: 'player-card',
    templateUrl: './player-card.component.html',
    styleUrls: ['./player-card.component.css'],
    animations: [
        RANK_ICON_ANIMATION, ADD_SCORE_ANIMATION
    ]
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

    /**
     * True to display medal, false to force to hide.
     */
    @Input()
    private displayMedal: boolean;

    /**
     * If show/hide add score.
     */
    private showAddScore: boolean;

    /**
     * Score to add.
     */
    private scoreToAdd: number;

    private faMedal = faMedal;
    private faPoo = faPoo;
    private faCookieBite = faCookieBite;
    private faUserFriends = faUserFriends;



    constructor(private _translate: TranslateService) {
    }

    ngOnInit(): void {
        this.showAddScore = false;
        this.scoreToAdd = 0;
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
     * Test ig the team must be show.
     */
    private showTeam(): boolean {
        return this.displayMedal && this.player.teamNumber != -1;
    }

    /**
     * Add css color.
     */
    private getTeamClass(): string {
        return "player-card-team-" + this.player.teamNumber;
    }

    /**
     * If show/hide icon(s).
     */
    public showIcon(): boolean {
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
    public async updatePLayer(player: Player): Promise<void> {

        this.player.rank = player.rank;
        this.player.teamNumber = player.teamNumber;
        this.player.last = player.last;
        this.player.turnToChoose = player.turnToChoose;

        if (this.player.score !== player.score) {

            this.scoreToAdd = player.score - this.player.score;
            this.showAddScore = true;

            this.player.score = player.score;
            await ToolsService.sleep(100);
            this.showAddScore = false;
        }
    }

}
