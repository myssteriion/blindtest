import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'modal-confirm',
    templateUrl: './modal-confirm.component.html',
    styleUrls: ['./modal-confirm.component.css']
})
export class ModalConfirmComponent implements OnInit {

    @Input() title: string;

    @Input() body: string;



    constructor(private _ngbActiveModal: NgbActiveModal) { }

    ngOnInit() {

    }



    public yes(): void {
        this._ngbActiveModal.close();
    }

    public no(): void {
        this._ngbActiveModal.dismiss();
    }

}
