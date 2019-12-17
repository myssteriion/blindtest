import {Component, OnInit, Input} from '@angular/core';
import {HORIZONTAL_BAR_GRAPH_SIZE} from '../../../tools/constant';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../../tools/tools.service';

/**
 * The rank counter view.
 */
@Component({
    selector: 'rank-counter',
    templateUrl: './rank-counter.component.html',
    styleUrls: ['./rank-counter.component.css']
})
export class RankCounterComponent implements OnInit {
    @Input() colorScheme;
    @Input() statistics;

    public rankCounter = [];
    public view = HORIZONTAL_BAR_GRAPH_SIZE;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Calculate statistics for players
     */
    private calculateStatistics() {
        let rankValues = ["FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH", "SIXTH", "SEVENTH", "EIGHTH", "NINTH", "TENTH", "ELEVENTH", "TWELFTH"];
        rankValues.forEach(rank => {
            this.rankCounter.push({
                name: this._translate.instant("STATISTICS.CATEGORIES.RANK_OCCUPIED." + rank),
                value: ToolsService.isNull(this.statistics.wonGames[rank]) ? 0 : this.statistics.wonGames[rank]
            });
        });
    }

}
