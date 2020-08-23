import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/dto/profile.interface';
import {faEdit, faTimes, faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfileEditModalComponent} from 'src/app/profile/profile-edit-modal/profile-edit-modal.component';
import {ToolsService} from "../../tools/tools.service";
import {ConfirmModalComponent} from "../../common/modal/confirm/confirm-modal.component";
import {ProfileResource} from "../../resources/profile.resource";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from 'src/app/services/toaster.service';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {HTTP_CONFLICT} from "../../tools/constant";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";

/**
 * Profile card.
 */
@Component({
	selector: 'profile-card',
	templateUrl: './profile-card.component.html',
	styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {
	
	/**
	 * The profile.
	 */
	@Input()
	public profile: Profile;
	
	/**
	 * If can update/delete profile.
	 */
	@Input()
	private canEdit: boolean;
	
	/**
	 * Event after update/delete profile.
	 */
	@Output()
	private onEdit = new EventEmitter();
	
	/**
	 * If can select profile.
	 */
	@Input()
	private canSelect: boolean;
	
	/**
	 * On select.
	 */
	@Output()
	private onSelect = new EventEmitter();
	
	/**
	 * If can deselect profile.
	 */
	@Input()
	private canDeselect: boolean;
	
	/**
	 * On deselect.
	 */
	@Output()
	private onDeselect = new EventEmitter();
	
	private faEdit = faEdit;
	private faTrashAlt = faTrashAlt;
	private faTimes = faTimes;
	
	
	
	constructor(private _ngbModal: NgbModal,
				private _profileResource: ProfileResource,
				private _translate: TranslateService,
				private _toasterService: ToasterService) {
		
	}
	
	
	
	/**
	 * Get image from avatar.
	 */
	public getImgFromAvatar(): string {
		return ToolsService.getImgFromAvatar(this.profile.avatar);
	}
	
	/**
	 * Open modal for edit profile and emit it.
	 */
	private edit(): void {
		
		const modalRef = this._ngbModal.open(ProfileEditModalComponent, { backdrop: 'static', size: 'md' } );
		modalRef.componentInstance.profile = this.profile;
		modalRef.componentInstance.create = false;
		
		modalRef.result.then(
			(result: Profile) => { this.profile = result; this.onEdit.emit(); },
			() => { /* do nothing */ }
		);
	}
	
	/**
	 * Open modal for delete profile.
	 */
	private delete(): void {
		
		const modalRef = this._ngbModal.open( ConfirmModalComponent, { backdrop: 'static' } );
		modalRef.componentInstance.title = this._translate.instant("COMMON.WARNING");
		modalRef.componentInstance.body = this._translate.instant("PROFILE.CARD.DELETE_BODY", { profile_name: this.profile.name } );
		
		modalRef.result.then(
			() => { this.deletedProfile() },
			() => { /* do nothing */ }
		);
	}
	
	/**
	 * Delete profile and emit it.
	 */
	private deletedProfile(): void {
		
		this._profileResource.delete(this.profile).subscribe(
			response => {
				this._toasterService.success( this._translate.instant("PROFILE.CARD.DELETED_TOASTER", { profile_name: this.profile.name } ) );
				this.onEdit.emit();
			},
			error => {
				
				let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
				
				if (errorAlert.status === HTTP_CONFLICT) {
					this._toasterService.error( this._translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
				}
				else {
					
					const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
					modalRef.componentInstance.text = this._translate.instant("PROFILE.CARD.DELETE_ERROR");
					modalRef.componentInstance.suggestions = undefined;
					modalRef.componentInstance.error = errorAlert;
					modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
					modalRef.componentInstance.showRetry = true;
					modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CLOSE");
					
					modalRef.result.then(
						() => { this.deletedProfile(); },
						() => { /* do nothing */ }
					);
				}
			}
		);
	}
	
	/**
	 * On click on profile.
	 */
	public select(): void {
		if (this.canSelect)
			this.onSelect.emit();
	}
	
	/**
	 * Test if icons need to be show.
	 */
	public showIcons(): boolean {
		return this.canEdit || this.canDeselect;
	}
	
}
