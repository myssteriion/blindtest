import { Component, OnInit } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { ModalService, RoutingService, ToasterService } from "myssteriion-utils";
import { Profile } from "../../interfaces/entity/profile";
import { ProfilesPerPage } from "../../profile/profile-page/common/profiles-per-page.enum";
import { ProfileStatisticsResource } from "../../resources/profile-statistics.resource";
import { ProfileResource } from "../../resources/profile.resource";
import { UtilsService } from "../../services/utils.service";
import { SLIDE_ANIMATION } from "../../tools/constant";
import { HOME_ROUTE } from "../../tools/routing.constant";

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
	
	constructor(private profileStatisticsResource: ProfileStatisticsResource,
				private translate: TranslateService,
				private toasterService: ToasterService,
				private ngbModal: NgbModal,
				private profileResource: ProfileResource,
				private routingService: RoutingService,
				private utilsService: UtilsService,
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
		return this.utilsService.getImgFromAvatar(profile.avatar);
	}
	
	/**
	 * Get all existing players.
	 *
	 * @param pageNumber
	 */
	private getAllPlayers(pageNumber: number) {
		this.profileResource.findAllBySearchName('', pageNumber, ProfilesPerPage.FIFTEEN).subscribe(
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
					() => { this.routingService.goTo(HOME_ROUTE); }
				);
			}
		)
	}
	
	/**
	 * Get all players statistics.
	 */
	public getPlayersStatistics() {
		this.isLoaded = false;
		this.profileStatisticsResource.getStatisticsForProfile(this.selectedUsers).subscribe(
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
			this.toasterService.error(message);
		}
	}
}
