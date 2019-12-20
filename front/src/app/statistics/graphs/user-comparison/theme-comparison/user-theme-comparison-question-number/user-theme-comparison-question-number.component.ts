import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import {ToolsService} from "../../../../../tools/tools.service";
import {COLOR_SCHEME, GOOD_ANSWERS, HORIZONTAL_STACKED_BAR_GRAPH_SIZE} from "../../../../../tools/constant";
import {Profile} from "../../../../../interfaces/dto/profile.interface";
import {SimpleGraphStatisticsInterface} from "../../../../../interfaces/common/graph.interface";

/**
 * The theme comparison question number view.
 */
@Component({
    selector: 'user-theme-comparison-question-number',
    templateUrl: './user-theme-comparison-question-number.component.html',
    styleUrls: ['./user-theme-comparison-question-number.component.css']
})

export class UserThemeComparisonQuestionNumberComponent implements OnInit {
    @Input()
    private theme: Theme;
    @Input()
    private players: Profile[];

    public questionsAnswered: SimpleGraphStatisticsInterface[] = [];
    public maxCount: number = 0;
    public view = HORIZONTAL_STACKED_BAR_GRAPH_SIZE;
    public colorScheme = COLOR_SCHEME;

    constructor() {
    }

    // Detect changes on input fields
    ngOnChanges(changes: SimpleChanges) {
        this.calculateStatistics();
        // You can also use categoryId.previousValue and
        // categoryId.firstChange for comparing old and new values
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Calculate each player's stats on found/listened musics
     */
    private calculateStatistics() {
        this.questionsAnswered = [];
        this.maxCount = 0;

        this.players.forEach(player => {
            let foundMusics = ToolsService.isNull(player.statistics.foundMusics[this.theme]) ? 0 : this.getAllMusicsForPlayer(player.statistics);
            if (!ToolsService.isNull(player.statistics.listenedMusics[this.theme]) && player.statistics.listenedMusics[this.theme] > this.maxCount) {
                this.maxCount = player.statistics.listenedMusics[this.theme];
            }
            this.questionsAnswered.push({
                name: player.name,
                value: foundMusics
            })
        });
    }

    /**
     * Get musics found for player
     * @param playerStats
     */
    private getAllMusicsForPlayer(playerStats) {
        let foundMusics = 0;
        let typeKeys = GOOD_ANSWERS;
        typeKeys.forEach(key => {
            if (!ToolsService.isNull(playerStats.foundMusics[this.theme][key])) {
                foundMusics += playerStats.foundMusics[this.theme][key];
            }
        });
        return foundMusics;
    }

}
