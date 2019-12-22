import {Component, OnInit, Input} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {Game} from "../../../interfaces/game/game.interface";
import {SimpleGraphStatisticsInterface} from "../../../interfaces/common/graph.interface";

/**
 * The end game statistics view.
 */
@Component({
    selector: 'end-game-statistics-view',
    templateUrl: './end-game-statistics-view.component.html',
    styleUrls: ['./end-game-statistics-view.component.css']
})
export class EndGameStatisticsViewComponent implements OnInit {
    @Input()
    public gameStatistics: Game;

    public cardResults: SimpleGraphStatisticsInterface[] = [];

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.updateCardsNumber();
    }

    /**
     * Update value on card number
     */
    private updateCardsNumber() {
        this.cardResults = [];
        this.cardResults.push({
            name: this._translate.instant("STATISTICS.CATEGORIES.TOTAL_LISTENED_MUSICS"),
            value: this.gameStatistics.nbMusicsPlayed
        });
    }
}
