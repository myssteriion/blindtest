import {Component, Input} from '@angular/core';
import {ErrorAlert} from 'src/app/interfaces/base/error.alert.interface';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
	selector: 'error-alert-modal',
	templateUrl: './error-alert-modal.component.html',
	styleUrls: ['./error-alert-modal.component.css']
})
export class ErrorAlertModalComponent {

	/**
	 * Text to show.
	 */
	@Input()
	private text: string;

	/**
	 * The error.
	 */
	@Input()
	private error: ErrorAlert;

	/**
	 * Alert level (ERROR, WARN).
	 */
	@Input()
	private level: string;

	/**
	 * Show retry button.
	 */
	@Input()
	private showRetry: boolean;

	/**
	 * Error level.
	 */
	public static ERROR: string = "ERROR";

	/**
	 * Warning level.
	 */
	public static WARNING: string = "WARNING";



	constructor(private _ngbActiveModal: NgbActiveModal) { }



	/**
	 * Gets css class for text.
	 */
	private getAlertClass() {

		let css = "";

		if (this.level === ErrorAlertModalComponent.ERROR)
			css = "alert alert-danger";
		else if (this.level === ErrorAlertModalComponent.WARNING)
			css = "alert alert-warning";

		return css;
	}

	/**
	 * On retry.
	 */
	private retry(): void {
		this._ngbActiveModal.close();
	}

	/**
	 * On close.
	 */
	private close(): void {
		this._ngbActiveModal.dismiss();
	}

}
