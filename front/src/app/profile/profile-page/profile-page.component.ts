import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/dto/profile.interface';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from 'src/app/interfaces/base/page.interface';
import {ProfileEditComponent} from "../profile-edit/profile-edit.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {OPACITY_ANIMATION} from "../../tools/constant";

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
    @Input() canEdit: boolean;

    /**
     * If can select profile card.
     */
    @Input() canSelect: boolean;

    /**
     * On select profile card.
     */
    @Output() onSelect = new EventEmitter();

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
     * The prefix name filter.
     */
    public prefixName: string;



    constructor(private _profileResource: ProfileResource,
                private _ngbModal: NgbModal) {}

    ngOnInit() {

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
    public loadProfiles(initPageNumber: boolean): void {

        this.showProfiles = false;
        if (initPageNumber)
            this.currentPage = 1;

        this._profileResource.findAllByNameStartingWith(this.prefixName, this.currentPage-1).subscribe(
            response => { this.page = response; this.showProfiles = true; this.isLoaded = true; },
            error => { throw Error("can't find all profiles : " + JSON.stringify(error)); }
        );
    }

    /**
     * Open modal for create new profile.
     */
    public createProfile(): void {

        const modalRef = this._ngbModal.open(ProfileEditComponent, { backdrop: 'static' } );
        modalRef.componentInstance.create = true;

        modalRef.result.then(
            (result) => { this.loadProfiles(true); },
            (reason) => { /* do nothing */ }
        );
    }

}
