import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION, THEMES} from '../../../../tools/constant';
import {Game} from "../../../../interfaces/game/game.interface";
import {ToolsService} from "../../../../tools/tools.service";
import {COLOR_SCHEME} from "../../../../tools/graph.constant";

/**
 * The theme comparison view.
 */
@Component({
    selector: 'user-theme-comparison-view',
    templateUrl: './user-theme-comparison-view.component.html',
    styleUrls: ['./user-theme-comparison-view.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})

export class UserThemeComparisonViewComponent implements OnInit {

    @Input()
    public gameStatistics: Game;

    public availableThemes: Theme[] = null;
    public selectedTheme: Theme = null;
    public colorScheme = COLOR_SCHEME;
    public themes = THEMES;

    constructor() {
    }

    ngOnInit() {
        this.availableThemes = this.gameStatistics.themes;
        this.availableThemes = ToolsService.sortByAlphabeticalAndNumericalThemes(this.availableThemes);
        this.selectedTheme = this.availableThemes[0];
    }

    /**
     * Select theme
     * @param theme
     */
    public selectTheme(theme: Theme) {
        this.selectedTheme = theme;
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
