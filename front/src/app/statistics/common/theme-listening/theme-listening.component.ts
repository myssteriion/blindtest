import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION, THEMES, HORIZONTAL_BAR_GRAPH_SIZE} from '../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../../tools/tools.service'

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
    @Input() statistics;
    @Input() colorScheme;

    public listenedMusicsByTheme = [];
    public maxCount = 0;
    public view = HORIZONTAL_BAR_GRAPH_SIZE;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.listenedMusicsByTheme = [];
        this.maxCount = 0;
        let keys = [];

        this.listenedMusicsByTheme = [];
        THEMES.forEach(theme => {
            keys.push(theme.enumVal);
        });

        let listenedThemes = this.statistics.listenedMusics;

        keys.forEach(key => {
            this.listenedMusicsByTheme.push({
                name: this._translate.instant("MUSIC_THEMES." + key),
                value: ToolsService.isNull(listenedThemes[key]) ? 0 : listenedThemes[key]
            });
            if (this.maxCount < listenedThemes[key]) {
                this.maxCount = listenedThemes[key];
            }
        });

        this.listenedMusicsByTheme = ToolsService.sortByAlphabeticalAndNumerical(this.listenedMusicsByTheme);
    }

}
