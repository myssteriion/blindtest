import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CommonUtilsService } from "myssteriion-utils";
import { ComplexGraphStatisticsInterface } from "../../../../../interfaces/common/graph.interface";
import { Theme } from "../../../../../interfaces/common/theme.enum";
import { Profile } from "../../../../../interfaces/entity/profile";
import { UtilsService } from '../../../../../services/utils.service'
import { COLOR_SCHEME, HORIZONTAL_STACKED_BAR_GRAPH_SIZE } from "../../../../../tools/graph.constant";

/**
 * The user theme comparison question detail view.
 */
@Component({
	selector: "user-theme-comparison-question-detail",
	templateUrl: "./user-theme-comparison-question-detail.component.html",
	styleUrls: ["./user-theme-comparison-question-detail.component.scss"]
})

export class UserThemeComparisonQuestionDetailComponent implements OnInit {
	@Input()
	private theme: Theme;
	@Input()
	public players: Profile[];
	
	public stackedPercentages: ComplexGraphStatisticsInterface[] = [];
	public view = HORIZONTAL_STACKED_BAR_GRAPH_SIZE;
	public colorScheme = COLOR_SCHEME;
	
	constructor(private _translate: TranslateService,
				private _commonUtilsService: CommonUtilsService,
				private _utilsService: UtilsService) { }
	
	ngOnInit() {
		this.calculateStatistics();
	}
	
	// Detect changes on input fields
	ngOnChanges(changes: SimpleChanges) {
		this.calculateStatistics();
	}
	
	/**
	 * Calculate each player's stats on found/listened musics with answers specification (both, title, artist)
	 */
	private calculateStatistics() {
		this.stackedPercentages = [];
		// this.players.forEach(player => {
		//     let series = this._commonUtilsService.isNull(player.profileStat.foundMusics[this.theme]) ? [] : this.getMusicWinForPlayer(player.profileStat);
		//     this.stackedPercentages.push({name: player.name, series: series})
		// });
		this.stackedPercentages = this._utilsService.sortByAlphabeticalAndNumerical(this.stackedPercentages);
	}
	
	/**
	 * Get musics found for player
	 * @param playerStats
	 */
	private getMusicWinForPlayer(playerStats: any): any[] {
		// let foundMusics = [];
		// let listenedMusicsInTheme = playerStats.listenedMusics[this.theme];
		// let typeKeys = GOOD_ANSWERS;
		// typeKeys.forEach(typeKey => {
		//     let value = this._commonUtilsService.isNull(playerStats.foundMusics[this.theme][typeKey]) ? 0 : playerStats.foundMusics[this.theme][typeKey];
		//     foundMusics.push({
		//         name: this._translate.instant("STATISTICS.CATEGORIES.FOUND_MUSICS_BY_THEME." + typeKey),
		//         value: Math.floor(value / listenedMusicsInTheme * 100)
		//     })
		// });
		// return foundMusics;
		return [];
	}
}
