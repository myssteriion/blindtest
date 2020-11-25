import {Component, OnInit, Input} from '@angular/core';
import {DURATIONS} from '../../../../tools/constant';
import {UtilsService} from '../../../../tools/utils.service'
import {TranslateService} from "@ngx-translate/core";
import {Profile} from "../../../../interfaces/entity/profile.interface";
import {ComplexGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from "../../../../tools/graph.constant";
import {CommonUtilsService} from "myssteriion-utils";

/**
 * The scores by game type view.
 */
@Component({
    selector: 'score-by-game-type',
    templateUrl: './score-by-game-type.component.html',
    styleUrls: ['./score-by-game-type.component.css']
})
export class ScoreByGameTypeComponent implements OnInit {

    @Input()
    private players: Profile[];

    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public results: ComplexGraphStatisticsInterface[] = [];
    public colorScheme = COLOR_SCHEME;

    constructor(private _translate: TranslateService,
				private _commonUtilsService: CommonUtilsService,
				private _utilsService: UtilsService) { }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Calculate statistics for each game type
     */
    private calculateStatistics() {
        this.results = [];
        let keys = DURATIONS;

        this.players.forEach(player => {
            let series = [];
            keys.forEach(key => {
                series.push({
                    name: this._translate.instant('STATISTICS.CATEGORIES.BEST_SCORE.' + key),
                    value: this._commonUtilsService.isNull(player.profileStat.bestScores[key]) ? 0 : player.profileStat.bestScores[key]
                });
            });

            this.results.push({
                name: player.name,
                series: series
            })
        });
        this.results = this._utilsService.sortByAlphabeticalAndNumerical(this.results);
    }

}
