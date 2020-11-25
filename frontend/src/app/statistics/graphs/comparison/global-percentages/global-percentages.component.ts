import {Component, OnInit, Input} from '@angular/core';
import {GOOD_ANSWERS} from '../../../../tools/constant';
import {UtilsService} from '../../../../tools/utils.service'
import {Profile} from "../../../../interfaces/entity/profile.interface";
import {SimpleGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from "../../../../tools/graph.constant";
import {CommonUtilsService} from "myssteriion-utils";

/**
 * The global percentages view.
 */
@Component({
    selector: 'global-percentages-view',
    templateUrl: './global-percentages.component.html',
    styleUrls: ['./global-percentages.component.css']
})
export class GlobalPercentagesComponent implements OnInit {
    @Input()
    private players: Profile[];

    public percentages: SimpleGraphStatisticsInterface[] = [];
    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public colorScheme = COLOR_SCHEME;

    constructor(private _commonUtilsService: CommonUtilsService,
				private _utilsService: UtilsService) { }

    ngOnInit() {
        this.calculateStatistics()
    }

    /**
     * Calculate global statistics for each player
     */
    private calculateStatistics() {
        this.percentages = [];

        let keys = GOOD_ANSWERS;
        this.players.forEach(player => {
            let listenedMusics = 0;
            let foundMusics = 0;
            let musicKeys = Object.keys(player.profileStat.listenedMusics);
            musicKeys.forEach(musicKey => {
                listenedMusics = listenedMusics + player.profileStat.listenedMusics[musicKey];
                keys.forEach(key => {
                    if (!this._commonUtilsService.isNull(player.profileStat.foundMusics[musicKey])) {
                        foundMusics = this._commonUtilsService.isNull(player.profileStat.foundMusics[musicKey][key]) ? foundMusics : foundMusics + player.profileStat.foundMusics[musicKey][key];
                    }
                })
            });

            // Necessary to put value = 0 if no value in order to make graph library work
            this.percentages.push({
                name: player.name,
                value: isNaN(Math.floor(foundMusics / listenedMusics * 100)) ? 0 : Math.floor(foundMusics / listenedMusics * 100)
            });
        });

        this.percentages = this._utilsService.sortByAlphabeticalAndNumerical(this.percentages);
    }

}
