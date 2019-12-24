import {Component, Input} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {Game} from "../../../interfaces/game/game.interface";

/**
 * The end game statistics view.
 */
@Component({
    selector: 'end-game-statistics-view',
    templateUrl: './end-game-statistics-view.component.html',
    styleUrls: ['./end-game-statistics-view.component.css']
})
export class EndGameStatisticsViewComponent {

    /**
     * The game.
     */
    @Input()
    public game: Game;



    constructor(private _translate: TranslateService) {
    }

}
