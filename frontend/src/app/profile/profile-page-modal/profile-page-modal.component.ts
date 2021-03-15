import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Page } from "myssteriion-utils";
import { Profile } from 'src/app/interfaces/entity/profile';
import { ProfilesPerPage } from "../profile-page/common/profiles-per-page.enum";

/**
 * The profiles view.
 */
@Component({
	selector: "profile-page-modal",
	templateUrl: "./profile-page-modal.component.html",
	styleUrls: ["./profile-page-modal.component.scss"]
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
	public profilePerPage: ProfilesPerPage;
	
	
	
	constructor(private ngbActiveModal: NgbActiveModal) {}
	
	ngOnInit() {
		this.profilePerPage = ProfilesPerPage.FIFTEEN;
	}
	
	
	
	/**
	 * Close modal.
	 */
	public close(): void {
		this.ngbActiveModal.close();
	}
	
}
