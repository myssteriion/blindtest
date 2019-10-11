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
    @Input() title: string;

    /**
     * The modal message.
     */
    @Input() body: string;



    constructor(private _ngbActiveModal: NgbActiveModal) { }

    ngOnInit() { }


    /**
     * On yes.
     */
    public yes(): void {
        this._ngbActiveModal.close();
    }

    /**
     * On no.
     */
    public no(): void {
        this._ngbActiveModal.dismiss();
    }

}
