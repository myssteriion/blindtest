import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Game} from "../../../interfaces/game/game.interface";
import {TranslateService} from '@ngx-translate/core';

/**
 * The round info modal.
 */
@Component({
    selector: 'round-info-modal',
    templateUrl: './round-info-modal.component.html',
    styleUrls: ['./round-info-modal.component.css']
})
export class RoundInfoModalComponent implements OnInit {

    /**
     * The game.
     */
    @Input()
    private game: Game;



    constructor(private _ngbActiveModal: NgbActiveModal,
                private _translate: TranslateService) {}

    ngOnInit() {
    }


    /**
     * Gets the round description.
     */
    private getDescription(): string {

        let params = {
            roundName: this._translate.instant("ROUND." + this.game.round + ".NAME"),
            nbMusics: this.game.roundContent.nbMusics
        };

        if (this.game.round === Round.CHOICE)
            params.nbMusics = params.nbMusics / this.game.players.length;

        return this._translate.instant("ROUND." + this.game.round + ".DESCRIPTION", params);
    }

    /**
     * If lose point part is showed.
     */
    private showNpPointLose(): boolean {
        return this.game.round === Round.THIEF;
    }

    /**
     * If bonus point part is showed.
     */
    private showNpPointBonus(): boolean {
        return this.game.round === Round.CHOICE;
    }

    /**
     * If malus point part is showed.
     */
    private showNpPointMalus(): boolean {
        return this.game.round === Round.CHOICE;
    }

    /**
     * Close modal.
     */
    private close() {
        this._ngbActiveModal.close();
    }

}
