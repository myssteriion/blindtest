import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

/**
 * A yes-no modal.
 */
@Component({
    selector: 'modal-confirm',
    templateUrl: './modal-confirm.component.html',
    styleUrls: ['./modal-confirm.component.css']
})
export class ModalConfirmComponent implements OnInit {

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

    ngOnInit() { }


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
