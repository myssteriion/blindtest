import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/entity/profile.interface';
import {Page} from 'src/app/interfaces/base/page.interface';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

/**
 * The profiles view.
 */
@Component({
	selector: 'profile-page-modal',
	templateUrl: './profile-page-modal.component.html',
	styleUrls: ['./profile-page-modal.component.css']
})
export class ProfilePageModalComponent implements OnInit {
	
	/**
	 * Profiles page.
	 */
	@Input()
	public page: Page<Profile>;
	
	/**
	 * The modal title.
	 */
	@Input()
	public title: string;
	
	/**
	 * On select profile card.
	 */
	@Output()
	public onSelect = new EventEmitter();
	
	/**
	 * The profiles number per page.
	 */
	private profilePerPage: ProfilesPerPage;
	
	
	
	constructor(private _ngbActiveModal: NgbActiveModal) {}
	
	ngOnInit() {
		this.profilePerPage = ProfilesPerPage.FIFTEEN;
	}
	
	
	
	/**
	 * Close modal.
	 */
	public close(): void {
		this._ngbActiveModal.close();
	}
	
}
