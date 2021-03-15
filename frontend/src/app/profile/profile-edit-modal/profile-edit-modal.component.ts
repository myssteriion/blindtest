import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { CommonUtilsService, HTTP_CONFLICT, ModalService, Page, ToasterService } from "myssteriion-utils";
import { Avatar } from 'src/app/interfaces/entity/avatar';
import { Profile } from 'src/app/interfaces/entity/profile';
import { AvatarResource } from 'src/app/resources/avatar.resource';
import { ProfileResource } from 'src/app/resources/profile.resource';
import { UtilsService } from "../../services/utils.service";
import { DEFAULT_BACKGROUND } from "../../tools/constant";

declare var $: any;

/**
 * Profile creation/edition modal.
 */
@Component({
	templateUrl: "./profile-edit-modal.component.html",
	styleUrls: ["./profile-edit-modal.component.scss"]
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
	public avatarsPage: Page<Avatar>;
	
	/**
	 * If avatars page is empty.
	 */
	public avatarsPageIsEmpty: boolean;
	
	/**
	 * Show/hide pageable.
	 */
	public showAvatarsPageable: boolean;
	
	/**
	 * The search name filter.
	 */
	public searchName: string;
	
	/**
	 * If the color picker is open.
	 */
	public colorPickerIsOpen: boolean;
	
	/**
	 * Avatars number per page.
	 */
	private avatarsPerPage: string = "12";
	
	
	
	constructor(private avatarResource: AvatarResource,
				private ngbActiveModal: NgbActiveModal,
				private profileResource: ProfileResource,
				private toasterService: ToasterService,
				private translate: TranslateService,
				private ngbModal: NgbModal,
				private commonUtilsService: CommonUtilsService,
				private utilsService: UtilsService,
				private modalService: ModalService) { }
	
	public ngOnInit(): void {
		
		this.avatarsPageIsEmpty = true;
		this.showAvatarsPageable = false;
		this.searchName = "";
		this.colorPickerIsOpen = false;
		
		if (this.create) {
			
			this.newProfile = new Profile();
			this.newProfile.id = null;
			this.newProfile.background = DEFAULT_BACKGROUND;
			this.newProfile.name = "";
			this.newProfile.avatar = null;
		}
		else {
			
			this.newProfile = new Profile();
			this.newProfile.id = this.profile.id;
			this.newProfile.background = this.profile.background;
			this.newProfile.name = this.profile.name;
			this.newProfile.avatar = this.profile.avatar;
		}
		
		this.loadAvatars(1);
	}
	
	
	
	/**
	 * Load avatars page.
	 *
	 * @param pageNumber the page number
	 */
	public loadAvatars(pageNumber: number): void {
		
		this.avatarResource.findAllBySearchName(this.searchName, pageNumber-1, this.avatarsPerPage).subscribe(
			response => {
				
				this.avatarsPage = response;
				this.avatarsPageIsEmpty = this.avatarsPage.empty;
				this.showAvatarsPageable = this.avatarsPage.totalPages > 1;
			},
			error => {
				
				let text: string = this.translate.instant("PROFILE.EDIT_MODAL.AVATAR_LOAD_ERROR");
				let closeLabel: string = this.translate.instant("COMMON.CANCEL_MODIFICATION");
				
				this.modalService.openErrorModal(text, error, true, closeLabel).then(
					() => { this.loadAvatars(1); },
					() => { this.cancel() }
				);
			}
		);
	}
	
	/**
	 * Get the B64 image from avatar. If the avatar is null, "not-found" image is used.
	 *
	 * @param avatar the avatar
	 * @return B64 image from avatar
	 */
	public getImgFromAvatar(avatar: Avatar): string {
		return this.utilsService.getImgFromAvatar(avatar);
	}
	
	/**
	 * Select an avatar.
	 *
	 * @param avatar the avatar to select
	 */
	public selectAvatar(avatar: Avatar): void {
		this.newProfile.avatar = avatar;
	}
	
	/**
	 * Test if the save button must be disable.
	 *
	 * @return TRUE if the save button must be disable, FALSE otherwise
	 */
	public saveButtonIsDisable(): boolean {
		return this.colorPickerIsOpen || this.commonUtilsService.isNullOrEmpty(this.newProfile.name) || this.commonUtilsService.isNull(this.newProfile.background);
	}
	
	/**
	 * Save/update profile.
	 */
	public save(): void {
		
		let profileTmp: Profile = new Profile();
		profileTmp.id = this.newProfile.id;
		profileTmp.background = this.newProfile.background;
		profileTmp.name = this.newProfile.name;
		profileTmp.avatar = this.newProfile.avatar;
		
		
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
		
		this.profileResource.create(profileTmp).subscribe(
			response => {
				
				this.profile = response;
				this.toasterService.success( this.translate.instant("PROFILE.EDIT_MODAL.CREATED_TOASTER", { profile_name: this.profile.name } ) );
				this.ngbActiveModal.close(this.profile);
			},
			error => {
				
				if (error.status === HTTP_CONFLICT) {
					this.toasterService.error( this.translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
				}
				else {
					
					let text: string = this.translate.instant("PROFILE.EDIT_MODAL.CREATE_ERROR");
					let closeLabel: string = this.translate.instant("COMMON.CANCEL_MODIFICATION");
					
					this.modalService.openErrorModal(text, error, true, closeLabel).then(
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
		
		this.profileResource.update(profileTmp).subscribe(
			response => {
				
				this.profile = response;
				this.toasterService.success( this.translate.instant("PROFILE.EDIT_MODAL.UPDATED_TOASTER", { profile_name: this.profile.name } ) );
				this.ngbActiveModal.close(this.profile);
			},
			error => {
				
				if (error.status === HTTP_CONFLICT) {
					this.toasterService.error( this.translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
				}
				else {
					
					let text: string = this.translate.instant("PROFILE.EDIT_MODAL.UPDATE_ERROR");
					let closeLabel: string = this.translate.instant("COMMON.CANCEL_MODIFICATION");
					
					this.modalService.openErrorModal(text, error, true, closeLabel).then(
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
		this.ngbActiveModal.dismiss();
	}
	
}
