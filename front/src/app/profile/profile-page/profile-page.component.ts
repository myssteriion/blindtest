import {Component, OnInit, Input} from '@angular/core';
import {Profile} from 'src/app/interfaces/profile.interface';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from 'src/app/interfaces/page.interface';
import {ProfileEditComponent} from "../profile-edit/profile-edit.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

/**
 * The profiles view.
 */
@Component({
    selector: 'profile-page',
    templateUrl: './profile-page.component.html',
    styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {

    /**
     * If can create/update/delete profile.
     */
    @Input() canUpdate: boolean;

    /**
     * Profiles page.
     */
    public page: Page<Profile>;

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

        this.showProfiles = false;
        this.prefixName = "";
        this.loadProfiles(1)
    }


    /**
     * Load profiles page.
     *
     * @param pageNumber the page number
     */
    public loadProfiles(pageNumber: number): void {

        this._profileResource.findAllByNameStartingWith(this.prefixName, pageNumber-1).subscribe(
            response => { this.page = response; this.showProfiles = true; },
            error => { console.log("can't find all profiles", error); }
        );
    }

    /**
     * Open modal for create new profile.
     */
    public createProfile(): void {

        const modalRef = this._ngbModal.open(ProfileEditComponent, { backdrop: 'static' } );
        modalRef.componentInstance.create = true;

        modalRef.result.then(
            (result) => { this.loadProfiles(1); },
            (reason) => { /* do nothing */ }
        );
    }

}
