import {Component, OnInit} from '@angular/core';
import { ProfilesPerPage } from "../../profile/profile-page/common/profiles-per-page.enum";
import {HOME_PATH, SLIDE_ANIMATION} from '../../tools/constant';
import {ProfileStatisticsResource} from "../../resources/profile-statistics.resource";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Profile} from "../../interfaces/entity/profile";
import {TranslateService} from '@ngx-translate/core';
import {ProfileResource} from "../../resources/profile.resource";
import {Router} from "@angular/router";
import {UtilsService} from "../../services/utils.service";
import { CommonUtilsService, ModalService, ToasterService } from "myssteriion-utils";

/**
 * The profiles statistics view.
 */
@Component({
    selector: "profiles-statistics-view",
    templateUrl: "./profiles-statistics-view.component.html",
    styleUrls: ["./profiles-statistics-view.component.css"],
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
                private translate: TranslateService,
                private _toasterService: ToasterService,
                private _ngbModal: NgbModal,
                private _profileResource: ProfileResource,
                private _router: Router,
				private _utilsService: UtilsService,
				private modalService: ModalService) { }

    ngOnInit() {
        this.getAllPlayers(0);
    }

    /**
     * Get image from avatar.
	 *
     * @param profile
     */
    private getImgFromAvatar(profile: Profile): string {
        return this._utilsService.getImgFromAvatar(profile.avatar);
    }

    /**
     * Get all existing players.
	 *
     * @param pageNumber
     */
    private getAllPlayers(pageNumber: number) {
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
	
				let text: string = this.translate.instant("PROFILE.PAGE.LOAD_PROFILES_ERROR");
				let closeLabel: string = this.translate.instant("COMMON.GO_HOME");
				
				this.modalService.openErrorModal(text, error, true, closeLabel).then(
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
    public onTabClick(event: any) {
        this.showGeneralGraph = event.index === 0;
    }

    /**
     * Map profile with statistics.
	 *
     * @param statistics
     */
    private mapProfile(statistics: any) {
        let userProfile = this.selectedUsers.find(selectedProfile => {
            return selectedProfile.id === statistics.id
        });
        if (userProfile !== undefined) {
            userProfile.profileStat = statistics;
        } else {
            let message = this.translate.instant("STATISTICS.USER_STATISTICS_BINDING_KO", {user: userProfile.name});
            this._toasterService.error(message);
        }
    }
}
