import {Component, OnInit, Input} from '@angular/core';
import {DURATIONS} from '../../../../tools/constant';
import {ToolsService} from '../../../../tools/tools.service'
import {TranslateService} from "@ngx-translate/core";
import {Profile} from "../../../../interfaces/entity/profile.interface";
import {ComplexGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from "../../../../tools/graph.constant";

/**
 * The number of games played view.
 */
@Component({
    selector: 'played-games',
    templateUrl: './played-games.component.html',
    styleUrls: ['./played-games.component.css']
})
export class PlayedGamesComponent implements OnInit {

    @Input()
    private players: Profile[];

    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public results: ComplexGraphStatisticsInterface[] = [];
    public colorScheme = COLOR_SCHEME;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Calculate number games played for each game type
     */
    private calculateStatistics() {
        this.results = [];
        let keys = DURATIONS;

        this.players.forEach(player => {
            let series = [];
            keys.forEach(key => {
                series.push({
                    name: this._translate.instant('STATISTICS.CATEGORIES.BEST_SCORE.' + key),
                    value: ToolsService.isNull(player.profileStat.playedGames[key]) ? 0 : player.profileStat.playedGames[key]
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