import {Component, OnInit} from '@angular/core';
import {SLIDE_ANIMATION} from 'src/app/tools/constant';

/**
 * The profiles view.
 */
@Component({
	selector: 'profile-view',
	templateUrl: './profile-view.component.html',
	styleUrls: ['./profile-view.component.css'],
	animations: [
		SLIDE_ANIMATION
	]
})
export class ProfileViewComponent implements OnInit {
	
	/**
	 * The profiles number per page.
	 */
	private profilePerPage: ProfilesPerPage;
	
	
	
	constructor() { }
	
	ngOnInit(): void {
		this.profilePerPage = ProfilesPerPage.SIXTEEN;
	}
	
}
