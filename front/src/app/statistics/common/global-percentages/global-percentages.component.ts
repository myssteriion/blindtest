import {Component, OnInit, Input} from '@angular/core';
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from '../../../tools/constant';
import {ToolsService} from '../../../tools/tools.service'

/**
 * The global percentages view.
 */
@Component({
    selector: 'global-percentages-view',
    templateUrl: './global-percentages.component.html',
    styleUrls: ['./global-percentages.component.css']
})
export class GlobalPercentagesComponent implements OnInit {
    @Input() statistics;

    public percentages = [];
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
    public calculateStatistics() {
        this.percentages = [];

        let keys = ["BOTH", "TITLE", "AUTHOR"];
        this.statistics.forEach(player => {
            let listenedMusics = 0;
            let foundMusics = 0;
            let musicKeys = Object.keys(player.statistics.listenedMusics);
            musicKeys.forEach(musicKey => {
                listenedMusics = listenedMusics + player.statistics.listenedMusics[musicKey];
                keys.forEach(key => {
                    if (!ToolsService.isNull(player.statistics.foundMusics[musicKey])) {
                        foundMusics = ToolsService.isNull(player.statistics.foundMusics[musicKey][key]) ? foundMusics : foundMusics + player.statistics.foundMusics[musicKey][key];
                    }
                })
            });

            this.percentages.push({
                name: player.name,
                value: Math.floor(foundMusics / listenedMusics * 100)
            });
        });

        this.percentages = ToolsService.sortByAlphabeticalAndNumerical(this.percentages);
    }

}
