import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Game} from "../../../interfaces/game/game.interface";
import {TranslateService} from '@ngx-translate/core';
import {LuckyContent} from "../../../interfaces/common/roundcontent/impl/lucky.content";
import {FriendshipContent} from "../../../interfaces/common/roundcontent/impl/friendship.content";

/**
 * The round info modal.
 */
@Component({
    selector: 'round-info-modal',
    templateUrl: './round-info-modal.component.html',
    styleUrls: ['./round-info-modal.component.css']
})
export class RoundInfoModalComponent {

    /**
     * The game.
     */
    @Input()
    public game: Game;



    constructor(private _ngbActiveModal: NgbActiveModal,
                private _translate: TranslateService) {}



    /**
     * Gets the round description.
     */
    public getDescription(): string {

        let params = {
            roundName: this._translate.instant("ROUND." + this.game.round + ".NAME"),
            nbMusics: this.game.roundContent.nbMusics,
            nbPlayers: undefined,
            nbTeams: undefined
        };

        if (this.game.round === Round.LUCKY)
            params.nbPlayers = (<LuckyContent>this.game.roundContent).nbPlayers;

        if (this.game.round === Round.FRIENDSHIP)
            params.nbTeams = (<FriendshipContent>this.game.roundContent).nbTeams;

        return this._translate.instant("ROUND." + this.game.round + ".DESCRIPTION", params);
    }

    /**
     * Gets nb point won.
     */
    public getNpPointWon(): string {

        let npPointWon = this.game.roundContent.nbPointWon.toString();

        if (this.game.round === Round.RECOVERY)
            npPointWon += " x " + this._translate.instant("COMMON.RANK");

        return npPointWon;
    }

    /**
     * If lose point part is showed.
     */
    public showNpPointLose(): boolean {
        return this.game.round === Round.THIEF;
    }

    /**
     * If bonus point part is showed.
     */
    public showNpPointBonus(): boolean {
        return this.game.round === Round.CHOICE || this.game.round === Round.LUCKY;
    }

    /**
     * If malus point part is showed.
     */
    public showNpPointMalus(): boolean {
        return this.game.round === Round.CHOICE;
    }

    /**
     * Close modal.
     */
    public close(): void {
        this._ngbActiveModal.close();
    }

}
