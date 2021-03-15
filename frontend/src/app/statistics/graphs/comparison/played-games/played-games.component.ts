import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from "@ngx-translate/core";
import { CommonUtilsService } from "myssteriion-utils";
import { ComplexGraphStatisticsInterface } from "../../../../interfaces/common/graph.interface";
import { Profile } from "../../../../interfaces/entity/profile";
import { UtilsService } from '../../../../services/utils.service'
import { DURATIONS } from '../../../../tools/constant';
import { COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE } from "../../../../tools/graph.constant";

/**
 * The number of games played view.
 */
@Component({
	selector: "played-games",
	templateUrl: "./played-games.component.html",
	styleUrls: ["./played-games.component.scss"]
})
export class PlayedGamesComponent implements OnInit {
	
	@Input()
	public players: Profile[];
	
	public view = HORIZONTAL_BAR_GRAPH_SIZE;
	public results: ComplexGraphStatisticsInterface[] = [];
	public colorScheme = COLOR_SCHEME;
	
	
	
	constructor(private _translate: TranslateService,
				private _commonUtilsService: CommonUtilsService,
				private _utilsService: UtilsService) { }
	
	ngOnInit() {
		this.calculateStatistics();
	}
	
	
	
	/**
	 * Calculate number games played for each game type
	 */
	private calculateStatistics(): void {
		this.results = [];
		let keys = DURATIONS;
		
		// this.players.forEach(player => {
		// 	let series = [];
		// 	keys.forEach(key => {
		// 		series.push({
		// 			name: this._translate.instant('STATISTICS.CATEGORIES.BEST_SCORE.' + key),
		// 			value: this._commonUtilsService.isNull(player.profileStat.playedGames[key]) ? 0 : player.profileStat.playedGames[key]
		// 		});
		// 	});
		//
		// 	this.results.push({
		// 		name: player.name,
		// 		series: series
		// 	})
		// });
		this.results = this._utilsService.sortByAlphabeticalAndNumerical(this.results);
	}
	
}
