import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION, THEMES} from '../../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../../../tools/tools.service'
import {SimpleGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {Profile} from "../../../../interfaces/dto/profile.interface";
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from "../../../../tools/graph.constant";

/**
 * The theme listening statistics view.
 */
@Component({
    selector: 'theme-listening-view',
    templateUrl: './theme-listening.component.html',
    styleUrls: ['./theme-listening.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ThemeListeningComponent implements OnInit {
    @Input()
    private user: Profile;

    public listenedMusicsByTheme: SimpleGraphStatisticsInterface[] = [];
    public maxCount: number = 0;
    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public colorScheme = COLOR_SCHEME;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.listenedMusicsByTheme = [];
        this.maxCount = 0;
        let keys = [];

        THEMES.forEach(theme => {
            keys.push(theme.enumVal);
        });

        let listenedThemes = this.user.statistics.listenedMusics;

        keys.forEach(key => {
            this.listenedMusicsByTheme.push({
                name: this._translate.instant("THEMES." + key),
                value: ToolsService.isNull(listenedThemes[key]) ? 0 : parseInt(listenedThemes[key])
            });
            if (this.maxCount < parseInt(listenedThemes[key])) {
                this.maxCount = parseInt(listenedThemes[key]);
            }
        });

        this.listenedMusicsByTheme = ToolsService.sortByAlphabeticalAndNumerical(this.listenedMusicsByTheme);
    }

}
