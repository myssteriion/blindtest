import {Component, Input, OnInit} from '@angular/core';
import { Theme } from "../../interfaces/common/theme.enum";
import {UtilsService} from "../../services/utils.service";
import {THEMES} from "../../tools/constant";
import {Profile} from "../../interfaces/entity/profile";
import {CommonUtilsService} from "myssteriion-utils";

/**
 * The theme comparison view.
 */
@Component({
    selector: "profiles-comparison-view",
    templateUrl: "./profiles-comparison-view.component.html",
    styleUrls: ["./profiles-comparison-view.component.scss"]
})
export class ProfilesComparisonViewComponent implements OnInit {

    @Input()
    public selectedUsers: Profile[];

    public themes = THEMES;
    public availableThemes: any[] = null;
    public selectedTheme: Theme = null;
    public noThemesAvailable = true;

    constructor(private _commonUtilsService: CommonUtilsService) { }

    ngOnInit(): void {
        this.availableThemes = [];
        this.getAvailableThemes();
        this.noThemesAvailable = this.availableThemes.length === 0;
    }

    /**
     * Get all available themes
     */
    private getAvailableThemes(): void {
        let themesToFilter: any[] = [];
        this.selectedUsers.forEach(user => {
            let keys = Object.keys(user.profileStat.listenedMusics);
            themesToFilter = themesToFilter.concat(keys);
        });

        themesToFilter = themesToFilter.filter((item, index) => themesToFilter.indexOf(item) === index);
        THEMES.forEach(theme => {
            let themeExists = themesToFilter.find(themeToFilter => {
                return themeToFilter === theme.enumVal
            });

            if (!this._commonUtilsService.isNullOrEmpty(themeExists)) {
                this.availableThemes.push(themeExists);
            }
        });
    }

    /**
     * Update theme on event received
     * @param theme
     */
    public onThemeChange(theme: Theme): void {
        this.selectedTheme = theme;
    }

}
