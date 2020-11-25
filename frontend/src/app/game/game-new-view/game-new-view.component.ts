import {Component, OnInit} from '@angular/core';
import {Profile} from "../../interfaces/entity/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {UtilsService} from 'src/app/tools/utils.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfilePageModalComponent} from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import {EFFECTS, GAME_PREFIX_PATH, MAX_PLAYERS, MIN_PLAYERS, SLIDE_ANIMATION, THEMES} from "../../tools/constant";
import {NewGame} from "../../interfaces/game/new-game.interface";
import {GameResource} from "../../resources/game.resource";
import {Router} from '@angular/router';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from 'src/app/common/error-alert/error-alert-modal.component';
import {faQuestionCircle, faSyncAlt} from '@fortawesome/free-solid-svg-icons';
import {MusicResource} from "../../resources/music.resource";
import {ThemeInfo} from "../../interfaces/music/theme-info.interface";
import {CommonUtilsService, ToasterService} from "myssteriion-utils";

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
	 * Themes list.
	 */
	public themes = THEMES;
	
	/**
	 * Selected themes.
	 */
	public selectedThemes: Theme[];
	
	/**
	 * Themes info.
	 */
	private themesInfo: ThemeInfo[];
	
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
	
	public faQuestionCircle = faQuestionCircle;
	public faSyncAlt = faSyncAlt;
	
	
	
	constructor(private _translate : TranslateService,
				private _toasterService: ToasterService,
				private _ngbModal: NgbModal,
				private _gameResource: GameResource,
				private _musicResource: MusicResource,
				private _router: Router,
				private _commonUtilsService: CommonUtilsService) { }
	
	ngOnInit(): void {
		
		this.playersProfiles = [];
		this.selectedDuration = Duration.NORMAL;
		
		this.selectedThemes = [];
		THEMES.forEach(theme => { this.selectedThemes.push(theme.enumVal); } );
		
		this.selectedEffect = [];
		EFFECTS.forEach(effect => { this.selectedEffect.push(effect.enumVal); } );
		
		this.computeThemesInfo();
	}
	
	/**
	 * Compute themes info.
	 */
	public computeThemesInfo(): void {
		
		this.themesInfo = [];
		
		this._musicResource.computeThemesInfo().subscribe(
			response => {
				this.themesInfo = response.content;
			},
			error => {
				
				let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
				
				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
				modalRef.componentInstance.text = this._translate.instant("GAME.NEW_VIEW.COMPUTE_THEMES_INFO_ERROR");
				modalRef.componentInstance.suggestions = undefined;
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = true;
				modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CLOSE");
				
				modalRef.result.then(
					() => { this.computeThemesInfo(); },
					() => { /* do nothing */ }
				);
			}
		);
	}
	
	
	/**
	 * Gets players label.
	 */
	public getPlayersLabel(): string {
		
		let params = {
			minPlayers: MIN_PLAYERS,
			maxPlayers: MAX_PLAYERS
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
	 * Gets the number of musics OR theme percent.
	 *
	 * @param theme the theme
	 */
	public getThemeInfo(theme: Theme): string {
		return this.getThemePercent(theme);
	}
	
	/**
	 * Gets the number of musics in theme.
	 *
	 * @param theme the theme
	 */
	private getNumberOfMusicsInTheme(theme: Theme): string {
		
		let nbMusics: number = 0;
		
		let index = this.themesInfo.findIndex(thm => thm.theme === theme);
		if (index != -1)
			nbMusics = this.themesInfo[index].nbMusics;
		
		return nbMusics.toString();
	}
	
	/**
	 * Get theme percent.
	 *
	 * @param theme the theme
	 */
	private getThemePercent(theme: Theme): string {
		
		let percent: number = 0;
		
		if ( this.themesInfo.length > 0)
			percent = 100 * this.computeThemeNbMusics(theme) / this.computeTotalNbMusics();
		
		return percent.toFixed(0) + "%";
	}
	
	/**
	 * Compute nb musics for the theme according to the "selectedConnectionMode" field.
	 *
	 * @param theme the theme
	 */
	private computeThemeNbMusics(theme: Theme): number {
		
		let nbMusics: number = 0;
		
		let index = this.themesInfo.findIndex( thm => thm.theme === theme);
		if ( index != -1 && this.themeIsSelected(theme) )
			nbMusics = this.themesInfo[index].nbMusics;
		
		return nbMusics;
	}
	
	/**
	 * Compute nb musics for all themes according to the "selectedConnectionMode" field.
	 */
	private computeTotalNbMusics(): number {
		
		let nbMusics: number = 0;
		
		this.themesInfo.forEach(themeInfo => {
			nbMusics += this.computeThemeNbMusics(themeInfo.theme);
		} );
		
		return nbMusics;
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
		
		while (index <= MAX_PLAYERS) {
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
		
		if ( this.playersProfiles.length >= MAX_PLAYERS ) {
			let message = this._translate.instant( "GAME.NEW_VIEW.MAX_PLAYERS_ERROR", { max_players: MAX_PLAYERS}  );
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
		
		let profilesId: number[] = [];
		this.playersProfiles.forEach( function (profile) {
			profilesId.push(profile.id);
		});
		
		let newGame: NewGame = {
			duration: this.selectedDuration,
			profilesId: profilesId,
			themes: this.selectedThemes,
			effects: this.selectedEffect
		};
		
		this._gameResource.newGame(newGame).subscribe(
			response => {
				this._router.navigateByUrl(GAME_PREFIX_PATH + response.id);
			},
			error => {
				
				let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };
				
				let suggestions = [];
				
				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'md' } );
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
		return this._commonUtilsService.isNull(this.selectedDuration) || this.playersProfiles.length < MIN_PLAYERS;
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
