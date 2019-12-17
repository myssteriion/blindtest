import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../../../../tools/tools.service'
import {HORIZONTAL_STACKED_BAR_GRAPH_SIZE} from '../../../../../tools/constant'

/**
 * The user theme comparison question detail view.
 */
@Component({
    selector: 'user-theme-comparison-question-detail',
    templateUrl: './user-theme-comparison-question-detail.component.html',
    styleUrls: ['./user-theme-comparison-question-detail.component.css']
})

export class UserThemeComparisonQuestionDetailComponent implements OnInit {
    @Input() theme: Theme;
    @Input() statistics;
    @Input() colorScheme;

    stackedPercentages = [];
    public view = HORIZONTAL_STACKED_BAR_GRAPH_SIZE;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    // Detect changes on input fields
    ngOnChanges(changes: SimpleChanges) {
        this.calculateStatistics();
    }

    /**
     * Calculate each player's stats on found/listened musics with answers specification (both, title, artist)
     */
    calculateStatistics() {
        this.stackedPercentages = [];
        this.statistics.forEach(player => {
            let series = ToolsService.isNull(player.statistics.foundMusics[this.theme]) ? [] : this.getMusicWinForPlayer(player.statistics);
            this.stackedPercentages.push({name: player.name, series: series})
        });
        this.stackedPercentages = ToolsService.sortByAlphabeticalAndNumerical(this.stackedPercentages);
    }

    /**
     * Get musics found for player
     * @param playerStats
     */
    getMusicWinForPlayer(playerStats) {
        let foundMusics = [];
        let listenedMusicsInTheme = playerStats.listenedMusics[this.theme];
        let typeKeys = ["BOTH", "TITLE", "AUTHOR"];
        typeKeys.forEach(typeKey => {
            let value = ToolsService.isNull(playerStats.foundMusics[this.theme][typeKey]) ? 0 : playerStats.foundMusics[this.theme][typeKey];
            foundMusics.push({
                name: this._translate.instant("STATISTICS.CATEGORIES.FOUND_MUSICS_BY_THEME." + typeKey),
                value: Math.floor(value / listenedMusicsInTheme * 100)
            })
        });
        return foundMusics;
    }
}
