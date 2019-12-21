import {Component, OnInit, Input} from '@angular/core';
import {ToolsService} from "../../tools/tools.service";
import {THEMES} from "../../tools/constant";
import {Profile} from "../../interfaces/dto/profile.interface";
import {COLOR_SCHEME} from "../../tools/graph.constant";

/**
 * The theme comparison view.
 */
@Component({
    selector: 'profiles-comparison-view',
    templateUrl: './profiles-comparison-view.component.html',
    styleUrls: ['./profiles-comparison-view.component.css']
})
export class ProfilesComparisonViewComponent implements OnInit {

    @Input()
    public selectedUsers: Profile[];

    public themes = THEMES;
    public availableThemes = null;
    public selectedTheme: Theme = null;
    public noThemesAvailable = true;

    constructor() {
    }

    ngOnInit() {
        this.availableThemes = [];
        this.getAvailableThemes();

        if (this.availableThemes.length > 0) {
            this.selectedTheme = this.availableThemes[0];
            this.noThemesAvailable = false;
        } else {
            this.noThemesAvailable = true;
        }
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
     * Get all available themes
     */
    private getAvailableThemes() {
        // TODO : Compare first in order to reset or not -> Reset available themes ?
        this.selectedUsers.forEach(user => {
            let keys = Object.keys(user.statistics.listenedMusics);
            keys.forEach(key => {
                let themeAlreadyAdded = this.availableThemes.find(theme => theme === key);
                if (ToolsService.isNull(themeAlreadyAdded)) {
                    this.availableThemes.push(key);
                }
            });
            this.availableThemes = ToolsService.sortByAlphabeticalAndNumericalThemes(this.availableThemes);
        });
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
