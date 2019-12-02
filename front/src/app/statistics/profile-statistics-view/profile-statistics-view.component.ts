import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION} from '../../tools/constant';
import {Profile} from "../../interfaces/dto/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from '../../tools/tools.service'
import {Observable} from 'rxjs';

/**
 * The statistics view.
 */
@Component({
    selector: 'profile-statistics-view',
    templateUrl: './profile-statistics-view.component.html',
    styleUrls: ['./profile-statistics-view.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ProfileStatisticsViewComponent implements OnInit {

    @Input() user: Profile;

    public loaded: boolean = false;

    colorScheme = {
        domain: ['#a8385d', '#7aa3e5', '#a27ea8', '#aae3f5', '#adcded', '#a95963', '#8796c0', '#7ed3ed', '#50abcc', '#ad6886']
    };
    animations: boolean = true;
    counter = [];

    public userHasStats: boolean = false;

    constructor(private _translate: TranslateService) {
    }

    ngOnInit() {
        console.log("user on stats", this.user, !ToolsService.isNull(this.user.statistics));
        this.userHasStats = !ToolsService.isNull(this.user.statistics);
        this.fillStats();
    }

    public noStatsForUser() {
        return this._translate.instant("STATISTICS.NO_STATS_FOR_USER", {user: this.user.name});
    }

    public onLegendLabelClick(event: Event) {
        // console.log("onLegend", event)
    }

    public select(event: Event) {
        // console.log("select", event)
    }

    public activate(event: Event) {
        // console.log("activate", event)
    }

    public deactivate(event: Event) {
        // console.log("deactivate", event)
    }

    public fillCounter() {
        let scores = this.getAllTimeScores();
        let playedGames = this.getAllPlayedGames();
        this.counter = [
            {
                "name": this._translate.instant("STATISTICS.CATEGORIES.BEST_SCORE.TITLE"),
                "value": ToolsService.isNull(scores.bestScore) ? 0 : scores.bestScore
            },
            {
                "name": this._translate.instant("STATISTICS.CATEGORIES.PLAYED_GAMES"),
                "value": ToolsService.isNull(playedGames) ? 0 : playedGames
            }

        ];
    }

    public isFinal(count) {
        if (count === 2) {
            this.loaded = true;
        }
    }

    public fillStats() {
        let count = 0;
        let counter = new Observable((observer) => {
            const {next, error} = observer;
            this.fillCounter();
            observer.next();
        });

        counter.subscribe(() => {
            count += 1;
            this.isFinal(count);
        });

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

        console.log("User is ", this.user.name, "bestUserScore", bestScore, "worstUserScore", worstScore);
        return {bestScore: bestScore, worstScore: worstScore}
    }

    private getAllPlayedGames(): Number {
        let keys = Object.keys(this.user.statistics.playedGames);
        let playedGames = this.user.statistics.playedGames;
        let numberOfPlayedGames = 0;

        for (let i = 0; i < keys.length; i++) {
            numberOfPlayedGames += playedGames[keys[i]]
        }

        return numberOfPlayedGames
    }

    private getBestScoreByGameType() {

    }
}
