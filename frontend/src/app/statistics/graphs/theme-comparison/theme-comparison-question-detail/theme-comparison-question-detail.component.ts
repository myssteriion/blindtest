import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {GOOD_ANSWERS} from '../../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {UtilsService} from '../../../../services/utils.service'
import {Game} from "../../../../interfaces/game/game.interface";
import {ComplexGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {COLOR_SCHEME} from "../../../../tools/graph.constant";
import {CommonUtilsService} from "myssteriion-utils";

/**
 * The theme comparison question detail view.
 */
@Component({
    selector: 'theme-comparison-question-detail',
    templateUrl: './theme-comparison-question-detail.component.html',
    styleUrls: ['./theme-comparison-question-detail.component.css']
})
export class ThemeComparisonQuestionDetailComponent implements OnInit {

    /**
     * The game.
     */
    @Input()
    private game: Game;

    /**
     * The theme.
     */
    @Input()
    private theme: Theme;

    public stackedPercentages: ComplexGraphStatisticsInterface[] = [];

    public colorScheme = COLOR_SCHEME;



    constructor(private _translate: TranslateService,
				private _commonUtilsService: CommonUtilsService,
				private _utilsService: UtilsService) {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Detect changes on input fields.
     */
    ngOnChanges(changes: SimpleChanges) {
        this.calculateStatistics();
    }



    /**
     * Calculate each player's stats on found/listened musics with answers specification (both, title, artist)
     */
    private calculateStatistics() {
        this.stackedPercentages = [];
        this.game.players.forEach(player => {
            let series = this._commonUtilsService.isNull(player.foundMusics[this.theme]) ? [] : this.getMusicWinForPlayer(player.foundMusics[this.theme]);
            this.stackedPercentages.push({name: player.profile.name, series: series})
        });
        this.stackedPercentages = this._utilsService.sortByAlphabeticalAndNumerical(this.stackedPercentages);
    }

    /**
     * Get musics found for each player on theme
     * @param musicsForTheme
     */
    private getMusicWinForPlayer(musicsForTheme) {
        let foundMusics = [];
        let listenedMusicsInTheme = this.game.listenedMusics[this.theme];
        let typeKeys = GOOD_ANSWERS;
        typeKeys.forEach(typeKey => {
            let value = this._commonUtilsService.isNull(musicsForTheme[typeKey]) ? 0 : musicsForTheme[typeKey];
            foundMusics.push({
                name: this._translate.instant("STATISTICS.CATEGORIES.FOUND_MUSICS_BY_THEME." + typeKey),
                // value: Math.floor(value / listenedMusicsInTheme * 100)
                value: Math.floor(value)
            })
        });
        return foundMusics;
    }

    /**
     * Get the nb musics for the selected theme.
     */
    public getMaximum(): number {

        let nbMusics: number = 0;
        if ( !this._commonUtilsService.isNull(this.game.listenedMusics[this.theme]) )
            nbMusics = this.game.listenedMusics[this.theme];

        return nbMusics;
    }

}
