import {Component, OnInit} from '@angular/core';
import {Profile} from "../../interfaces/dto/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from "../../services/toaster.service";
import {ToolsService} from 'src/app/tools/tools.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfilePageModalComponent} from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import {GAME_PREFIX_PATH, SLIDE_ANIMATION, THEMES} from "../../tools/constant";
import {NewGame} from "../../interfaces/game/newgame.interface";
import {GameResource} from "../../resources/game.resource";
import {Router} from '@angular/router';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from 'src/app/common/error-alert/error-alert-modal.component';
import {environment} from "../../../environments/environment";

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
    public durations = [Duration.SHORT, Duration.NORMAL, Duration.LONG];

    /**
     * Selected duration.
     */
    public selectedDuration: Duration;

	/**
	 * ConnectionModes list.
	 */
	public connectionModes = [ConnectionMode.OFFLINE, ConnectionMode.ONLINE, ConnectionMode.BOTH];

	/**
	 * Selected connection mode.
	 */
	public selectedConnectionMode: ConnectionMode;

	/**
	 * Themes list.
	 */
	private themes = THEMES;

	/**
	 * Selected themes.
	 */
	public selectedThemes: Theme[];

	/**
	 * Players profiles and empty names.
	 */
	public playersProfiles: Profile[];

	/**
	 * The max players number.
	 */
    private static MAX_PLAYERS: number = environment.maxPlayer;



    constructor(private _translate : TranslateService,
                private _toasterService: ToasterService,
				private _ngbModal: NgbModal,
				private _gameResource: GameResource,
				private _router: Router) {}

    ngOnInit(): void {

        this.playersProfiles = [];
        this.selectedDuration = Duration.NORMAL;
		this.selectedThemes = [];
		THEMES.forEach(theme => { this.selectedThemes.push(theme.enumVal); } );
		this.selectedConnectionMode = ConnectionMode.OFFLINE;
    }



	/**
	 * Select/deselect duration.
	 *
	 * @param theme: Theme
	 */
	public selectDeselectTheme(theme: Theme): void {

		let index = this.selectedThemes.findIndex(thm => thm === theme);
		if (index !== -1) {
			if (this.selectedThemes.length > 1)
				this.selectedThemes.splice(index, 1);
		}
		else
			this.selectedThemes.push(theme);
	}

	/**
	 * Tests if the theme is selected.
	 *
	 * @param theme: Theme
	 */
	public themeIsSelected(theme: Theme): boolean {
		return (this.selectedThemes.findIndex(thm => thm === theme)) !== -1;
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
	public deselectProfile(profile: Profile): void {
		let index: number = this.playersProfiles.findIndex((p) => p.id === profile.id);
		if (index !== -1)
			this.playersProfiles.splice(index, 1);
	}

	/**
	 * Select profile.
	 *
	 * @param profile
	 */
	public selectProfile(profile: Profile): void {

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
			playersNames: playersNames,
			themes: this.selectedThemes,
			connectionMode: this.selectedConnectionMode
		};

		this._gameResource.newGame(newGame).subscribe(
			response => {
				this._router.navigateByUrl(GAME_PREFIX_PATH + response.id);
			},
			error => {

				let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };

				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' } );
				modalRef.componentInstance.text = this._translate.instant("GAME.NEW_VIEW.LAUNCH_GAME_ERROR");
				modalRef.componentInstance.suggestion = this._translate.instant("GAME.NEW_VIEW.LAUNCH_GAME_SUGGESTION");
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = false;
				modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CLOSE");

				modalRef.result.then(
					(result) => { /* do nothing */ },
					(reason) => { /* do nothing */ }
				);
			}
		);
	}

	/**
	 * Disabled launch game button.
	 */
	public launchGameIsDisabled(): boolean {
		return ToolsService.isNull(this.selectedDuration) || ToolsService.isNull(this.selectedConnectionMode) || this.playersProfiles.length < 2;
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
