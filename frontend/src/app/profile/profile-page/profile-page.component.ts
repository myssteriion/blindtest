import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { ModalService, Page, RoutingService } from "myssteriion-utils";
import { Profile } from "src/app/interfaces/entity/profile";
import { ProfileResource } from "src/app/resources/profile.resource";
import { OPACITY_ANIMATION } from "../../tools/constant";
import { HOME_ROUTE } from "../../tools/routing.constant";
import { ProfileEditModalComponent } from "../profile-edit-modal/profile-edit-modal.component";
import { ProfilesPerPage } from "./common/profiles-per-page.enum";

/**
 * The profiles view.
 */
@Component({
	selector: "profile-page",
	templateUrl: "./profile-page.component.html",
	styleUrls: ["./profile-page.component.css"],
	animations: [
		OPACITY_ANIMATION
	]
})
export class ProfilePageComponent implements OnInit {
	
	/**
	 * Profiles number per page.
	 */
	@Input()
	private profilesPerPage: ProfilesPerPage;
	
	/**
	 * If can create/update/delete profile.
	 */
	@Input()
	public canEdit: boolean;
	
	/**
	 * If can select profile card.
	 */
	@Input()
	public canSelect: boolean;
	
	/**
	 * On select profile card.
	 */
	@Output()
	public onSelect = new EventEmitter();
	
	/**
	 * If view is loaded.
	 */
	public isLoaded: boolean;
	
	/**
	 * Profiles page.
	 */
	public page: Page<Profile>;
	
	/**
	 * The current page.
	 */
	public currentPage: number;
	
	/**
	 * Show/hide profiles pages.
	 */
	public showProfiles: boolean;
	
	/**
	 * Show/hide pageable.
	 */
	public showPageable: boolean;
	
	/**
	 * The search name filter.
	 */
	public name: string;
	
	
	
	constructor(private profileResource: ProfileResource,
				private ngbModal: NgbModal,
				private translate: TranslateService,
				private routingService: RoutingService,
				private modalService: ModalService) {}
	
	ngOnInit(): void {
		
		this.isLoaded = false;
		this.showProfiles = false;
		this.name = "";
		this.loadProfiles(true);
	}
	
	
	
	/**
	 * Load profiles page.
	 *
	 * @param initPageNumber TRUE for force page number to 1
	 */
	public loadProfiles(initPageNumber: boolean): void {
		
		this.showProfiles = false;
		if (initPageNumber)
			this.currentPage = 1;
		
		this.profileResource.findAllBySearchName(this.name, this.currentPage-1, this.profilesPerPage).subscribe(
			response => {
				this.page = response;
				this.showProfiles = true;
				this.isLoaded = true;
				this.showPageable = this.page.totalPages > 1;
			},
			error => {
				
				let text: string = this.translate.instant("PROFILE.PAGE.LOAD_PROFILES_ERROR");
				let closeLabel: string = this.translate.instant("COMMON.GO_HOME");
				
				this.modalService.openErrorModal(text, error, true, closeLabel).then(
					() => { this.loadProfiles(true); },
					() => { this.routingService.goTo(HOME_ROUTE); }
				);
			}
		);
	}
	
	/**
	 * Open modal for create new profile.
	 */
	public createProfile(): void {
		
		const modalRef = this.ngbModal.open(ProfileEditModalComponent, { backdrop: 'static', size: 'md' } );
		modalRef.componentInstance.create = true;
		
		modalRef.result.then(
			() => { this.loadProfiles(true); },
			() => { /* do nothing */ }
		);
	}
	
	
	/**
	 * Get "col-x" css.
	 */
	public getCssCol(): string {
		
		var colCss: string = "col-";
		
		switch (this.profilesPerPage) {
			case ProfilesPerPage.FIFTEEN:	colCss += "4"; break;
			case ProfilesPerPage.SIXTEEN:	colCss += "3"; break;
		}
		
		return colCss;
	}
	
}
