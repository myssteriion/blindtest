import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION, THEMES} from '../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../../tools/tools.service'

/**
 * The statistics view.
 */
@Component({
    selector: 'theme-percentages-view',
    templateUrl: './theme-percentages.component.html',
    styleUrls: ['./theme-percentages.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ThemePercentagesComponent implements OnInit {
    @Input() statistics;
    @Input() colorScheme;

    stackedPercentages = [];

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.stackedPercentages = [];
        let keys = [];
        THEMES.forEach(theme => {
            keys.push(theme.enumVal);
        });
        let listenedThemes = this.statistics.listenedMusics;
        let foundThemes = this.statistics.foundMusics;
        let typeKeys = ["BOTH", "TITLE", "AUTHOR"];

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
