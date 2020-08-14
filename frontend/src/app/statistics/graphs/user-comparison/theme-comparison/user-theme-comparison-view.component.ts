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

    ngOnInit(): void {
        this.availableThemes = [];
        THEMES.forEach(theme => {
            let themeExists = this.gameStatistics.themes.find(gameTheme => {
                return theme.enumVal === gameTheme
            });
            if (!ToolsService.isNull(themeExists)) {
                this.availableThemes.push(themeExists)
            }
        });
    }

    /**
     * Update theme on event received
     * @param theme
     */
    public onThemeChange(theme): void {
        this.selectedTheme = theme;
    }

}
