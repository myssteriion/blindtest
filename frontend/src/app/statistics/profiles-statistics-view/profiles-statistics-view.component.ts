import {Component, OnInit} from '@angular/core';
import {HOME_PATH, SLIDE_ANIMATION} from '../../tools/constant';
import {ProfileStatisticsResource} from "../../resources/profile-statistics.resource";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Profile} from "../../interfaces/entity/profile.interface";
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

    public isLoaded: boolean = false;
    public users: Profile[] = [];
    public isLoading: boolean = true;
    public selectedUsers: Profile[] = [];
    public showGeneralGraph: boolean = true;

    constructor(private _profileStatisticsResource: ProfileStatisticsResource,
                private _translate: TranslateService,
                private _toasterService: ToasterService,
                private _ngbModal: NgbModal,
                private _profileResource: ProfileResource,
                private _router: Router) {
    }

    ngOnInit() {
        this.getAllPlayers(0);
    }

    /**
     * Get image from avatar.
	 *
     * @param profile
     */
    private getImgFromAvatar(profile): string {
        return ToolsService.getImgFromAvatar(profile.avatar);
    }

    /**
     * Get all existing players.
	 *
     * @param pageNumber
     */
    private getAllPlayers(pageNumber) {
        this._profileResource.findAllBySearchName('', pageNumber, ProfilesPerPage.FIFTEEN).subscribe(
        	response => {
        	
				response.content.forEach(user => {
					this.users.push(user)
				});
				if (!response.last) {
					this.getAllPlayers(pageNumber + 1);
				} else {
					this.isLoading = false;
				}
			},
			error => {
	
				let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
		
				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
				modalRef.componentInstance.text = this._translate.instant("PROFILE.PAGE.LOAD_PROFILES_ERROR");
				modalRef.componentInstance.suggestion = undefined;
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = true;
				modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.GO_HOME");
	
				modalRef.result.then(
					() => { this.getAllPlayers(pageNumber); },
					() => { this._router.navigateByUrl(HOME_PATH); }
				);
        	}
        )
    }

    /**
     * Get all players statistics.
     */
    public getPlayersStatistics() {
        this.isLoaded = false;
        this._profileStatisticsResource.getStatisticsForProfile(this.selectedUsers).subscribe(
            response => {
                this.isLoaded = this.selectedUsers.length !== 0;
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
     * Display graphs on appropriata tab.
	 *
     * @param event
     */
    public onTabClick(event) {
        this.showGeneralGraph = event.index === 0;
    }

    /**
     * Map profile with statistics.
	 *
     * @param statistics
     */
    private mapProfile(statistics) {
        let userProfile = this.selectedUsers.find(selectedProfile => {
            return selectedProfile.id === statistics.id
        });
        if (userProfile !== undefined) {
            userProfile.profileStat = statistics;
        } else {
            let message = this._translate.instant("STATISTICS.USER_STATISTICS_BINDING_KO", {user: userProfile.name});
            this._toasterService.error(message);
        }
    }
}
