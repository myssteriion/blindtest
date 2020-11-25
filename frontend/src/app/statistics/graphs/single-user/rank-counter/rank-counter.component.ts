import {Component, OnInit, Input} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {UtilsService} from '../../../../tools/utils.service';
import {Profile} from "../../../../interfaces/entity/profile.interface";
import {SimpleGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {COLOR_SCHEME, HORIZONTAL_BAR_GRAPH_SIZE} from "../../../../tools/graph.constant";
import {CommonUtilsService} from "myssteriion-utils";

/**
 * The rank counter view.
 */
@Component({
    selector: 'rank-counter',
    templateUrl: './rank-counter.component.html',
    styleUrls: ['./rank-counter.component.css']
})
export class RankCounterComponent implements OnInit {
    @Input()
    private user: Profile;

    public rankCounter: SimpleGraphStatisticsInterface[] = [];
    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public colorScheme = COLOR_SCHEME;

    constructor(private _translate: TranslateService,
				private _commonUtilsService: CommonUtilsService) {
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
                name: this._translate.instant("RANK_OCCUPIED." + rank),
                value: this._commonUtilsService.isNull(this.user.profileStat.wonGames[rank]) ? 0 : this.user.profileStat.wonGames[rank]
            });
        });
    }

}
