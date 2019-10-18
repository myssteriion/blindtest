import {Component, OnInit} from '@angular/core';
import {Profile} from "../../interfaces/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from "../../services/toaster.service";
import {ToolsService} from 'src/app/tools/tools.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfilePageModalComponent} from 'src/app/profile/profile-page-modal/profile-page-modal.component';

/**
 * The profiles view.
 */
@Component({
    selector: 'game-new-view',
    templateUrl: './game-new-view.component.html',
    styleUrls: ['./game-new-view.component.css']
})
export class GameNewViewComponent implements OnInit {

    /**
     * Durations list.
     */
    public durations: string[] = ["VERY_SHORT", "SHORT", "NORMAL", "LONG", "VERY_LONG"];

    /**
     * Selected duration.
     */
    public selectedDuration: string;

	/**
	 * Players profiles and empty names.
	 */
	public playersProfiles: Profile[];

    private static MAX_PLAYERS: number = 12;



    constructor(private _translate : TranslateService,
                private _toasterService: ToasterService,
				private _ngbModal: NgbModal) {}

    ngOnInit() {
        this.playersProfiles = [];
        this.selectedDuration = this.durations[2];
    }



    /**
     * Select duration.
     *
     * @param index
     */
    public selectDuration(index: number) {
        this.selectedDuration = this.durations[index];
    }

	/**
	 * Gets empty players.
	 */
	public getEmptyNames(): string[] {

		let index = this.playersProfiles.length + 1;
		var emptyNames = [];

		while (index <= GameNewViewComponent.MAX_PLAYERS) {
			emptyNames.push( this._translate.instant("GAME.NEW.PLAYER") + " " + index );
			index++;
		}

		return emptyNames;
	}

	/**
	 * Deselect profile.
	 *
	 * @param profile
	 */
	public deselectProfile(profile: Profile) {
		let index: number = this.playersProfiles.findIndex((p) => p.id === profile.id);
		if (index !== -1)
			this.playersProfiles.splice(index, 1);
	}

	/**
	 * Select profile.
	 *
	 * @param profile
	 */
	public selectProfile(profile: Profile) {

		let index: number = this.playersProfiles.findIndex((p) => p.id === profile.id);
		console.log("array", this.playersProfiles);
		console.log("profile", profile);
		console.log("index", index);

		if ( this.playersProfiles.length >= GameNewViewComponent.MAX_PLAYERS ) {
			let message = this._translate.instant( "GAME.NEW.MAX_PLAYERS_ERROR", { max_players: GameNewViewComponent.MAX_PLAYERS}  );
			this._toasterService.error(message);
		}
		else if (index !== -1) {
			let message = this._translate.instant( "GAME.NEW.DUPLICATE_PLAYERS_ERROR", { player_name: profile.name } );
			this._toasterService.error(message);
		}
		else {
			this.playersProfiles.push(profile);
			let message = this._translate.instant( "GAME.NEW.PLAYERS_ADDED", { player_name: profile.name } );
			this._toasterService.success(message);
		}
	}

	/**
	 * Start game.
	 */
	public launchGame(): void {
		console.log("launch !");
	}

	/**
	 * Disabled lauch game button.
	 */
	public launchGameIsDisabled(): boolean {
		return ToolsService.isNullOrEmpty(this.selectedDuration) || this.playersProfiles.length < 2;
	}

	/**
	 * Open modal for edit profile and emit it.
	 */
	public select(): void {

		const modalRef = this._ngbModal.open(ProfilePageModalComponent, { backdrop: 'static', size: 'xl' } );
		modalRef.componentInstance.canEdit = false;
		modalRef.componentInstance.canSelect = true;
		modalRef.componentInstance.canDeselect = false;
		modalRef.componentInstance.onSelect.subscribe(
			(profile) => {
				this.selectProfile(profile);
			}
		);

		modalRef.result.then(
			(result) => { /* do nothing */ },
			(reason) => { /* do nothing */ }
		);
	}

}
