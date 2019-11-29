import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import {SLIDE_ANIMATION} from '../../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../../../tools/tools.service'

/**
 * The theme comparison question detail view.
 */
@Component({
    selector: 'theme-comparison-question-detail',
    templateUrl: './theme-comparison-question-detail.component.html',
    styleUrls: ['./theme-comparison-question-detail.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ThemeComparisonQuestionDetailComponent implements OnInit {
    @Input() theme: Theme;
    @Input() statistics;
    @Input() colorScheme;

    stackedPercentages = [];

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    // Detect changes on input fields
    ngOnChanges(changes: SimpleChanges) {
        this.calculateStatistics();
        // You can also use categoryId.previousValue and
        // categoryId.firstChange for comparing old and new values
    }

    /**
     * Calculate each player's stats on found/listened musics with answers specification (both, title, artist)
     */
    calculateStatistics() {
        this.stackedPercentages = [];
        this.statistics.players.forEach(player => {
            let series = ToolsService.isNull(player.foundMusics[this.theme]) ? [] : this.getMusicWinForPlayer(player.foundMusics[this.theme]);
            this.stackedPercentages.push({name: player.profile.name, series: series})
        });
        this.stackedPercentages = ToolsService.sortByAlphabeticalAndNumerical(this.stackedPercentages);
    }

    getMusicWinForPlayer(musicsForTheme) {
        let foundMusics = [];
        let listenedMusicsInTheme = this.statistics.listenedMusics[this.theme];
        let typeKeys = ["BOTH", "TITLE", "AUTHOR"];
        typeKeys.forEach(typeKey => {
            let value = ToolsService.isNull(musicsForTheme[typeKey]) ? 0 : musicsForTheme[typeKey];
            foundMusics.push({
                name: this._translate.instant("STATISTICS.CATEGORIES.FOUND_MUSICS_BY_THEME." + typeKey),
                value: Math.floor(value / listenedMusicsInTheme * 100)
            })
        });
        return foundMusics;
    }
}
