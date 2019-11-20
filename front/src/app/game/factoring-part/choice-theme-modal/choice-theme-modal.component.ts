import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {THEMES} from "../../../tools/constant";
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
