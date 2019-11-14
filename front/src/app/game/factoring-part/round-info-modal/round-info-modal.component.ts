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



    private getDescription(): string {

        let params = {
            roundName: this._translate.instant("ROUND." + this.game.round + ".NAME"),
            nbMusics: this.game.roundContent.nbMusics
        };

        return this._translate.instant("ROUND." + this.game.round + ".DESCRIPTION", params);
    }

    /**
     * Close modal.
     */
    private close() {
        this._ngbActiveModal.close();
    }

}
