import {Component, Input, OnInit} from '@angular/core';
import {ErrorAlert} from 'src/app/interfaces/base/error.alert.interface';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from "../../tools/tools.service";

@Component({
	selector: 'error-alert-modal',
	templateUrl: './error-alert-modal.component.html',
	styleUrls: ['./error-alert-modal.component.css']
})
export class ErrorAlertModalComponent implements OnInit {

	/**
	 * Text to show.
	 */
	@Input()
	private text: string;

	/**
	 * Suggestion to show.
	 */
	@Input()
	private suggestion: string;

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
	 * Button label for close.
	 */
	@Input()
	private closeLabel: string;

	/**
	 * Show error.
	 */
	private showError: boolean;

	/**
	 * Error level.
	 */
	public static ERROR: string = "ERROR";

	/**
	 * Warning level.
	 */
	public static WARNING: string = "WARNING";



	constructor(private _ngbActiveModal: NgbActiveModal,
				private _translate: TranslateService) { }

	ngOnInit(): void {
		this.showError = this.error.status === 0;
	}



	/**
	 * Gets css class for text.
	 */
	private getAlertClass(): string {

		let css = "";

		if (this.level === ErrorAlertModalComponent.ERROR)
			css = "alert alert-danger";
		else if (this.level === ErrorAlertModalComponent.WARNING)
			css = "alert alert-warning";

		return css;
	}

	/**
	 * If show suggestion.
	 */
	private showSuggestion(): boolean {
		return !ToolsService.isNullOrEmpty(this.suggestion);
	}

	/**
	 * Gets label.
	 */
	private getShowMoreShowLessLabel(): string {

		let label = "COMMON.";

		label += (this.showError) ? "HIDE_DETAIL" : "SHOW_DETAIL";

		return this._translate.instant(label);
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
