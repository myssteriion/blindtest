import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { CommonUtilsService } from "myssteriion-utils";
import { SimpleGraphStatisticsInterface } from "../../../../../interfaces/common/graph.interface";
import { Theme } from "../../../../../interfaces/common/theme.enum";
import { Profile } from "../../../../../interfaces/entity/profile";
import { GOOD_ANSWERS } from "../../../../../tools/constant";
import { COLOR_SCHEME, HORIZONTAL_STACKED_BAR_GRAPH_SIZE } from "../../../../../tools/graph.constant";

/**
 * The theme comparison question number view.
 */
@Component({
	selector: "user-theme-comparison-question-number",
	templateUrl: "./user-theme-comparison-question-number.component.html",
	styleUrls: ["./user-theme-comparison-question-number.component.css"]
})

export class UserThemeComparisonQuestionNumberComponent implements OnInit {
	@Input()
	private theme: Theme;
	@Input()
	public players: Profile[];
	
	public questionsAnswered: SimpleGraphStatisticsInterface[] = [];
	public maxCount: number = 0;
	public view = HORIZONTAL_STACKED_BAR_GRAPH_SIZE;
	public colorScheme = COLOR_SCHEME;
	
	constructor(private _commonUtilsService: CommonUtilsService) { }
	
	// Detect changes on input fields
	ngOnChanges(changes: SimpleChanges) {
		this.calculateStatistics();
	}
	
	ngOnInit() {
		this.calculateStatistics();
	}
	
	/**
	 * Calculate each player's stats on found/listened musics
	 */
	private calculateStatistics() {
		this.questionsAnswered = [];
		this.maxCount = 0;
		
		// this.players.forEach(player => {
		//     let foundMusics = this._commonUtilsService.isNull(player.profileStat.foundMusics[this.theme]) ? 0 : this.getAllMusicsForPlayer(player.profileStat);
		//     if (!this._commonUtilsService.isNull(player.profileStat.listenedMusics[this.theme]) && player.profileStat.listenedMusics[this.theme] > this.maxCount) {
		//         this.maxCount = player.profileStat.listenedMusics[this.theme];
		//     }
		//     this.questionsAnswered.push({
		//         name: player.name,
		//         value: foundMusics
		//     })
		// });
	}
	
	/**
	 * Get musics found for player
	 * @param playerStats
	 */
	private getAllMusicsForPlayer(playerStats: any) {
		let foundMusics = 0;
		let typeKeys = GOOD_ANSWERS;
		typeKeys.forEach(key => {
			if (!this._commonUtilsService.isNull(playerStats.foundMusics[this.theme][key])) {
				foundMusics += playerStats.foundMusics[this.theme][key];
			}
		});
		return foundMusics;
	}
	
}
