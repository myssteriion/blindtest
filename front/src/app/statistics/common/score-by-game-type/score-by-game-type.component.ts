import {Component, OnInit, Input} from '@angular/core';
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from '../../../tools/constant';
import {ToolsService} from '../../../tools/tools.service'
import {TranslateService} from "@ngx-translate/core";

/**
 * The scores by game type view.
 */
@Component({
    selector: 'score-by-game-type',
    templateUrl: './score-by-game-type.component.html',
    styleUrls: ['./score-by-game-type.component.css']
})
export class ScoreByGameTypeComponent implements OnInit {

    @Input() statistics;

    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public results = [];
    public colorScheme = COLOR_SCHEME;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Calculate statistics for each game type
     */
    private calculateStatistics() {
        this.results = [];
        let keys = ["SHORT", "NORMAL", "LONG"];

        this.statistics.forEach(player => {
            let series = [];
            keys.forEach(key => {
                series.push({
                    name: this._translate.instant('STATISTICS.CATEGORIES.BEST_SCORE.' + key),
                    value: ToolsService.isNull(player.statistics.bestScores[key]) ? 0 : player.statistics.bestScores[key]
                });
            });

            this.results.push({
                name: player.name,
                series: series
            })
        });
        this.results = ToolsService.sortByAlphabeticalAndNumerical(this.results);
    }

}
