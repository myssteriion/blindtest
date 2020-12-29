import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CommonUtilsService } from "myssteriion-utils";
import { SimpleGraphStatisticsInterface } from "../../../../interfaces/common/graph.interface";
import { Profile } from "../../../../interfaces/entity/profile";
import { UtilsService } from '../../../../services/utils.service'
import { SLIDE_ANIMATION } from '../../../../tools/constant';
import { COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE } from "../../../../tools/graph.constant";

/**
 * The theme listening statistics view.
 */
@Component({
	selector: "theme-listening-view",
	templateUrl: "./theme-listening.component.html",
	styleUrls: ["./theme-listening.component.css"],
	animations: [
		SLIDE_ANIMATION
	]
})
export class ThemeListeningComponent implements OnInit {
	
	@Input()
	private user: Profile;
	
	public listenedMusicsByTheme: SimpleGraphStatisticsInterface[] = [];
	public maxCount: number = 0;
	public view = HORIZONTAL_BAR_GRAPH_SIZE;
	public colorScheme = COLOR_SCHEME;
	
	
	
	constructor(private _translate: TranslateService,
				private _commonUtilsService: CommonUtilsService,
				private _utilsService: UtilsService) {
	}
	
	ngOnInit() {
		this.listenedMusicsByTheme = [];
		this.maxCount = 0;
		// let keys = [];
		//
		// THEMES.forEach(theme => {
		//     keys.push(theme.enumVal);
		// });
		//
		// let listenedThemes = this.user.profileStat.listenedMusics;
		//
		// keys.forEach(key => {
		//     this.listenedMusicsByTheme.push({
		//         name: this._translate.instant("THEMES." + key),
		//         value: this._commonUtilsService.isNull(listenedThemes[key]) ? 0 : parseInt(listenedThemes[key])
		//     });
		//     if (this.maxCount < parseInt(listenedThemes[key])) {
		//         this.maxCount = parseInt(listenedThemes[key]);
		//     }
		// });
		
		this.listenedMusicsByTheme = this._utilsService.sortByAlphabeticalAndNumerical(this.listenedMusicsByTheme);
	}
	
}
