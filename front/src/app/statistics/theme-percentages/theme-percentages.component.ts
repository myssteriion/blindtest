import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION, THEMES} from '../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../tools/tools.service'

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
        this.getStackedPercentages();
    }

    private getStackedPercentages() {
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
                    let byTypeValue = 0;
                    if (!ToolsService.isNull(foundThemes[key][typeKey])) {
                        byTypeValue = foundThemes[key][typeKey];
                    }
                    let listenedMusicsInTheme = 0;
                    let value = 0;
                    if (!ToolsService.isNull(listenedThemes[key])) {
                        listenedMusicsInTheme = parseInt(listenedThemes[key]);
                    }
                    value = Math.floor(byTypeValue / listenedMusicsInTheme * 100);

                    series.push({
                        name: this._translate.instant("STATISTICS.CATEGORIES.FOUND_MUSICS_BY_THEME." + typeKey),
                        value: value
                    });
                });
            }

            this.stackedPercentages.push({name: this._translate.instant("MUSIC_THEMES." + key), series: series})
        });

        this.stackedPercentages.sort(function (a, b) {
            let textA = a.name.toUpperCase();
            let textB = b.name.toUpperCase();
            return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
        });
    }

}
