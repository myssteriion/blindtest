import {Component, OnInit} from '@angular/core';
import {HOME_PATH, SLIDE_ANIMATION} from '../../tools/constant';
import {ProfileStatisticsResource} from "../../resources/profile-statistics.resource";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Profile} from "../../interfaces/dto/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from "../../services/toaster.service";
import {ProfileResource} from "../../resources/profile.resource";
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {Router} from "@angular/router";

import {ToolsService} from "../../tools/tools.service";

/**
 * The profiles statistics view.
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

    public selectedProfiles: Profile[];
    public isLoaded: boolean = false;
    public users = [];
    public isLoading = true;
    public selectedUsers = [];

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

    /**
     * Get flux for avatar image
     * @param profile
     */
    private getFluxForImg(profile): string {
        return ToolsService.getFluxForImg(profile.avatar.flux);
    }

    /**
     * Get all existing players
     * @param page
     */
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

    /**
     * Get all players statistics
     */
    public getPlayersStatistics() {
        this.isLoaded = false;
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

    /**
     * Map profile with statistics
     * @param statistics
     */
    private mapProfile(statistics) {
        let userProfile = this.selectedUsers.find(selectedProfile => {
            return selectedProfile.id === statistics.id
        });
        if (userProfile !== undefined) {
            userProfile.statistics = statistics;
        } else {
            let message = this._translate.instant("STATISTICS.USER_STATISTICS_BINDING_KO", {user: userProfile.name});
            this._toasterService.error(message);
        }
    }
}
