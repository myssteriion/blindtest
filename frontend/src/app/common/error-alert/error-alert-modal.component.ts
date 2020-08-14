import {Component, Input, OnInit} from '@angular/core';
import {ErrorAlert} from 'src/app/interfaces/base/error.alert.interface';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TranslateService} from '@ngx-translate/core';
import {ToolsService} from "../../tools/tools.service";
import {HOME_PATH, HTTP_GATEWAY_TIMEOUT, HTTP_NOT_FOUND} from "../../tools/constant";

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
	public text: string;
	
	/**
	 * Suggestion to show.
	 */
	@Input()
	private suggestions: string[];
	
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
	public showRetry: boolean;
	
	/**
	 * Button label for close.
	 */
	@Input()
	public closeLabel: string;
	
	/**
	 * Show error.
	 */
	public showError: boolean;
	
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
	public getAlertClass(): string {
		
		let css = "";
		
		if (this.level === ErrorAlertModalComponent.ERROR)
			css = "alert alert-danger";
		else if (this.level === ErrorAlertModalComponent.WARNING)
			css = "alert alert-warning";
		
		return css;
	}
	
	/**
	 * If show suggestions.
	 */
	public showSuggestions(): boolean {
		return !ToolsService.isNull(this.suggestions) && this.suggestions.length > 0;
	}
	
	/**
	 * Gets label.
	 */
	public getShowMoreShowLessLabel(): string {
		
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
	public close(): void {
		this._ngbActiveModal.dismiss();
	}
	
	
	/**
	 * Parse http error to ErrorAlert.
	 * @param error the http error
	 *
	 * @return the ErrorAlert
	 */
	public static parseError(error: any): ErrorAlert {
		
		let status = (error.status === HTTP_GATEWAY_TIMEOUT) ? 0 :error.status;
		return { status: status, name: error.name, error: error.error };
	}
	
	/**
	 * Get the modal options.
	 *
	 * @return the model options
	 */
	public static getModalOptions(): Object {
		return { backdrop: 'static', size: 'md' };
	}
	
}
