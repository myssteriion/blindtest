import {Component, OnInit, Input} from '@angular/core';
import {GOOD_ANSWERS} from '../../../../tools/constant';
import {ToolsService} from '../../../../tools/tools.service';
import {ProfileStatistics} from "../../../../interfaces/common/profile-statistics.interface";
import {COLOR_SCHEME, LINEAR_GAUGE_GRAPH_SIZE} from "../../../../tools/graph.constant";

/**
 * The listened / found musics ratio view.
 */
@Component({
    selector: 'found-listened-musics-ratio',
    templateUrl: './found-listened-musics-ratio.component.html',
    styleUrls: ['./found-listened-musics-ratio.component.css']
})
export class FoundListenedMusicsRatioComponent implements OnInit {
    @Input()
    private statistics: ProfileStatistics;

    public totalMusicsFound: number = 0;
    public totalMusicsListened: number = 0;
    public view = LINEAR_GAUGE_GRAPH_SIZE;
    public colorScheme = COLOR_SCHEME;

    constructor() {
    }

    ngOnInit() {
        this.calculateStatistics();
    }

    /**
     * Calculate statistics for players
     */
    private calculateStatistics() {
        let keys = Object.keys(this.statistics.listenedMusics);

        keys.forEach(key => {
            this.totalMusicsListened += this.statistics.listenedMusics[key];
            this.totalMusicsFound = ToolsService.isNull(this.statistics.foundMusics[key]) ? this.totalMusicsFound : this.totalMusicsFound + this.getAllMusicsFound(this.statistics.foundMusics[key])
        });
    }

    /**
     * Get all musics found for theme
     * @param musicsForTheme
     */
    private getAllMusicsFound(musicsForTheme) {
        let foundMusics = 0;
        let typeKeys = GOOD_ANSWERS;
        typeKeys.forEach(typeKey => {
            foundMusics = ToolsService.isNull(musicsForTheme[typeKey]) ? foundMusics : foundMusics + musicsForTheme[typeKey];
        });
        return foundMusics;
    }
}
