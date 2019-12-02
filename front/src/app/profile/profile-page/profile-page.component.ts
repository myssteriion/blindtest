import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/dto/profile.interface';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from 'src/app/interfaces/base/page.interface';
import {ProfileEditModalComponent} from "../profile-edit-modal/profile-edit-modal.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {OPACITY_ANIMATION} from "../../tools/constant";
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {TranslateService} from '@ngx-translate/core';

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
    private isLoaded: boolean;

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
     * The prefix name filter.
     */
    private prefixName: string;



    constructor(private _profileResource: ProfileResource,
                private _ngbModal: NgbModal,
                private _translate: TranslateService) {}

    ngOnInit(): void {

        this.isLoaded = false;
        this.showProfiles = false;
        this.prefixName = "";
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

        this._profileResource.findAllByNameStartingWith(this.prefixName, this.currentPage-1).subscribe(
            response => {
                this.page = response;
                this.showProfiles = true;
                this.isLoaded = true;
            },
            error => {

                let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };

                const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' } );
                modalRef.componentInstance.text = this._translate.instant("PROFILE.PAGE.LOAD_PROFILES_ERROR");
                modalRef.componentInstance.error = errorAlert;
                modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
                modalRef.componentInstance.showRetry = true;

                modalRef.result.then(
                    (result) => { this.loadProfiles(true); },
                    (reason) => { /* do nothing */ }
                );
            }
        );
    }

    /**
     * Open modal for create new profile.
     */
    private createProfile(): void {

        const modalRef = this._ngbModal.open(ProfileEditModalComponent, { backdrop: 'static' } );
        modalRef.componentInstance.create = true;

        modalRef.result.then(
            (result) => { this.loadProfiles(true); },
            (reason) => { /* do nothing */ }
        );
    }

}
