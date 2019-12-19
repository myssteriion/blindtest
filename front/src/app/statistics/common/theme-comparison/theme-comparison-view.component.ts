import {Component, OnInit, Input} from '@angular/core';
import {COLOR_SCHEME, NUMBER_CARD_GRAPH_SIZE_SMALL, SLIDE_ANIMATION} from '../../../tools/constant';
import {ToolsService} from "../../../tools/tools.service";

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
    public view = NUMBER_CARD_GRAPH_SIZE_SMALL;
    public listenedMusics = 0;

    constructor() {
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
        this.listenedMusics = ToolsService.isNull(this.gameStatistics.listenedMusics[this.selectedTheme]) ? 0 : this.gameStatistics.listenedMusics[this.selectedTheme];
    }

    /**
     * Return true if theme is selected
     * @param theme
     */
    isSelectedTheme(theme) {
        return theme === this.selectedTheme;
    }

}
