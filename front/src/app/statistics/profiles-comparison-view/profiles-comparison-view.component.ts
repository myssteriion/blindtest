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

    ngOnInit(): void {
        this.availableThemes = [];
        this.getAvailableThemes();
        this.noThemesAvailable = this.availableThemes.length === 0;
    }

    /**
     * Get all available themes
     */
    private getAvailableThemes(): void {
        this.selectedUsers.forEach(user => {
            let keys = Object.keys(user.statistics.listenedMusics);
            THEMES.forEach(theme => {
                let userHasTheme = keys.find(userTheme => {
                    return userTheme === theme.enumVal
                });
                if (!ToolsService.isNull(userHasTheme)) {
                    let themeAlreadyAdded = this.availableThemes.find(availableTheme => {
                        return availableTheme === theme.enumVal
                    });
                    if (ToolsService.isNull(themeAlreadyAdded)) {
                        this.availableThemes.push(theme.enumVal);
                    }
                }
            })
        })
    }

    /**
     * Update theme on event received
     * @param theme
     */
    public onThemeChange(theme): void {
        this.selectedTheme = theme;
    }

}
