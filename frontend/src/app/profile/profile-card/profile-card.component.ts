import { Component, EventEmitter, Input, Output } from '@angular/core';
import { faEdit, faTimes, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { HTTP_CONFLICT, ModalService, ToasterService } from "myssteriion-utils";
import { Profile } from 'src/app/interfaces/entity/profile';
import { ProfileEditModalComponent } from 'src/app/profile/profile-edit-modal/profile-edit-modal.component';
import { ProfileResource } from "../../resources/profile.resource";
import { UtilsService } from "../../services/utils.service";

/**
 * Profile card.
 */
@Component({
	selector: "profile-card",
	templateUrl: "./profile-card.component.html",
	styleUrls: ["./profile-card.component.css"]
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
	public canEdit: boolean;
	
	/**
	 * Event after update/delete profile.
	 */
	@Output()
	private onEdit = new EventEmitter();
	
	/**
	 * If can select profile.
	 */
	@Input()
	public canSelect: boolean;
	
	/**
	 * On select.
	 */
	@Output()
	private onSelect = new EventEmitter();
	
	/**
	 * If can deselect profile.
	 */
	@Input()
	public canDeselect: boolean;
	
	/**
	 * On deselect.
	 */
	@Output()
	public onDeselect = new EventEmitter();
	
	public faEdit = faEdit;
	public faTrashAlt = faTrashAlt;
	public faTimes = faTimes;
	
	
	
	constructor(private ngbModal: NgbModal,
				private profileResource: ProfileResource,
				private translate: TranslateService,
				private toasterService: ToasterService,
				private utilsService: UtilsService,
				private modalService: ModalService) {
		
	}
	
	
	
	/**
	 * Get image from avatar.
	 */
	public getImgFromAvatar(): string | null {
		return this.utilsService.getImgFromAvatar(this.profile.avatar);
	}
	
	/**
	 * Open modal for edit profile and emit it.
	 */
	public edit(): void {
		
		const modalRef = this.ngbModal.open(ProfileEditModalComponent, { backdrop: "static", size: "md" } );
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
	public delete(): void {
		
		let title: string = this.translate.instant("COMMON.WARNING");
		let body: string = this.translate.instant("PROFILE.CARD.DELETE_BODY", { profile_name: this.profile.name } );
		
		this.modalService.openConfirmModal(title, body).then(
			() => { this.deletedProfile() },
			() => { /* do nothing */ }
		);
	}
	
	/**
	 * Delete profile and emit it.
	 */
	private deletedProfile(): void {
		
		this.profileResource.delete(this.profile).subscribe(
			response => {
				this.toasterService.success( this.translate.instant("PROFILE.CARD.DELETED_TOASTER", { profile_name: this.profile.name } ) );
				this.onEdit.emit();
			},
			error => {
				
				if (error.status === HTTP_CONFLICT) {
					this.toasterService.error( this.translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
				}
				else {
					
					let text: string = this.translate.instant("PROFILE.CARD.DELETE_ERROR");
					let closeLabel: string = this.translate.instant("COMMON.CLOSE");
					
					this.modalService.openErrorModal(text, error, true, closeLabel).then(
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
	 * Test if icons must be show.
	 *
	 * @return TRUE if the icon must be show, FALSE otherwise
	 */
	public showIcons(): boolean {
		return this.canEdit || this.canDeselect;
	}
	
}
