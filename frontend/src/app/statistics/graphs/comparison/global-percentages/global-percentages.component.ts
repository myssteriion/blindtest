import {Component, OnInit, Input} from '@angular/core';
import {GOOD_ANSWERS} from '../../../../tools/constant';
import {ToolsService} from '../../../../tools/tools.service'
import {Profile} from "../../../../interfaces/dto/profile.interface";
import {SimpleGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from "../../../../tools/graph.constant";

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

    constructor() {
    }

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
                    if (!ToolsService.isNull(player.profileStat.foundMusics[musicKey])) {
                        foundMusics = ToolsService.isNull(player.profileStat.foundMusics[musicKey][key]) ? foundMusics : foundMusics + player.profileStat.foundMusics[musicKey][key];
                    }
                })
            });

            // Necessary to put value = 0 if no value in order to make graph library work
            this.percentages.push({
                name: player.name,
                value: isNaN(Math.floor(foundMusics / listenedMusics * 100)) ? 0 : Math.floor(foundMusics / listenedMusics * 100)
            });
        });

        this.percentages = ToolsService.sortByAlphabeticalAndNumerical(this.percentages);
    }

}
