import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {GOOD_ANSWERS} from '../../../../tools/constant';
import {UtilsService} from "../../../../services/utils.service";
import {Game} from "../../../../interfaces/game/game.interface";
import {SimpleGraphStatisticsInterface} from "../../../../interfaces/common/graph.interface";
import {COLOR_SCHEME} from "../../../../tools/graph.constant";
import {CommonUtilsService} from "myssteriion-utils";

/**
 * The theme comparison question number view.
 */
@Component({
    selector: 'theme-comparison-question-number',
    templateUrl: './theme-comparison-question-number.component.html',
    styleUrls: ['./theme-comparison-question-number.component.css']
})
export class ThemeComparisonQuestionNumberComponent implements OnInit {

    /**
     * The game.
     */
    @Input()
    private game: Game;

    /**
     * The theme.
     */
    @Input()
    private theme: Theme;

    public questionsAnswered: SimpleGraphStatisticsInterface[] = [];

    public maxCount: number = 0;

    public colorScheme = COLOR_SCHEME;



    constructor(private _commonUtilsService: CommonUtilsService) { }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Detect changes on input fields.
     */
    ngOnChanges(changes: SimpleChanges) {
        this.calculateStatistics();
    }



    /**
     * Calculate each player's stats on found/listened musics
     */
    private calculateStatistics() {
        this.questionsAnswered = [];
        this.maxCount = this.game.listenedMusics[this.theme];

        this.game.players.forEach(player => {
            let foundMusics = this._commonUtilsService.isNull(player.foundMusics[this.theme]) ? 0 : this.getAllMusicsForPlayer(player.foundMusics[this.theme]);
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
        let typeKeys = GOOD_ANSWERS;
        typeKeys.forEach(key => {
            if (!this._commonUtilsService.isNull(musicsForTheme[key])) {
                foundMusics += musicsForTheme[key];
            }
        });
        return foundMusics;
    }

    /**
     * Get the nb musics for the selected theme.
     */
    public getMaximum(): number {

        let nbMusics: number = 0;
        if ( !this._commonUtilsService.isNull(this.game.listenedMusics[this.theme]) )
            nbMusics = this.game.listenedMusics[this.theme];

        return nbMusics;
    }

}
