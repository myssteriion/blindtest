import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from "@ngx-translate/core";
import { CommonUtilsService } from "myssteriion-utils";
import { ComplexGraphStatisticsInterface } from "../../../../interfaces/common/graph.interface";
import { Profile } from "../../../../interfaces/entity/profile";
import { UtilsService } from '../../../../services/utils.service'
import { GOOD_ANSWERS } from '../../../../tools/constant';
import { COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE } from "../../../../tools/graph.constant";

/**
 * The answer type view.
 */
@Component({
	selector: "answer-types",
	templateUrl: "./answer-types.component.html",
	styleUrls: ["./answer-types.component.css"]
})
export class AnswerTypesComponent implements OnInit {
	
	@Input()
	public players: Profile[];
	
	public view = HORIZONTAL_BAR_GRAPH_SIZE;
	public results: ComplexGraphStatisticsInterface[] = [];
	public colorScheme = COLOR_SCHEME;
	
	
	
	constructor(private translate: TranslateService,
				private commonUtilsService: CommonUtilsService,
				private utilsService: UtilsService) {
	}
	
	ngOnInit() {
		this.calculateStatistics();
	}
	
	
	
	/**
	 * Compute statistics for each game type.
	 */
	private calculateStatistics(): void {
		this.results = [];
		let keys = GOOD_ANSWERS;
		
		// this.players.forEach(player => {
		//
		// 	let musicKeys = Object.keys(player.profileStat.foundMusics);
		// 	let series: any[] = [];
		// 	let values = {
		// 		AUTHOR: 0,
		// 		TITLE: 0,
		// 		BOTH: 0
		// 	};
		//
		// 	musicKeys.forEach(musicKey => {
		// 		if (!this.commonUtilsService.isNull(player.profileStat.foundMusics[musicKey])) {
		// 			keys.forEach(key => {
		// 				values[key] = this.commonUtilsService.isNull(player.profileStat.foundMusics[musicKey][key]) ? values[key] : values[key] + player.profileStat.foundMusics[musicKey][key]
		// 			})
		// 		}
		// 	});
		//
		// 	let typeKeys = Object.keys(values);
		// 	typeKeys.forEach(typeKey => {
		// 		series.push({
		// 			name: this.translate.instant('STATISTICS.CATEGORIES.ANSWER_TYPES.' + typeKey),
		// 			value: values[typeKey]
		// 		})
		// 	});
		//
		// 	this.results.push({
		// 		name: player.name,
		// 		series: series
		// 	})
		// });
		this.results = this.utilsService.sortByAlphabeticalAndNumerical(this.results);
	}
	
}
