import {Component, OnInit, Input} from '@angular/core';
import {COLOR_SCHEME, NUMBER_CARD_GRAPH_SIZE_SMALL} from '../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';

/**
 * The end game statistics view.
 */
@Component({
    selector: 'end-game-statistics-view',
    templateUrl: './end-game-statistics-view.component.html',
    styleUrls: ['./end-game-statistics-view.component.css']
})
export class EndGameStatisticsViewComponent implements OnInit {
    @Input() gameStatistics;

    public colorScheme = COLOR_SCHEME;
    public cardResults = [];
    public view = NUMBER_CARD_GRAPH_SIZE_SMALL;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.updateCardsNumber();
    }

    /**
     * Update value on card number
     */
    updateCardsNumber() {
        this.cardResults = [];
        this.cardResults.push({
            name: this._translate.instant("STATISTICS.CATEGORIES.TOTAL_LISTENED_MUSICS"),
            value: this.gameStatistics.nbMusicsPlayed
        });
    }
}
