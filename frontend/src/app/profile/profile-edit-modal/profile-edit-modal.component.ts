import {Component, Input, OnInit} from '@angular/core';
import {Profile} from 'src/app/interfaces/entity/profile.interface';
import {Avatar} from 'src/app/interfaces/entity/avatar.interface';
import {AvatarResource} from 'src/app/resources/avatar.resource';
import {UtilsService} from "../../tools/utils.service";
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from "../../interfaces/base/page.interface";
import {TranslateService} from '@ngx-translate/core';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {DEFAULT_BACKGROUND} from "../../tools/constant";
import {CommonUtilsService, HTTP_CONFLICT, ToasterService} from "myssteriion-utils";

declare var $: any;

/**
 * Profile creation/edition modal.
 */
@Component({
	selector: 'profile-edit-modal',
	templateUrl: './profile-edit-modal.component.html',
	styleUrls: ['./profile-edit-modal.component.css']
})
export class ProfileEditModalComponent implements OnInit {
	
	/**
	 * The profile.
	 */
	@Input()
	private profile: Profile;
	
	/**
	 * Creation or edition.
	 */
	@Input()
	public create: boolean;
	
	/**
	 * The new profile.
	 */
	public newProfile: Profile;
	
	/**
	 * The avatars page.
	 */
	private avatarsPage: Page<Avatar>;
	
	/**
	 * If avatars page is empty.
	 */
	public avatarsPageIsEmpty: boolean;
	
	/**
	 * Show/hide pageable.
	 */
	private showAvatarsPageable: boolean;
	
	/**
	 * The search name filter.
	 */
	private searchName: string;
	
	/**
	 * If the color picker is open.
	 */
	private colorPickerIsOpen: boolean;
	
	/**
	 * Avatars number per page.
	 */
	private avatarsPerPage: string = "12";
	
	
	
	constructor(private _avatarResource: AvatarResource,
				private _ngbActiveModal: NgbActiveModal,
				private _profileResource: ProfileResource,
				private _toasterService: ToasterService,
				private _translate: TranslateService,
				private _ngbModal: NgbModal,
				private _commonUtilsService: CommonUtilsService,
				private _utilsService: UtilsService) { }
	
	public ngOnInit(): void {
		
		this.avatarsPageIsEmpty = true;
		this.showAvatarsPageable = false;
		this.searchName = "";
		this.colorPickerIsOpen = false;
		
		if (this.create) {
			
			this.newProfile = {
				id: null,
				background: DEFAULT_BACKGROUND,
				name: "",
				avatar: null
			};
		}
		else {
			
			this.newProfile = {
				id: this.profile.id,
				background: this.profile.background,
				name: this.profile.name,
				avatar: this.profile.avatar
			};
		}
		
		this.loadAvatars(1);
	}
	
	
	
	/**
	 * Load avatars page.
	 *
	 * @param pageNumber the page number
	 */
	private loadAvatars(pageNumber: number): void {
		
		this._avatarResource.findAllBySearchName(this.searchName, pageNumber-1, this.avatarsPerPage).subscribe(
			response => {
				
				this.avatarsPage = response;
				this.avatarsPageIsEmpty = this.avatarsPage.empty;
				this.showAvatarsPageable = this.avatarsPage.totalPages > 1;
			},
			error => {
				
				let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
				
				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
				modalRef.componentInstance.text = this._translate.instant("PROFILE.EDIT_MODAL.AVATAR_LOAD_ERROR");
				modalRef.componentInstance.suggestions = undefined;
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = true;
				modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CANCEL_MODIFICATION");
				
				modalRef.result.then(
					() => { this.loadAvatars(1); },
					() => { this.cancel() }
				);
			}
		);
	}
	
	/**
	 * Gets image from avatar.
	 *
	 * @param avatar the avatar
	 */
	private getImgFromAvatar(avatar: Avatar): string {
		return this._utilsService.getImgFromAvatar(avatar);
	}
	
	/**
	 * Select an avatar.
	 *
	 * @param avatar the avatar selected
	 */
	private selectAvatar(avatar: Avatar): void {
		this.newProfile.avatar = avatar;
	}
	
	/**
	 * Test if the save button is disabled.
	 */
	public disabledSave(): boolean {
		return this.colorPickerIsOpen || this._commonUtilsService.isNullOrEmpty(this.newProfile.name) || this._commonUtilsService.isNull(this.newProfile.background);
	}
	
	/**
	 * Save/update profile.
	 */
	public save(): void {
		
		let profileTmp: Profile = {
			id: this.newProfile.id,
			background: this.newProfile.background,
			name: this.newProfile.name,
			avatar: this.newProfile.avatar
		};
		
		
		if (this.create)
			this.createProfile(profileTmp);
		else
			this.updateProfile(profileTmp);
	}
	
	/**
	 * Create new profile.
	 *
	 * @param profileTmp the profile to save
	 */
	private createProfile(profileTmp: Profile): void {
		
		this._profileResource.create(profileTmp).subscribe(
			response => {
				
				this.profile = response;
				this._toasterService.success( this._translate.instant("PROFILE.EDIT_MODAL.CREATED_TOASTER", { profile_name: this.profile.name } ) );
				this._ngbActiveModal.close(this.profile);
			},
			error => {
				
				let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
				
				if (errorAlert.status === HTTP_CONFLICT) {
					this._toasterService.error( this._translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
				}
				else {
					
					const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
					modalRef.componentInstance.text = this._translate.instant("PROFILE.EDIT_MODAL.CREATE_ERROR");
					modalRef.componentInstance.suggestions = undefined;
					modalRef.componentInstance.error = errorAlert;
					modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
					modalRef.componentInstance.showRetry = true;
					modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CANCEL_MODIFICATION");
					
					modalRef.result.then(
						() => { this.save(); },
						() => { this.cancel(); }
					);
				}
			}
		);
	}
	
	/**
	 * Update profile.
	 *
	 * @param profileTmp the profile to save
	 */
	private updateProfile(profileTmp: Profile): void {
		
		this._profileResource.update(profileTmp).subscribe(
			response => {
				
				this.profile = response;
				this._toasterService.success( this._translate.instant("PROFILE.EDIT_MODAL.UPDATED_TOASTER", { profile_name: this.profile.name } ) );
				this._ngbActiveModal.close(this.profile);
			},
			error => {
				
				let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
				
				if (errorAlert.status === HTTP_CONFLICT) {
					this._toasterService.error( this._translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
				}
				else {
					
					const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
					modalRef.componentInstance.text = this._translate.instant("PROFILE.EDIT_MODAL.UPDATE_ERROR");
					modalRef.componentInstance.suggestions = undefined;
					modalRef.componentInstance.error = errorAlert;
					modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
					modalRef.componentInstance.showRetry = true;
					modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CANCEL_MODIFICATION");
					
					modalRef.result.then(
						() => { this.save(); },
						() => { this.cancel(); }
					);
				}
			}
		);
	}
	
	/**
	 * Cancel the modal.
	 */
	public cancel(): void {
		this._ngbActiveModal.dismiss();
	}
	
}
