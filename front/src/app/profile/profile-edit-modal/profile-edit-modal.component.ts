import {Component, Input, OnInit} from '@angular/core';
import {Profile} from 'src/app/interfaces/dto/profile.interface';
import {Avatar} from 'src/app/interfaces/dto/avatar.interface';
import {AvatarResource} from 'src/app/resources/avatar.resource';
import {ToolsService} from "../../tools/tools.service";
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from "../../interfaces/base/page.interface";
import {ToasterService} from "../../services/toaster.service";
import {TranslateService} from '@ngx-translate/core';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {HTTP_CONFLICT} from "../../tools/constant";

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
	private create: boolean;

	/**
	 * The new profile.
	 */
	private newProfile: Profile;

	/**
	 * The avatars page.
	 */
	private page: Page<Avatar>;

	/**
	 * Show/hide avatars page.
	 */
	private showAvatars: boolean;

	/**
	 * Background ids.
	 */
	private backgroundIds = [0, 1, 2, 3, 4];



	constructor(private _avatarResource: AvatarResource,
				private _ngbActiveModal: NgbActiveModal,
				private _profileResource: ProfileResource,
				private _toasterService: ToasterService,
				private _translate: TranslateService,
				private _ngbModal: NgbModal) { }

	public ngOnInit(): void {

		ToolsService.verifyValue("create", this.create);

		this.showAvatars = false;

		if (this.create) {

			this.newProfile = {
				id: null,
				background: 0,
				name: "",
				avatarName: "",
				avatar: { id: null, name: "", flux: { name: "", fileExists: false, contentFlux: "", contentType: "" } }
			};
		}
		else {

			ToolsService.verifyValue("profile", this.profile);
			ToolsService.verifyValue("profile.avatar", this.profile.avatar);

			this.newProfile = {
				id: this.profile.id,
				background: this.profile.background,
				name: this.profile.name,
				avatarName: this.profile.avatarName,
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

		this._avatarResource.findAllByNameStartingWith("", pageNumber-1).subscribe(
			response => { this.page = response; },
			error => {

				let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };

				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' } );
				modalRef.componentInstance.text = this._translate.instant("PROFILE.EDIT_MODAL.AVATAR_LOAD_ERROR");
				modalRef.componentInstance.suggestion = undefined;
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = true;
				modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CANCEL_MODIFICATION");

				modalRef.result.then(
					(result) => { this.loadAvatars(1); },
					(reason) => { this.cancel() }
				);
			}
		);
	}

	/**
	 * Show/hide avatars page.
	 */
	private showHideAvatars(): void {
		this.showAvatars = !this.showAvatars;
	}

	/**
	 * Gets the new avatar flux.
	 */
	private getCurrentFluxForImg(): string {
		return ToolsService.getFluxForImg(this.newProfile.avatar.flux);
	}

	/**
	 * Gets the flux for avatar.
	 *
	 * @param avatar the avatar
	 */
	private getFluxForImg(avatar: Avatar): string {

		ToolsService.verifyValue("avatar", avatar);

		return ToolsService.getFluxForImg(avatar.flux);
	}

	/**
	 * Gets background dynamic class.
	 *
	 * @param index the background id
	 */
	private getBackgroundClass(index: number): string {

		let cssClass = "profile-card-background-" + index;

		if (index === this.newProfile.background)
			cssClass += " profile-edit-background-active";

		return cssClass;
	}

	/**
	 * Select an avatar.
	 *
	 * @param avatar the avatar selected
	 */
	private selectAvatar(avatar: Avatar): void {

		ToolsService.verifyValue("avatar", avatar);

		this.newProfile.avatar = avatar;
		this.newProfile.avatarName = avatar.name;
	}

	/**
	 * Test if the save button is disabled.
	 */
	private disabledSave(): boolean {
		return ToolsService.isNullOrEmpty(this.newProfile.name) ||
			ToolsService.isNull(this.newProfile.avatar) ||
			ToolsService.isNullOrEmpty(this.newProfile.avatar.name) ||
			ToolsService.isNull(this.newProfile.avatar.flux) ||
			!this.newProfile.avatar.flux.fileExists;
	}

	/**
	 * Save/update profile.
	 */
	private save(): void {

		let profileTmp = {
			id: this.newProfile.id,
			background: this.newProfile.background,
			name: this.newProfile.name,
			avatarName: this.newProfile.avatarName,
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

				let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };

				if (errorAlert.status === HTTP_CONFLICT) {
					this._toasterService.error( this._translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
				}
				else {

					const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' });
					modalRef.componentInstance.text = this._translate.instant("PROFILE.EDIT_MODAL.CREATE_ERROR");
					modalRef.componentInstance.suggestion = undefined;
					modalRef.componentInstance.error = errorAlert;
					modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
					modalRef.componentInstance.showRetry = true;
					modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CANCEL_MODIFICATION");

					modalRef.result.then(
						(result) => { this.save(); },
						(reason) => { this.cancel(); }
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

				let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };

				if (errorAlert.status === HTTP_CONFLICT) {
					this._toasterService.error( this._translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
				}
				else {

					const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' });
					modalRef.componentInstance.text = this._translate.instant("PROFILE.EDIT_MODAL.UPDATE_ERROR");
					modalRef.componentInstance.suggestion = undefined;
					modalRef.componentInstance.error = errorAlert;
					modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
					modalRef.componentInstance.showRetry = true;
					modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CANCEL_MODIFICATION");

					modalRef.result.then(
						(result) => { this.save(); },
						(reason) => { this.cancel(); }
					);
				}
			}
		);
	}

	/**
	 * Cancel the modal.
	 */
	private cancel(): void {
		this._ngbActiveModal.dismiss();
	}

}
