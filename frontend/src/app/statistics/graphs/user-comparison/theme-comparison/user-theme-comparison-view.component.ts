import { Component, Input, OnInit } from '@angular/core';
import { CommonUtilsService } from "myssteriion-utils";
import { Theme } from "../../../../interfaces/common/theme.enum";
import { Game } from "../../../../interfaces/game/game";
import { SLIDE_ANIMATION, THEMES } from '../../../../tools/constant';
import { COLOR_SCHEME } from "../../../../tools/graph.constant";

/**
 * The theme comparison view.
 */
@Component({
	selector: "user-theme-comparison-view",
	templateUrl: "./user-theme-comparison-view.component.html",
	styleUrls: ["./user-theme-comparison-view.component.css"],
	animations: [
		SLIDE_ANIMATION
	]
})

export class UserThemeComparisonViewComponent implements OnInit {
	
	@Input()
	public gameStatistics: Game;
	
	public availableThemes: Theme[] = null;
	public selectedTheme: Theme = null;
	public colorScheme = COLOR_SCHEME;
	public themes = THEMES;
	
	constructor(private _commonUtilsService: CommonUtilsService) { }
	
	ngOnInit(): void {
		this.availableThemes = [];
		THEMES.forEach(theme => {
			let themeExists = this.gameStatistics.themes.find(gameTheme => {
				return theme.enumVal === gameTheme
			});
			if (!this._commonUtilsService.isNull(themeExists)) {
				this.availableThemes.push(themeExists)
			}
		});
	}
	
	/**
	 * Update theme on event received
	 * @param theme
	 */
	public onThemeChange(theme: Theme): void {
		this.selectedTheme = theme;
	}
	
}
