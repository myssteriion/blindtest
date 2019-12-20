import {Component, OnInit, Input} from '@angular/core';
import {THEMES, HORIZONTAL_BAR_GRAPH_SIZE, COLOR_SCHEME, GOOD_ANSWERS} from '../../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../../../tools/tools.service'
import {ComplexGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {Profile} from "../../../../interfaces/dto/profile.interface";

/**
 * The theme percentages view.
 */
@Component({
    selector: 'theme-percentages-view',
    templateUrl: './theme-percentages.component.html',
    styleUrls: ['./theme-percentages.component.css']
})
export class ThemePercentagesComponent implements OnInit {
    @Input()
    private user: Profile;

    public stackedPercentages: ComplexGraphStatisticsInterface[] = [];
    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public colorScheme = COLOR_SCHEME;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.stackedPercentages = [];
        let keys = [];
        THEMES.forEach(theme => {
            keys.push(theme.enumVal);
        });
        let listenedThemes = this.user.statistics.listenedMusics;
        let foundThemes = this.user.statistics.foundMusics;
        let typeKeys = GOOD_ANSWERS;

        keys.forEach(key => {
            let series = [];
            if (!ToolsService.isNull(foundThemes[key])) {
                typeKeys.forEach(typeKey => {
                    let byTypeValue = ToolsService.isNull(foundThemes[key][typeKey]) ? 0 : foundThemes[key][typeKey];
                    let listenedMusicsInTheme = ToolsService.isNull(listenedThemes[key]) ? 0 : parseInt(listenedThemes[key]);
                    series.push({
                        name: this._translate.instant("STATISTICS.CATEGORIES.FOUND_MUSICS_BY_THEME." + typeKey),
                        value: Math.floor(byTypeValue / listenedMusicsInTheme * 100)
                    });
                });
            }

            this.stackedPercentages.push({name: this._translate.instant("MUSIC_THEMES." + key), series: series})
        });

        this.stackedPercentages = ToolsService.sortByAlphabeticalAndNumerical(this.stackedPercentages);
    }

}
