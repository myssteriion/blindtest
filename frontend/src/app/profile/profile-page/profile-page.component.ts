import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/dto/profile.interface';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from 'src/app/interfaces/base/page.interface';
import {ProfileEditModalComponent} from "../profile-edit-modal/profile-edit-modal.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {HOME_PATH, OPACITY_ANIMATION} from "../../tools/constant";
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {TranslateService} from '@ngx-translate/core';
import {Router} from '@angular/router';

/**
 * The profiles view.
 */
@Component({
	selector: 'profile-page',
	templateUrl: './profile-page.component.html',
	styleUrls: ['./profile-page.component.css'],
	animations: [
		OPACITY_ANIMATION
	]
})
export class ProfilePageComponent implements OnInit {
	
	/**
	 * If can create/update/delete profile.
	 */
	@Input()
	private canEdit: boolean;
	
	/**
	 * If can select profile card.
	 */
	@Input()
	private canSelect: boolean;
	
	/**
	 * On select profile card.
	 */
	@Output()
	private onSelect = new EventEmitter();
	
	/**
	 * If view is loaded.
	 */
	public isLoaded: boolean;
	
	/**
	 * Profiles page.
	 */
	private page: Page<Profile>;
	
	/**
	 * The current page.
	 */
	private currentPage: number;
	
	/**
	 * Show/hide profiles pages.
	 */
	private showProfiles: boolean;
	
	/**
	 * Show/hide pageable.
	 */
	private showPageable: boolean;
	
	/**
	 * The search name filter.
	 */
	private name: string;
	
	
	
	constructor(private _profileResource: ProfileResource,
				private _ngbModal: NgbModal,
				private _translate: TranslateService,
				private _router: Router) {}
	
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
	private loadProfiles(initPageNumber: boolean): void {
		
		this.showProfiles = false;
		if (initPageNumber)
			this.currentPage = 1;
		
		this._profileResource.findAllBySearchName(this.name, this.currentPage-1).subscribe(
			response => {
				this.page = response;
				this.showProfiles = true;
				this.isLoaded = true;
				this.showPageable = this.page.totalPages > 1;
			},
			error => {
				
				console.log("e", error.status, error);
				let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
				
				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
				modalRef.componentInstance.text = this._translate.instant("PROFILE.PAGE.LOAD_PROFILES_ERROR");
				modalRef.componentInstance.suggestions = undefined;
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = true;
				modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.GO_HOME");
				
				modalRef.result.then(
					() => { this.loadProfiles(true); },
					() => { this._router.navigateByUrl(HOME_PATH); }
				);
			}
		);
	}
	
	/**
	 * Open modal for create new profile.
	 */
	private createProfile(): void {
		
		const modalRef = this._ngbModal.open(ProfileEditModalComponent, { backdrop: 'static', size: 'md' } );
		modalRef.componentInstance.create = true;
		
		modalRef.result.then(
			() => { this.loadProfiles(true); },
			() => { /* do nothing */ }
		);
	}
	
}
