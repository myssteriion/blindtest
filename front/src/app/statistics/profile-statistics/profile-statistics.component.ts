import { Component, OnInit, Input } from '@angular/core';
import { SLIDE_ANIMATION } from '../../tools/constant';
import { ProfileStatisticsResource } from "../../resources/profile-statistics.resource";
import { Profile } from "../../interfaces/dto/profile.interface";
import { TranslateService } from '@ngx-translate/core';
import { ToasterService } from "../../services/toaster.service";
import { ToolsService } from '../../tools/tools.service'
import { Observable } from 'rxjs';

/**
 * The statistics view.
 */
@Component({
    selector: 'profile-statistics-view',
    templateUrl: './profile-statistics.component.html',
    styleUrls: ['./profile-statistics.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ProfileStatisticsComponent implements OnInit {

    @Input() user: Profile;

    public loaded: boolean = false;

    single: any[];
    multi: any[];

    view: any[] = [700, 400];

    // options
    showXAxis = true;
    showYAxis = true;
    gradient = false;
    showLegend = true;
    showXAxisLabel = true;
    xAxisLabel = "Country";
    showYAxisLabel = true;
    yAxisLabel = "Population";
    colorScheme = {
        domain: ['#a8385d', '#7aa3e5', '#a27ea8', '#aae3f5', '#adcded', '#a95963', '#8796c0', '#7ed3ed', '#50abcc', '#ad6886']
    };
    animations: boolean = true;

    counter = [];

    public userHasStats: boolean = false;

    constructor(private _translate: TranslateService,
        private _toasterService: ToasterService) { }

    ngOnInit() {
        console.log("user on stats", this.user, !ToolsService.isNull(this.user.statistics))
        if (ToolsService.isNull(this.user.statistics)) {
            this.userHasStats = false;
        } else {
            this.userHasStats = true;
        }

        this.fillStats();
    }

    public noStatsForUser() {
        return this._translate.instant("STATISTICS.NO_STATS_FOR_USER", { user: this.user.name });
    }

    public onLegendLabelClick(event: Event) {
        console.log("onLegend", event)
    }

    public select(event: Event) {
        console.log("select", event)
    }

    public activate(event: Event) {
        console.log("activate", event)
    }

    public deactivate(event: Event) {
        console.log("deactivate", event)
    }

    public fillCounter() {
        let scores = this.getAllTimeScores()
        this.counter = [
            {
                "name": this._translate.instant("STATISTICS.CATEGORIES.BEST_SCORE"),
                "value": ToolsService.isNull(scores.bestScore) ? 0 : scores.bestScore
            },
            {
                "name": this._translate.instant("STATISTICS.CATEGORIES.PLAYED_GAMES"),
                "value": this.user.statistics.playedGames
            }

        ];
    }

    public isFinal(count) {
        if (count === 1) {
            this.loaded = true;
        }
    }

    public fillStats() {
        let count = 0;
        let counter = new Observable((observer) => {
            const { next, error } = observer;
            this.fillCounter()
            observer.next()
        })

        counter.subscribe(() => {
            count += 1;
            this.isFinal(count)
        })

    }

    private getAllTimeScores() {
        let keys = Object.keys(this.user.statistics.bestScores);
        let scores = this.user.statistics.bestScores;
        let bestScore = scores[keys[0]];
        let worstScore = scores[keys[0]];

        for (let i = 0; i < keys.length; i++) {
            if (scores[keys[i]] > bestScore) {
                bestScore = scores[keys[i]]
            }
            if (scores[keys[i]] < worstScore) {
                worstScore = scores[keys[i]]
            }
        }

        console.log("User is ", this.user.name, "bestUserScore", bestScore, "worstUserScore", worstScore)
        return { bestScore: bestScore, worstScore: worstScore }
    }
}
