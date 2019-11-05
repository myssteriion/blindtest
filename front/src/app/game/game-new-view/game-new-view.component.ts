import {Component, OnInit} from '@angular/core';
import {Profile} from "../../interfaces/dto/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from "../../services/toaster.service";
import {ToolsService} from 'src/app/tools/tools.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfilePageModalComponent} from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import {SLIDE_ANIMATION} from "../../tools/constant";
import {NewGame} from "../../interfaces/game/newgame.interface";
import {GameResource} from "../../resources/game.resource";
import {Router} from '@angular/router';

/**
 * The new game view.
 */
@Component({
    selector: 'game-new-view',
    templateUrl: './game-new-view.component.html',
    styleUrls: ['./game-new-view.component.css'],
	animations: [
		SLIDE_ANIMATION
	]
})
export class GameNewViewComponent implements OnInit {

    /**
     * Durations list.
     */
    public durations = [Duration.VERY_SHORT, Duration.SHORT, Duration.NORMAL, Duration.LONG, Duration.VERY_LONG];

    /**
     * Selected duration.
     */
    public selectedDuration: Duration;

	/**
	 * Players profiles and empty names.
	 */
	public playersProfiles: Profile[];

	/**
	 * The max players number.
	 */
    private static MAX_PLAYERS: number = 12;



    constructor(private _translate : TranslateService,
                private _toasterService: ToasterService,
				private _ngbModal: NgbModal,
				private _gameResource: GameResource,
				private _router: Router) {}

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
			emptyNames.push( this._translate.instant("GAME.NEW_VIEW.PLAYER") + " " + index );
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

		if ( this.playersProfiles.length >= GameNewViewComponent.MAX_PLAYERS ) {
			let message = this._translate.instant( "GAME.NEW_VIEW.MAX_PLAYERS_ERROR", { max_players: GameNewViewComponent.MAX_PLAYERS}  );
			this._toasterService.error(message);
		}
		else if (index !== -1) {
			let message = this._translate.instant( "GAME.NEW_VIEW.DUPLICATE_PLAYERS_ERROR", { player_name: profile.name } );
			this._toasterService.error(message);
		}
		else {
			this.playersProfiles.push(profile);
			let message = this._translate.instant( "GAME.NEW_VIEW.PLAYERS_ADDED", { player_name: profile.name } );
			this._toasterService.success(message);
		}
	}

	/**
	 * Start game.
	 */
	public launchGame(): void {

		let playersNames: string[] = [];
		this.playersProfiles.forEach( function (profile) {
			playersNames.push(profile.name);
		});

		let newGame: NewGame = {
			duration: this.selectedDuration,
			playersNames: playersNames
		};

		this._gameResource.newGame(newGame).subscribe(
			response => {
				this._router.navigateByUrl("/game/" + response.id);
			},
			error => { throw Error("can't create new game : " + JSON.stringify(error)); }
		);
	}

	/**
	 * Disabled launch game button.
	 */
	public launchGameIsDisabled(): boolean {
		return ToolsService.isNull(this.selectedDuration) || this.playersProfiles.length < 2;
	}

	/**
	 * Open modal for select profiles.
	 */
	public selectProfiles(): void {

		const modalRef = this._ngbModal.open(ProfilePageModalComponent, { backdrop: 'static', size: 'xl' } );
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

}
