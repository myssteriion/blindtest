import {Component, OnInit, Input} from '@angular/core';
import {THEMES, GOOD_ANSWERS} from '../../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {UtilsService} from '../../../../services/utils.service'
import {ComplexGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {Profile} from "../../../../interfaces/entity/profile.interface";
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from "../../../../tools/graph.constant";
import {CommonUtilsService} from "myssteriion-utils";

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

    constructor(private _translate: TranslateService,
				private _commonUtilsService: CommonUtilsService,
				private _utilsService: UtilsService) {
    }

    ngOnInit() {
        this.stackedPercentages = [];
        let keys = [];
        THEMES.forEach(theme => {
            keys.push(theme.enumVal);
        });
        let listenedThemes = this.user.profileStat.listenedMusics;
        let foundThemes = this.user.profileStat.foundMusics;
        let typeKeys = GOOD_ANSWERS;

        keys.forEach(key => {
            let series = [];
            if (!this._commonUtilsService.isNull(foundThemes[key])) {
                typeKeys.forEach(typeKey => {
                    let byTypeValue = this._commonUtilsService.isNull(foundThemes[key][typeKey]) ? 0 : foundThemes[key][typeKey];
                    let listenedMusicsInTheme = this._commonUtilsService.isNull(listenedThemes[key]) ? 0 : parseInt(listenedThemes[key]);
                    series.push({
                        name: this._translate.instant("STATISTICS.CATEGORIES.FOUND_MUSICS_BY_THEME." + typeKey),
                        value: Math.floor(byTypeValue / listenedMusicsInTheme * 100)
                    });
                });
            }

            this.stackedPercentages.push({name: this._translate.instant("THEMES." + key), series: series})
        });

        this.stackedPercentages = this._utilsService.sortByAlphabeticalAndNumerical(this.stackedPercentages);
    }

}
