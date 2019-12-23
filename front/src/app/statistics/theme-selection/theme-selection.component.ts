import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {THEMES} from "../../tools/constant";

/**
 * The theme selection view.
 */
@Component({
    selector: 'theme-selection',
    templateUrl: './theme-selection.component.html',
    styleUrls: ['./theme-selection.component.css']
})
export class ThemeSelectionComponent implements OnInit {

    @Input()
    public themes;

    @Output()
    private onEvent = new EventEmitter();

    public selectedTheme: Theme = null;

    constructor() {
    }

    ngOnInit(): void {
        if (this.themes.length > 0) {
            this.selectTheme(this.themes[0]);
        }
    }

    /**
     * Select theme
     * @param theme
     */
    public selectTheme(theme: Theme): void {
        this.selectedTheme = theme;
        this.onEvent.emit(theme);
    }

    /**
     * Return true if theme is selected
     * @param theme
     */
    public isSelectedTheme(theme): boolean {
        return theme === this.selectedTheme;
    }

    /**
     * Get corresponding theme values
     * @param selectedTheme
     */
    public getTheme(selectedTheme): Object {
        return THEMES.find(theme => {
            return theme.enumVal === selectedTheme;
        })
    }
}
