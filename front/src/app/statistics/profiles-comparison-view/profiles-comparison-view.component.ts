import {Component, OnInit, Input} from '@angular/core';
import {ToolsService} from "../../tools/tools.service";
import {COLOR_SCHEME} from "../../tools/constant";

/**
 * The theme comparison view.
 */
@Component({
    selector: 'profiles-comparison-view',
    templateUrl: './profiles-comparison-view.component.html',
    styleUrls: ['./profiles-comparison-view.component.css']
})
export class ProfilesComparisonViewComponent implements OnInit {

    @Input() selectedUsers;

    public availableThemes = null;
    public selectedTheme = null;
    public noThemesAvailable = true;
    public colorScheme = COLOR_SCHEME;

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
    selectTheme(theme: Theme) {
        this.selectedTheme = theme;
    }

    /**
     * Return true if theme is selected
     * @param theme
     */
    isSelectedTheme(theme) {
        return theme === this.selectedTheme;
    }

    /**
     * Get all available themes
     */
    getAvailableThemes() {
        // TODO : Compare first in order to reset or not -> Reset available themes ?
        this.selectedUsers.forEach(user => {
            let keys = Object.keys(user.statistics.listenedMusics);
            keys.forEach(key => {
                let themeAlreadyAdded = this.availableThemes.find(theme => theme === key);
                if (ToolsService.isNull(themeAlreadyAdded)) {
                    this.availableThemes.push(key);
                }
            })
        });
    }

}
