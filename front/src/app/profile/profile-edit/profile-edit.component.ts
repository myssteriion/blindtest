import {Component, Input, OnInit} from '@angular/core';
import {Profile} from 'src/app/interfaces/dto/profile.interface';
import {Avatar} from 'src/app/interfaces/dto/avatar.interface';
import {AvatarResource} from 'src/app/resources/avatar.resource';
import {ToolsService} from "../../tools/tools.service";
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from "../../interfaces/base/page.interface";
import {ToasterService} from "../../services/toaster.service";
import {TranslateService} from '@ngx-translate/core';

declare var $: any;

/**
 * Profile creation/edition modal.
 */
@Component({
	selector: 'profile-edit',
	templateUrl: './profile-edit.component.html',
	styleUrls: ['./profile-edit.component.css']
})
export class ProfileEditComponent implements OnInit {

	/**
	 * The profile.
	 */
	@Input() profile: Profile;

	/**
	 * Creation or edition.
	 */
	@Input() create: boolean;

	/**
	 * The new profile.
	 */
	public newProfile: Profile;

	/**
	 * The avatars page.
	 */
	public page: Page<Avatar>;

	/**
	 * Show/hide avatars page.
	 */
	public showAvatars: boolean;

	/**
	 * Background ids.
	 */
	public backgroundIds = ["0", "1", "2", "3", "4"];



	constructor(private _avatarResource: AvatarResource,
				private _ngbActiveModal: NgbActiveModal,
				private _profileResource: ProfileResource,
				private _toasterService: ToasterService,
				private _translate: TranslateService) { }

	public ngOnInit() {

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
	public loadAvatars(pageNumber: number): void {

		this._avatarResource.findAllByNameStartingWith("", pageNumber-1).subscribe(
			response => { this.page = response; },
			error => { throw Error("can't find all avatars : " + error); }
		);
	}

	/**
	 * Show/hide avatars page.
	 */
	public showHideAvatars(): void {
		this.showAvatars = !this.showAvatars;
	}

	/**
	 * Gets the new avatar flux.
	 */
	public getCurrentFluxForImg(): string {
		return ToolsService.getFluxForImg(this.newProfile.avatar.flux);
	}

	/**
	 * Gets the flux for avatar.
	 *
	 * @param avatar the avatar
	 */
	public getFluxForImg(avatar: Avatar): string {

		ToolsService.verifyValue("avatar", avatar);

		return ToolsService.getFluxForImg(avatar.flux);
	}

	/**
	 * Gets background dynamic class.
	 *
	 * @param index the background id
	 */
	public getBackgroundClass(index: number): string {

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
	public selectAvatar(avatar: Avatar): void {

		ToolsService.verifyValue("avatar", avatar);

		this.newProfile.avatar = avatar;
		this.newProfile.avatarName = avatar.name;
	}

	/**
	 * Test if the save button is disabled.
	 */
	public disabledSave(): boolean {
		return ToolsService.isNullOrEmpty(this.newProfile.name) ||
			ToolsService.isNull(this.newProfile.avatar) ||
			ToolsService.isNullOrEmpty(this.newProfile.avatar.name) ||
			ToolsService.isNull(this.newProfile.avatar.flux) ||
			!this.newProfile.avatar.flux.fileExists;
	}

	/**
	 * Save/update profile.
	 */
	public save(): void {

		let profileTmp = {
			id: this.newProfile.id,
			background: this.newProfile.background,
			name: this.newProfile.name,
			avatarName: this.newProfile.avatarName,
		};


		if (this.create) {

			this._profileResource.create(profileTmp).subscribe(
				response => {

					this.profile = response;
					this._toasterService.success( this._translate.instant("PROFILE.EDIT.CREATED_TOASTER", { profile_name: this.profile.name } ) );
					this._ngbActiveModal.close(this.profile);
				},
				error => {
					new Error("can't create profile : " + error);
				}
			);
		}
		else {

			this._profileResource.update(profileTmp).subscribe(
				response => {

					this.profile = response;
					this._toasterService.success( this._translate.instant("PROFILE.EDIT.UPDATED_TOASTER", { profile_name: this.profile.name } ) );
					this._ngbActiveModal.close(this.profile);
				},
				error => {
					new Error("can't update profile : " + error);
				}
			);
		}
	}

	/**
	 * Cancel the modal.
	 */
	public cancel(): void {
		this._ngbActiveModal.dismiss();
	}

}
