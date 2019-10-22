import { Component, OnInit } from '@angular/core';
import { SLIDE_ANIMATION } from '../tools/constant';
import { StatisticsResource } from "../resources/statistics.resource";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfilePageModalComponent } from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import { Profile } from "../interfaces/dto/profile.interface";


/**
 * The statistics view.
 */
@Component({
    selector: 'statistics-view',
    templateUrl: './statistics.component.html',
    styleUrls: ['./statistics.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class StatisticsComponent implements OnInit {

    public selectedProfiles: Profile[];
    public profileStats: {}

    constructor(private _statisticsResource: StatisticsResource,
        private _ngbModal: NgbModal) { }


    ngOnInit() {
        this.selectedProfiles = [];
    }

    public getPlayersStatistics() {
        this._statisticsResource.getStatisticsForProfile(this.selectedProfiles).subscribe(
            response => { this.profileStats = response; console.log("response", response) },

            error => { throw Error("can't retrieve statistics : " + error); }
        );

    }

	/**
	 * Open modal for profile selection.
	 */
    public select(): void {
        const modalRef = this._ngbModal.open(ProfilePageModalComponent, { backdrop: 'static', size: 'xl' });
        modalRef.componentInstance.canEdit = false;
        modalRef.componentInstance.canSelect = true;
        modalRef.componentInstance.canDeselect = false;
        modalRef.componentInstance.onSelect.subscribe(
            profile => { this.selectProfile(profile); }
        );

        modalRef.result.then(
            (result) => { /* do nothing */ },
            (reason) => { /* do nothing */ }
        );
    }

    /**
	 * Select profile.
	 *
	 * @param profile
	 */
    public selectProfile(profile: Profile) {
        this.selectedProfiles.push(profile);
    }


}
