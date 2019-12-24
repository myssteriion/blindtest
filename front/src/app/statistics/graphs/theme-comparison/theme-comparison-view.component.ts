import {Component, Input, OnInit} from '@angular/core';
import {SLIDE_ANIMATION, THEMES} from '../../../tools/constant';
import {Game} from "../../../interfaces/game/game.interface";
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

    /**
     * The game.
     */
    @Input()
    public game: Game;

    /**
     * Themes list.
     */
    public themes: {}[];

    /**
     * Selected theme.
     */
    private selectedTheme: Theme;



    constructor() {
    }

    ngOnInit(): void {

        this.themes = [];
        THEMES.forEach(theme => {

            let index = this.game.themes.findIndex(thm => thm === theme.enumVal);

            if (index !== -1)
                this.themes.push(theme);

            if (this.themes.length === 1)
                this.selectedTheme = theme.enumVal;
        });
    }



    /**
     * Get the nb musics for the selected theme.
     */
    public getNbMusicsInTheme(): number {

        let nbMusics: number = 0;
        if ( !ToolsService.isNull(this.game.listenedMusics[this.selectedTheme]) )
            nbMusics = this.game.listenedMusics[this.selectedTheme];

        return nbMusics;
    }

}
