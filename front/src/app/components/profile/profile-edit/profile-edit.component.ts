import { Component, Input, OnInit } from '@angular/core';
import { Profile } from 'src/app/interfaces/profile.interface';
import { Avatar } from 'src/app/interfaces/avatar.interface';
import { AvatarResource } from 'src/app/resources/avatar.resource';
import { ToolsService } from "../../../tools/tools.service";
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { faEye, faEyeSlash, faSyncAlt } from '@fortawesome/free-solid-svg-icons';
import { ProfileResource } from 'src/app/resources/profile.resource';

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

	avatars: Avatar[];

	showAvatars: boolean;

	faEye = faEye;
	faEyeSlash = faEyeSlash;
	faSyncAlt = faSyncAlt;



	constructor(private _avatarResource: AvatarResource,
				private _toolsService: ToolsService,
				private _ngbActiveModal: NgbActiveModal,
				private _profileResource: ProfileResource) { }

	public ngOnInit() {

		this._toolsService.verifyValue("create", this.create);

		this.showAvatars = false;

		if (this.create) {

			this.newProfile = {
				id: -1,
				name: "",
				avatar: { name: "", fileExists: false, contentType: "", flux: "" }
			};
		}
		else {

			this._toolsService.verifyValue("profile", this.profile);
			this._toolsService.verifyValue("profile.avatar", this.profile.avatar);

			this.newProfile = {
				id: this.profile.id,
				name: this.profile.name,
				avatar: this.profile.avatar
			};
		}

		this._loadAvatars();
	}



	public refreshAvatars(): void {

		this.showAvatars = false;
		this._avatarResource.refresh().subscribe(
			response => {
				this._loadAvatars();
			},
			error => {
				throw Error("can't refresh all avatars : " + error);
			}
		);
	}

	private _loadAvatars(): void {

		this._avatarResource.getAll().subscribe(
			response => {
				this.avatars = response.items;
			},
			error => {
				throw Error("can't find all avatars : " + error);
			}
		);
	}

	public showHideAvatars(): void {

		if (!this.showAvatars) {
			this.initCarousel();
		}
		this.showAvatars = !this.showAvatars;
	}

	private initCarousel(): void {
		let options = {
			dots: false,
			margin: 20,
			autoWidth: true
		};

		$(document).ready(function () {
			$(".owl-carousel").owlCarousel(options);
		});
	}

	public getCurrentAvatarFluxForImg(): string {
		return this._toolsService.getAvatarFluxForImg(this.newProfile.avatar);
	}

	public getAvatarFluxForImg(avatar: Avatar): string {

		this._toolsService.verifyValue("avatar", avatar);

		return this._toolsService.getAvatarFluxForImg(avatar);
	}

	public selectAvatar(avatar: Avatar): void {

		this._toolsService.verifyValue("avatar", avatar);

		this.newProfile.avatar = avatar;
	}

	public disabledSave(): boolean {
		return this._toolsService.isNullOrEmpty(this.newProfile.name) ||
		this._toolsService.isNull(this.newProfile.avatar) || 
		this._toolsService.isNullOrEmpty(this.newProfile.avatar.name) ||
		!this.newProfile.avatar.fileExists;
	}

	public save(): void {
		
		if (this.create) {

			this._profileResource.create(this.newProfile).subscribe(
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

			this._profileResource.update(this.newProfile).subscribe(
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
