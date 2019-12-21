import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../../../tools/tools.service'
import {Profile} from "../../../../interfaces/dto/profile.interface";
import {ComplexGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {COLOR_SCHEME, HORIZONTAL_STACKED_BAR_GRAPH_SIZE} from "../../../../tools/graph.constant";

/**
 * The user rank comparison view.
 */
@Component({
    selector: 'user-rank-comparison',
    templateUrl: './user-rank-comparison.component.html',
    styleUrls: ['./user-rank-comparison.component.css']
})

export class UserRankComparisonComponent implements OnInit {

    @Input()
    private players: Profile[];

    public rankResults: ComplexGraphStatisticsInterface[] = [];
    public view = HORIZONTAL_STACKED_BAR_GRAPH_SIZE;
    public colorScheme = COLOR_SCHEME;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.calculateStatistics()
    }

    // Detect changes on input fields
    ngOnChanges(changes: SimpleChanges) {
        this.calculateStatistics();
    }

    /**
     * Calculate each player's stats on found/listened musics with answers specification (both, title, artist)
     */
    private calculateStatistics() {
        this.rankResults = [];
        this.players.forEach(player => {
            let series = ToolsService.isNull(player.statistics.wonGames) ? [] : this.getRanksForPlayer(player.statistics.wonGames);
            this.rankResults.push({name: player.name, series: series})
        });
        this.rankResults = ToolsService.sortByAlphabeticalAndNumerical(this.rankResults);
    }

    /**
     * Get ranks for player
     * @param playerStats
     */
    private getRanksForPlayer(playerStats) {
        let userRanks = [];
        let rankValues = ["FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH", "SIXTH", "SEVENTH", "EIGHTH", "NINTH", "TENTH", "ELEVENTH", "TWELFTH"];
        rankValues.forEach(rank => {
            userRanks.push({
                name: this._translate.instant("STATISTICS.CATEGORIES.RANK_OCCUPIED." + rank),
                value: ToolsService.isNull(playerStats[rank]) ? 0 : playerStats[rank]
            });
        });
        return userRanks;
    }

}
