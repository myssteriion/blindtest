import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

/**
 * A yes-no modal.
 */
@Component({
    selector: 'confirm-modal',
    templateUrl: './confirm-modal.component.html',
    styleUrls: ['./confirm-modal.component.css']
})
export class ConfirmModalComponent {

    /**
     * The modal title.
     */
    @Input()
    private title: string;

    /**
     * The modal message.
     */
    @Input()
    private body: string;



    constructor(private _ngbActiveModal: NgbActiveModal) { }



    /**
     * On yes.
     */
    private yes(): void {
        this._ngbActiveModal.close();
    }

    /**
     * On no.
     */
    private no(): void {
        this._ngbActiveModal.dismiss();
    }

}
