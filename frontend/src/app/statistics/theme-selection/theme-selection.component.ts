import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Theme } from "../../interfaces/common/theme.enum";
import { THEMES } from "../../tools/constant";

/**
 * The theme selection view.
 */
@Component({
	selector: "theme-selection",
	templateUrl: "./theme-selection.component.html",
	styleUrls: ["./theme-selection.component.scss"]
})
export class ThemeSelectionComponent implements OnInit {
	
	@Input()
	public themes: Theme[];
	
	@Output()
	private onThemeChange = new EventEmitter();
	
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
		this.onThemeChange.emit(theme);
	}
	
	/**
	 * Return true if theme is selected
	 * @param theme
	 */
	public isSelectedTheme(theme: Theme): boolean {
		return theme === this.selectedTheme;
	}
	
	/**
	 * Get corresponding theme values
	 * @param selectedTheme
	 */
	public getTheme(selectedTheme: Theme): Object {
		return THEMES.find(theme => {
			return theme.enumVal === selectedTheme;
		})
	}
}
