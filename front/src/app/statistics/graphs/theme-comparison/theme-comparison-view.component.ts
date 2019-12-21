import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION, THEMES} from '../../../tools/constant';
import {ToolsService} from "../../../tools/tools.service";
import {Game} from "../../../interfaces/game/game.interface";
import {NUMBER_CARD_GRAPH_SIZE_SMALL} from "../../../tools/graph.constant";

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

    @Input()
    public gameStatistics: Game;

    public availableThemes: Theme[] = null;
    public selectedTheme: Theme = null;
    public view = NUMBER_CARD_GRAPH_SIZE_SMALL;
    public listenedMusics: number = 0;
    public themes = THEMES;

    constructor() {
    }

    ngOnInit() {
        this.availableThemes = this.gameStatistics.themes;
        this.availableThemes = ToolsService.sortByAlphabeticalAndNumericalThemes(this.availableThemes);
        this.selectTheme(this.availableThemes[0]);
    }

    /**
     * Select theme to display
     * @param theme
     */
    public selectTheme(theme: Theme) {
        this.selectedTheme = theme;
        this.listenedMusics = ToolsService.isNull(this.gameStatistics.listenedMusics[this.selectedTheme]) ? 0 : this.gameStatistics.listenedMusics[this.selectedTheme];
    }

    /**
     * Return true if theme is selected
     * @param theme
     */
    public isSelectedTheme(theme) {
        return theme === this.selectedTheme;
    }

    /**
     * Get corresponding theme values
     * @param selectedTheme
     */
    public getTheme(selectedTheme) {
        return this.themes.find(theme => {
            return theme.enumVal === selectedTheme;
        })
    }

}
