import { Component, OnInit } from '@angular/core';
import { SLIDE_ANIMATION } from '../tools/constant';
import { StatisticsResource } from "../resources/statistics.resource";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfilePageModalComponent } from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import { Profile } from "../interfaces/dto/profile.interface";
import { TranslateService } from '@ngx-translate/core';
import { ToasterService } from "../services/toaster.service";

export var single = [
    {
      "name": "Germany",
      "value": 8940000
    },
    {
      "name": "USA",
      "value": 5000000
    },
    {
      "name": "France",
      "value": 7200000
    }
  ];

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

    single: any[];
    multi: any[];
  
    view: any[] = [700, 400];
  
    // options
    showXAxis = true;
    showYAxis = true;
    gradient = false;
    showLegend = true;
    showXAxisLabel = true;
    xAxisLabel = "Country";
    showYAxisLabel = true;
    yAxisLabel = "Population";
    colorScheme = {
      domain: ["#5AA454", "#A10A28", "#C7B42C", "#AAAAAA"]
    };

    
    private static MAX_PLAYERS: number = 4;
    public selectedProfiles: Profile[];
    public profileStats: {};

    constructor(private _statisticsResource: StatisticsResource,
        private _translate : TranslateService,
        private _toasterService: ToasterService,
        private _ngbModal: NgbModal) { 
            Object.assign(this, { single });
        }


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
            (result) => { this.getPlayersStatistics(); },
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

		if ( this.selectedProfiles.length >= StatisticsComponent.MAX_PLAYERS ) {
			let message = this._translate.instant( "STATISTICS.MODAL.MAX_USERS_ERROR", { max_users: StatisticsComponent.MAX_PLAYERS}  );
			this._toasterService.error(message);
		}
		else if (index !== -1) {
			let message = this._translate.instant( "STATISTICS.MODAL.DUPLICATE_USER_ERROR", { user_name: profile.name } );
			this._toasterService.error(message);
		}
		else {
			this.selectedProfiles.push(profile);
			let message = this._translate.instant( "STATISTICS.MODAL.USER_ADDED", { user_name: profile.name } );
			this._toasterService.success(message);
		}
    }


    public onSelect(event) {
        console.log(event);
    }

}
