import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import {COLOR_SCHEME, FOUND_MUSIC_TYPES, HORIZONTAL_BAR_GRAPH_SIZE} from '../../../../tools/constant';
import {ToolsService} from "../../../../tools/tools.service";
import {Game} from "../../../../interfaces/game/game.interface";
import {SimpleGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";

/**
 * The theme comparison question number view.
 */
@Component({
    selector: 'theme-comparison-question-number',
    templateUrl: './theme-comparison-question-number.component.html',
    styleUrls: ['./theme-comparison-question-number.component.css']
})
export class ThemeComparisonQuestionNumberComponent implements OnInit {
    @Input()
    private theme: Theme;
    @Input()
    private statistics: Game;

    public questionsAnswered: SimpleGraphStatisticsInterface[] = [];
    public maxCount: number = 0;
    public view = HORIZONTAL_BAR_GRAPH_SIZE;
    public colorScheme = COLOR_SCHEME;

    constructor() {
    }

    // Detect changes on input fields
    ngOnChanges(changes: SimpleChanges) {
        this.calculateStatistics();
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Calculate each player's stats on found/listened musics
     */
    private calculateStatistics() {
        this.questionsAnswered = [];
        this.maxCount = this.statistics.listenedMusics[this.theme];

        this.statistics.players.forEach(player => {
            let foundMusics = ToolsService.isNull(player.foundMusics[this.theme]) ? 0 : this.getAllMusicsForPlayer(player.foundMusics[this.theme]);
            this.questionsAnswered.push({
                name: player.profile.name,
                value: foundMusics
            })
        });
    }

    /**
     * Get all found musics for theme
     * @param musicsForTheme
     */
    private getAllMusicsForPlayer(musicsForTheme) {
        let foundMusics = 0;
        let typeKeys = FOUND_MUSIC_TYPES;
        typeKeys.forEach(key => {
            if (!ToolsService.isNull(musicsForTheme[key])) {
                foundMusics += musicsForTheme[key];
            }
        });
        return foundMusics;
    }

}
