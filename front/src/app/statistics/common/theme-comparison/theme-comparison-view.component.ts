import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION, THEMES} from '../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';

/**
 * The theme comparison view.
 */
@Component({
    selector: 'theme-comparison-view',
    templateUrl: './theme-comparison-view.component.html',
    styleUrls: ['./theme-comparison-view.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ThemeComparisonViewComponent implements OnInit {

    @Input() gameStatistics;

    public availableThemes: Theme[] = null;
    public selectedTheme: Theme = null;

    public colorScheme = {
        domain: ['#a8385d', '#7aa3e5', '#a27ea8', '#aae3f5', '#adcded', '#a95963', '#8796c0', '#7ed3ed', '#50abcc', '#ad6886']
    };

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.availableThemes = this.gameStatistics.themes;
    }

    selectTheme(theme: Theme) {
        this.selectedTheme = theme;
    }

    isSelectedTheme(theme) {
        return theme === this.selectedTheme;
    }

}
