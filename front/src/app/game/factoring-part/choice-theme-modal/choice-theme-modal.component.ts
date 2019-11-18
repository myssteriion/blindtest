import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {THEMES, THEMES_INDEX} from "../../../tools/constant";
import {ToolsService} from "../../../tools/tools.service";

/**
 * The choice theme modal.
 */
@Component({
    selector: 'choice-theme-modal',
    templateUrl: './choice-theme-modal.component.html',
    styleUrls: ['./choice-theme-modal.component.css']
})
export class ChoiceThemeModalComponent implements OnInit {

    /**
     * Themes list.
     */
    private themes = THEMES;

    /**
     * Selected theme.
     */
    private selectedTheme: Theme;



    constructor(private _ngbActiveModal: NgbActiveModal) {}

    ngOnInit() {
    }



    /**
     * Select current theme.
     *
     * @param index the theme index
     */
    private selectCurrentTheme(index: number) {
        this.selectedTheme = THEMES_INDEX[index];
    }

    /**
     * Gets image class.
     *
     * @param index the theme index
     */
    private getImgClass(index: number) {

        let css = "";

        if ( !ToolsService.isNull(this.selectedTheme) && this.selectedTheme === THEMES_INDEX[index] )
            css = "music-result-modal-img-active";

        return css;
    }

    /**
     * If the button is disabled.
     */
    private closeIsDisabled() {
        return ToolsService.isNull(this.selectedTheme);
    }

    /**
     * Close modal.
     */
    private close() {
        this._ngbActiveModal.close(this.selectedTheme);
    }

}
