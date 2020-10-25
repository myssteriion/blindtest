import {Component, Input, OnInit} from '@angular/core';
import {ToolsService} from "../../tools/tools.service";
import {THEMES} from "../../tools/constant";
import {Profile} from "../../interfaces/entity/profile.interface";

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
        let themesToFilter = [];
        this.selectedUsers.forEach(user => {
            let keys = Object.keys(user.profileStat.listenedMusics);
            themesToFilter = themesToFilter.concat(keys);
        });

        themesToFilter = themesToFilter.filter((item, index) => themesToFilter.indexOf(item) === index);
        THEMES.forEach(theme => {
            let themeExists = themesToFilter.find(themeToFilter => {
                return themeToFilter === theme.enumVal
            });

            if (!ToolsService.isNull(themeExists)) {
                this.availableThemes.push(themeExists);
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
