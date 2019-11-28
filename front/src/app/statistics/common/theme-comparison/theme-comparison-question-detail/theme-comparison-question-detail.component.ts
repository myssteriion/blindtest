import {Component, OnInit, Input} from '@angular/core';
import {SLIDE_ANIMATION, THEMES} from '../../../../tools/constant';

/**
 * The theme comparison question detail view.
 */
@Component({
    selector: 'theme-comparison-question-detail',
    templateUrl: './theme-comparison-question-detail.component.html',
    styleUrls: ['./theme-comparison-question-detail.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ThemeComparisonQuestionDetailComponent implements OnInit {
    @Input() theme: Theme;
    @Input() statistics;

    ngOnInit() {
    }

}
