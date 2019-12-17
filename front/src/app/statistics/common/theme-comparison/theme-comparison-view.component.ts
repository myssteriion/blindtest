import {Component, OnInit, Input} from '@angular/core';
import {COLOR_SCHEME, SLIDE_ANIMATION} from '../../../tools/constant';

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
    public colorScheme = COLOR_SCHEME;

    constructor() {
    }

    ngOnInit() {
        this.availableThemes = this.gameStatistics.themes;
        this.selectedTheme = this.availableThemes[0];
    }

    /**
     * Select theme to display
     * @param theme
     */
    selectTheme(theme: Theme) {
        this.selectedTheme = theme;
    }

    /**
     * Return true if theme is selected
     * @param theme
     */
    isSelectedTheme(theme) {
        return theme === this.selectedTheme;
    }

}
