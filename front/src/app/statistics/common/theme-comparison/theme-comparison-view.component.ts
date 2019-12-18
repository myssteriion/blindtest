import {Component, OnInit, Input} from '@angular/core';
import {COLOR_SCHEME, NUMBER_CARD_GRAPH_SIZE_SMALL, SLIDE_ANIMATION} from '../../../tools/constant';
import {ToolsService} from "../../../tools/tools.service";
import {TranslateService} from "@ngx-translate/core";

/**
 * The theme comparison view.
 */
@Component({
    selector: 'theme-comparison-view',
    templateUrl: './theme-comparison-view.component.html',
    styleUrls: ['./theme-comparison-view.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ThemeComparisonViewComponent implements OnInit {

    @Input() gameStatistics;

    public availableThemes: Theme[] = null;
    public selectedTheme: Theme = null;
    public colorScheme = COLOR_SCHEME;
    public cardResults = [];
    public view = NUMBER_CARD_GRAPH_SIZE_SMALL;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.availableThemes = this.gameStatistics.themes;
        this.selectTheme(this.availableThemes[0]);
    }

    /**
     * Select theme to display
     * @param theme
     */
    selectTheme(theme: Theme) {
        this.selectedTheme = theme;
        this.updateCardsNumber();
    }

    /**
     * Update value on card number
     */
    updateCardsNumber() {
        this.cardResults = [];
        let listenedMusics = 0;
        this.availableThemes.forEach(theme => {
            listenedMusics = ToolsService.isNull(this.gameStatistics.listenedMusics[theme]) ? listenedMusics : listenedMusics + this.gameStatistics.listenedMusics[theme];
        });
        this.cardResults.push({
            name: this._translate.instant("STATISTICS.CATEGORIES.TOTAL_LISTENED_MUSICS"),
            value: listenedMusics
        });
        this.cardResults.push({
            name: this._translate.instant("STATISTICS.CATEGORIES.LISTENED_MUSICS_BY_THEME.NUMBER_LISTENED_MUSICS_IN_THEME"),
            value: ToolsService.isNull(this.gameStatistics.listenedMusics[this.selectedTheme]) ? 0 : this.gameStatistics.listenedMusics[this.selectedTheme]
        });
    }

    /**
     * Return true if theme is selected
     * @param theme
     */
    isSelectedTheme(theme) {
        return theme === this.selectedTheme;
    }

}
