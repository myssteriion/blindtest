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
