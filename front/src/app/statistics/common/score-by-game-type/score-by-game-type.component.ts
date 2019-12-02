import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION} from '../../../tools/constant';
import {ToolsService} from '../../../tools/tools.service'
import {TranslateService} from "@ngx-translate/core";

/**
 * The scores by game type view.
 */
@Component({
    selector: 'score-by-game-type',
    templateUrl: './score-by-game-type.component.html',
    styleUrls: ['./score-by-game-type.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ScoreByGameTypeComponent implements OnInit {

    @Input() statistics;
    @Input() colorScheme;

    public cardResults = [];

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    private calculateStatistics() {
        let keys = ["SHORT", "NORMAL", "LONG"];
        keys.forEach(key => {
            this.cardResults.push({
                name: this._translate.instant('STATISTICS.CATEGORIES.BEST_SCORE.' + key),
                value: ToolsService.isNull(this.statistics.bestScores[key]) ? 0 : this.statistics.bestScores[key]
            });
        });
    }

}
