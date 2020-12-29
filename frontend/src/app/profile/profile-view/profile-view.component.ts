import { Component } from '@angular/core';
import { SLIDE_ANIMATION } from 'src/app/tools/constant';
import { ProfilesPerPage } from "../profile-page/common/profiles-per-page.enum";

/**
 * The profiles view.
 */
@Component({
	templateUrl: "./profile-view.component.html",
	styleUrls: ["./profile-view.component.css"],
	animations: [
		SLIDE_ANIMATION
	]
})
export class ProfileViewComponent {
	
	/**
	 * The profiles number per page.
	 */
	public profilePerPage: ProfilesPerPage;
	
	
	
	constructor() {
		this.profilePerPage = ProfilesPerPage.SIXTEEN;
	}
	
}
