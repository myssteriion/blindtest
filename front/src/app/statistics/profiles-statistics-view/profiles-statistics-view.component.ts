import {Component, OnInit} from '@angular/core';
import {HOME_PATH, SLIDE_ANIMATION} from '../../tools/constant';
import {ProfileStatisticsResource} from "../../resources/profile-statistics.resource";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfilePageModalComponent} from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import {Profile} from "../../interfaces/dto/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from "../../services/toaster.service";
import {ProfileResource} from "../../resources/profile.resource";
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {Router} from "@angular/router";

import {ToolsService} from "../../tools/tools.service";

/**
 * The theme comparison view.
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

    public users = [];
    isLoading = true;
    selectedUsers = [];

    constructor(private _profileStatisticsResource: ProfileStatisticsResource,
                private _translate: TranslateService,
                private _toasterService: ToasterService,
                private _ngbModal: NgbModal,
                private _profileResource: ProfileResource,
                private _router: Router) {
    }

    ngOnInit() {
        this.selectedProfiles = [];
        this.getAllPlayers(0);
    }

    public onChange() {
        console.log("users", this.selectedUsers)
        this.getPlayersStatistics()
    }

    private getFluxForImg(profile): string {
        return ToolsService.getFluxForImg(profile.avatar.flux);
    }

    private getAllPlayers(page) {
        this._profileResource.findAllByNameStartingWith('', page).subscribe(response => {
            response.content.forEach(user => {
                this.users.push(user)
            });
            if (!response.last) {
                this.getAllPlayers(page + 1);
            } else {
                this.isLoading = false;
            }
        }, error => {
            let errorAlert: ErrorAlert = {status: error.status, name: error.name, error: error.error};

            const modalRef = this._ngbModal.open(ErrorAlertModalComponent, {backdrop: 'static', size: 'lg'});
            modalRef.componentInstance.text = this._translate.instant("PROFILE.PAGE.LOAD_PROFILES_ERROR");
            modalRef.componentInstance.suggestion = undefined;
            modalRef.componentInstance.error = errorAlert;
            modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
            modalRef.componentInstance.showRetry = true;
            modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.GO_HOME");

            modalRef.result.then(
                (result) => {
                    this.getAllPlayers(page);
                },
                (reason) => {
                    this._router.navigateByUrl(HOME_PATH);
                }
            );
        })
    }

    public getPlayersStatistics() {
        this._profileStatisticsResource.getStatisticsForProfile(this.selectedUsers).subscribe(
            response => {
                this.isLoaded = true;
                response.content.forEach(user =>
                    this.mapProfile(user)
                )
            },

            error => {
                throw Error("can't retrieve statistics : " + error);
            }
        );
    }

    private mapProfile(statistics) {
        let userProfile = this.selectedUsers.find(selectedProfile => {
            return selectedProfile.id === statistics.id
        });
        console.log("userProfile", userProfile)
        if (userProfile !== undefined) {
            userProfile.statistics = statistics;
        } else {
            let message = this._translate.instant("STATISTICS.USER_STATISTICS_BINDING_KO", {user: userProfile.name});
            this._toasterService.error(message);
        }
    }

    /**
     * Open modal for profile selection.
     */
    public select(): void {
        // Reset profiles on new selection modal
        this.selectedProfiles = [];
        const modalRef = this._ngbModal.open(ProfilePageModalComponent, {backdrop: 'static', size: 'xl'});
        modalRef.componentInstance.title = this._translate.instant("STATISTICS.MODAL.TITLE");
        modalRef.componentInstance.canEdit = false;
        modalRef.componentInstance.canSelect = true;
        modalRef.componentInstance.canDeselect = false;
        modalRef.componentInstance.onSelect.subscribe(
            profile => {
                this.selectProfile(profile);
            }
        );

        modalRef.result.then(
            (result) => {
                this.getPlayersStatistics();
            },
            (reason) => { /* do nothing */
            }
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
            let message = this._translate.instant("STATISTICS.MODAL.MAX_USERS_ERROR", {max_users: ProfilesStatisticsViewComponent.MAX_PLAYERS});
            this._toasterService.error(message);
        } else if (index !== -1) {
            let message = this._translate.instant("STATISTICS.MODAL.DUPLICATE_USER_ERROR", {user_name: profile.name});
            this._toasterService.error(message);
        } else {
            this.selectedProfiles.push(profile);
            let message = this._translate.instant("STATISTICS.MODAL.USER_ADDED", {user_name: profile.name});
            this._toasterService.success(message);
        }
    }

    public getActiveClass() {
        if (this.selectedProfiles.length === 1) {
            return 'col-12'
        } else if (this.selectedProfiles.length === 2) {
            return 'col-6'
        }
        if (this.selectedProfiles.length === 3) {
            return 'col-4'
        }
        if (this.selectedProfiles.length === 4) {
            return 'col-3'
        }
    }
}
