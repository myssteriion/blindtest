import {Component, Input, OnInit} from '@angular/core';
import {Profile} from 'src/app/interfaces/profile.interface';
import {Avatar} from 'src/app/interfaces/avatar.interface';
import {AvatarResource} from 'src/app/resources/avatar.resource';
import {ToolsService} from "../../tools/tools.service";
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from "../../interfaces/page.interface";

declare var $: any;

@Component({
	selector: 'profile-edit',
	templateUrl: './profile-edit.component.html',
	styleUrls: ['./profile-edit.component.css']
})
export class ProfileEditComponent implements OnInit {

	@Input() profile: Profile;

	@Input() create: boolean;

	newProfile: Profile;

	page: Page<Avatar>;

	showAvatars: boolean;



	constructor(private _avatarResource: AvatarResource,
				private _toolsService: ToolsService,
				private _ngbActiveModal: NgbActiveModal,
				private _profileResource: ProfileResource) { }

	public ngOnInit() {

		this._toolsService.verifyValue("create", this.create);

		this.showAvatars = false;

		if (this.create) {

			this.newProfile = {
				id: null,
				name: "",
				avatarName: "",
				avatar: { id: null, name: "", flux: { name: "", fileExists: false, contentFlux: "", contentType: "" } }
			};
		}
		else {

			this._toolsService.verifyValue("profile", this.profile);
			this._toolsService.verifyValue("profile.avatar", this.profile.avatar);

			this.newProfile = {
				id: this.profile.id,
				name: this.profile.name,
				avatarName: this.profile.avatarName,
				avatar: this.profile.avatar
			};
		}

		this.loadAvatars(1);
	}



	public loadAvatars(pageNumber): void {

		this._avatarResource.findAllByNameStartingWith("", pageNumber-1).subscribe(
			response => {
				this.page = response;
			},
			error => {
				throw Error("can't find all avatars : " + error);
			}
		);
	}

	public showHideAvatars(): void {
		this.showAvatars = !this.showAvatars;
	}

	public getCurrentFluxForImg(): string {
		return this._toolsService.getFluxForImg(this.newProfile.avatar.flux);
	}

	public getFluxForImg(avatar: Avatar): string {

		this._toolsService.verifyValue("avatar", avatar);

		return this._toolsService.getFluxForImg(avatar.flux);
	}

	public selectAvatar(avatar: Avatar): void {

		this._toolsService.verifyValue("avatar", avatar);

		this.newProfile.avatar = avatar;
		this.newProfile.avatarName = avatar.name;
	}

	public disabledSave(): boolean {
		return this._toolsService.isNullOrEmpty(this.newProfile.name) ||
			this._toolsService.isNull(this.newProfile.avatar) ||
			this._toolsService.isNullOrEmpty(this.newProfile.avatar.name) ||
			this._toolsService.isNull(this.newProfile.avatar.flux) ||
			!this.newProfile.avatar.flux.fileExists;
	}

	public save(): void {

		let profileTmp = {
			id: this.newProfile.id,
			name: this.newProfile.name,
			avatarName: this.newProfile.avatarName,
		};


		if (this.create) {

			this._profileResource.create(profileTmp).subscribe(
				response => {
					this.profile = response;
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
					this._ngbActiveModal.close(this.profile);
				},
				error => {
					new Error("can't update profile : " + error);
				}
			);
		}
	}

	public cancel(): void {
		this._ngbActiveModal.dismiss();
	}

}
