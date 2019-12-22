import {Component, OnInit} from '@angular/core';
import {Profile} from "../../interfaces/dto/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from "../../services/toaster.service";
import {ToolsService} from 'src/app/tools/tools.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfilePageModalComponent} from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import {EFFECTS, GAME_PREFIX_PATH, SLIDE_ANIMATION, THEMES} from "../../tools/constant";
import {NewGame} from "../../interfaces/game/newgame.interface";
import {GameResource} from "../../resources/game.resource";
import {Router} from '@angular/router';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from 'src/app/common/error-alert/error-alert-modal.component';
import {environment} from "../../../environments/environment";
import {faExclamationCircle} from '@fortawesome/free-solid-svg-icons';

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
	public themes = THEMES;

	/**
	 * Selected themes.
	 */
	public selectedThemes: Theme[];

	/**
	 * Themes list.
	 */
	public effects = EFFECTS;

	/**
	 * Selected themes.
	 */
	public selectedEffect: Effect[];

	/**
	 * Players profiles and empty names.
	 */
	public playersProfiles: Profile[];

	/**
	 * The max players number.
	 */
    private static MAX_PLAYERS: number = environment.maxPlayers;

	/**
	 * The min players number.
	 */
	private static MIN_PLAYERS: number = environment.minPlayers;

	private faExclamationCircle = faExclamationCircle;



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

		this.selectedEffect = [];
		EFFECTS.forEach(effect => { this.selectedEffect.push(effect.enumVal); } );

		this.selectedConnectionMode = ConnectionMode.OFFLINE;
    }


	/**
	 * Gets players label.
	 */
	public getPlayersLabel(): string {

    	let params = {
			minPlayers: GameNewViewComponent.MIN_PLAYERS,
			maxPlayers: GameNewViewComponent.MAX_PLAYERS
		};

    	return this._translate.instant("GAME.NEW_VIEW.PLAYERS_LIST_LABEL", params);
	}

	/**
	 * Select/deselect theme.
	 *
	 * @param theme the theme
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
	 * @param theme the theme
	 */
	public themeIsSelected(theme: Theme): boolean {
		return (this.selectedThemes.findIndex(thm => thm === theme)) !== -1;
	}


	/**
	 * Select/deselect effect.
	 *
	 * @param effect the effect
	 */
	public selectDeselectEffect(effect: Effect): void {

		let index = this.selectedEffect.findIndex(eff => eff === effect);
		if (index !== -1) {
			if (this.selectedEffect.length > 1)
				this.selectedEffect.splice(index, 1);
		}
		else
			this.selectedEffect.push(effect);
	}

	/**
	 * Tests if the effect is selected.
	 *
	 * @param effect the effect
	 */
	public effectIsSelected(effect: Effect): boolean {
		return (this.selectedEffect.findIndex(eff => eff === effect)) !== -1;
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
			effects: this.selectedEffect,
			connectionMode: this.selectedConnectionMode
		};

		this._gameResource.newGame(newGame).subscribe(
			response => {
				this._router.navigateByUrl(GAME_PREFIX_PATH + response.id);
			},
			error => {

				let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };

				let suggestions = [];
				suggestions.push( this._translate.instant("GAME.NEW_VIEW.LAUNCH_GAME_SUGGESTION") );
				suggestions.push( this._translate.instant("GAME.NEW_VIEW.LAUNCH_GAME_SUGGESTION_2") );

				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' } );
				modalRef.componentInstance.text = this._translate.instant("GAME.NEW_VIEW.LAUNCH_GAME_ERROR");
				modalRef.componentInstance.suggestions = suggestions;
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = false;
				modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CLOSE");

				modalRef.result.then(
					() => { /* do nothing */ },
					() => { /* do nothing */ }
				);
			}
		);
	}

	/**
	 * Disabled launch game button.
	 */
	public launchGameIsDisabled(): boolean {
		return ToolsService.isNull(this.selectedDuration) || ToolsService.isNull(this.selectedConnectionMode) || this.playersProfiles.length < GameNewViewComponent.MIN_PLAYERS;
	}

	/**
	 * Open modal for select profiles.
	 */
	public selectProfiles(): void {

		const modalRef = this._ngbModal.open(ProfilePageModalComponent, { backdrop: 'static', size: 'xl' } );
		modalRef.componentInstance.title = this._translate.instant("PROFILE.PAGE_MODAL.TITLE");
		modalRef.componentInstance.canEdit = false;
		modalRef.componentInstance.canSelect = true;
		modalRef.componentInstance.canDeselect = false;
		modalRef.componentInstance.onSelect.subscribe(
			profile => { this.selectProfile(profile); }
		);

		modalRef.result.then(
			() => { /* do nothing */ },
			() => { /* do nothing */ }
		);
	}

}
