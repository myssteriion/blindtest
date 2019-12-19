import {Component, OnInit, Input} from '@angular/core';
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from '../../../tools/constant';
import {ToolsService} from '../../../tools/tools.service'
import {TranslateService} from "@ngx-translate/core";

/**
 * The answer type view.
 */
@Component({
    selector: 'answer-types',
    templateUrl: './answer-types.component.html',
    styleUrls: ['./answer-types.component.css']
})
export class AnswerTypesComponent implements OnInit {

    @Input() statistics;

    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public results = [];
    public colorScheme = COLOR_SCHEME;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Calculate statistics for each game type
     */
    private calculateStatistics() {
        this.results = [];
        let keys = ["AUTHOR", "TITLE", "BOTH"];

        this.statistics.forEach(player => {
            let musicKeys = Object.keys(player.statistics.foundMusics);
            let series = [];
            let values = {
                AUTHOR: 0,
                TITLE: 0,
                BOTH: 0
            };

            musicKeys.forEach(musicKey => {
                if (!ToolsService.isNull(player.statistics.foundMusics[musicKey])) {
                    keys.forEach(key => {
                        values[key] = ToolsService.isNull(player.statistics.foundMusics[musicKey][key]) ? values[key] : values[key] + player.statistics.foundMusics[musicKey][key]
                    })
                }
            });

            let typeKeys = Object.keys(values);
            typeKeys.forEach(typeKey => {
                series.push({
                    name: this._translate.instant('STATISTICS.CATEGORIES.ANSWER_TYPES.' + typeKey),
                    value: values[typeKey]
                })
            });

            this.results.push({
                name: player.name,
                series: series
            })
        });
        this.results = ToolsService.sortByAlphabeticalAndNumerical(this.results);
    }

}
