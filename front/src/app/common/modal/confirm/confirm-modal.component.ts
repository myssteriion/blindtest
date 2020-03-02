import {Component, Input} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

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
	public title: string;
	
	/**
	 * The modal message.
	 */
	@Input()
	public body: string;
	
	
	
	constructor(private _ngbActiveModal: NgbActiveModal) { }
	
	
	
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
