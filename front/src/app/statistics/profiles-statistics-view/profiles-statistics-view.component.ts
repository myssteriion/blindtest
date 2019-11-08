import { Component, OnInit } from '@angular/core';
import { SLIDE_ANIMATION } from '../../tools/constant';
import { ProfileStatisticsResource } from "../../resources/profile-statistics.resource";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfilePageModalComponent } from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import { Profile } from "../../interfaces/dto/profile.interface";
import { TranslateService } from '@ngx-translate/core';
import { ToasterService } from "../../services/toaster.service";

/**
 * The statistics view.
 */
@Component({
    selector: 'profiles-statistics-view',
    templateUrl: './profiles-statistics-view.component.html',
    styleUrls: ['./profiles-statistics-view.component.css'],
    animations: [
        SLIDE_ANIMATION
    ]
})
export class ProfilesStatisticsViewComponent implements OnInit {

    private static MAX_PLAYERS: number = 3;
    public selectedProfiles: Profile[];
    public isLoaded: boolean = false;

    constructor(private _profileStatisticsResource: ProfileStatisticsResource,
        private _translate: TranslateService,
        private _toasterService: ToasterService,
        private _ngbModal: NgbModal) { }

    ngOnInit() {
        this.selectedProfiles = [];
    }

    public getPlayersStatistics() {
        this._profileStatisticsResource.getStatisticsForProfile(this.selectedProfiles).subscribe(
            response => {
                this.isLoaded = true;
                response.content.forEach(user =>
                    this.mapProfile(user)
                )
            },

            error => { throw Error("can't retrieve statistics : " + error); }
        );
    }

    private mapProfile(statistics) {
        let userProfile = this.selectedProfiles.find(selectedProfile => { return selectedProfile.id === statistics.id })
        if (userProfile !== undefined) {
            userProfile.statistics = statistics;
        } else {
            let message = this._translate.instant("STATISTICS.USER_STATISTICS_BINDING_KO", { user: userProfile.name });
            this._toasterService.error(message);
        }
    }

	/**
	 * Open modal for profile selection.
	 */
    public select(): void {
        // Reset profiles on new selection modal
        this.selectedProfiles = [];
        const modalRef = this._ngbModal.open(ProfilePageModalComponent, { backdrop: 'static', size: 'xl' });
        modalRef.componentInstance.title = this._translate.instant("STATISTICS.MODAL.TITLE");
        modalRef.componentInstance.canEdit = false;
        modalRef.componentInstance.canSelect = true;
        modalRef.componentInstance.canDeselect = false;
        modalRef.componentInstance.onSelect.subscribe(
            profile => { this.selectProfile(profile); }
        );

        modalRef.result.then(
            (result) => {
                this.getPlayersStatistics();
            },
            (reason) => { /* do nothing */ }
        );
    }

    /**
	 * Select profile.
	 *
	 * @param profile
	 */
    public selectProfile(profile: Profile) {
        let index: number = this.selectedProfiles.findIndex((p) => p.id === profile.id);

        if (this.selectedProfiles.length >= ProfilesStatisticsViewComponent.MAX_PLAYERS) {
            let message = this._translate.instant("STATISTICS.MODAL.MAX_USERS_ERROR", { max_users: ProfilesStatisticsViewComponent.MAX_PLAYERS });
            this._toasterService.error(message);
        }
        else if (index !== -1) {
            let message = this._translate.instant("STATISTICS.MODAL.DUPLICATE_USER_ERROR", { user_name: profile.name });
            this._toasterService.error(message);
        }
        else {
            this.selectedProfiles.push(profile);
            let message = this._translate.instant("STATISTICS.MODAL.USER_ADDED", { user_name: profile.name });
            this._toasterService.success(message);
        }
    }

    public getActiveClass() {
        if (this.selectedProfiles.length === 1) {
            return 'col-12'
        } else if (this.selectedProfiles.length === 2) {
            return 'col-6'
        } if (this.selectedProfiles.length === 3) {
            return 'col-4'
        } if (this.selectedProfiles.length === 4) {
            return 'col-3'
        }
    }

}
