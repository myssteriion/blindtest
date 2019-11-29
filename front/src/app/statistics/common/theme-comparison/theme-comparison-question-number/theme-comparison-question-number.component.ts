import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import {SLIDE_ANIMATION} from '../../../../tools/constant';
import {ToolsService} from "../../../../tools/tools.service";

/**
 * The theme comparison question number view.
 */
@Component({
    selector: 'theme-comparison-question-number',
    templateUrl: './theme-comparison-question-number.component.html',
    styleUrls: ['./theme-comparison-question-number.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ThemeComparisonQuestionNumberComponent implements OnInit {
    @Input() theme: Theme;
    @Input() statistics;
    @Input() colorScheme;

    public questionsAnswered = [];
    public maxCount = 0;

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
    calculateStatistics() {
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

    getAllMusicsForPlayer(musicsForTheme) {
        let foundMusics = 0;
        let typeKeys = ["BOTH", "TITLE", "AUTHOR"];
        typeKeys.forEach(key => {
            if (!ToolsService.isNull(musicsForTheme[key])) {
                foundMusics += musicsForTheme[key];
            }
        });
        return foundMusics;
    }

}
