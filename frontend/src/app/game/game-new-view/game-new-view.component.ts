import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faQuestionCircle, faSyncAlt } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { CommonUtilsService, ModalService, ToasterService } from "myssteriion-utils";
import { ProfilePageModalComponent } from 'src/app/profile/profile-page-modal/profile-page-modal.component';
import { Duration } from "../../interfaces/common/duration.enum";
import { Effect } from "../../interfaces/common/effect.enum";
import { Theme } from "../../interfaces/common/theme.enum";
import { Profile } from "../../interfaces/entity/profile";
import { NewGame } from "../../interfaces/game/new-game";
import { ThemeInfo } from "../../interfaces/music/theme-info";
import { GameResource } from "../../resources/game.resource";
import { MusicResource } from "../../resources/music.resource";
import { EFFECTS, GAME_PREFIX_PATH, MAX_PLAYERS, MIN_PLAYERS, SLIDE_ANIMATION, THEMES } from "../../tools/constant";

/**
 * The new game view.
 */
@Component({
	templateUrl: "./game-new-view.component.html",
	styleUrls: ["./game-new-view.component.css"],
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
	
	
	
	constructor(private translate : TranslateService,
				private toasterService: ToasterService,
				private ngbModal: NgbModal,
				private gameResource: GameResource,
				private musicResource: MusicResource,
				private router: Router,
				private commonUtilsService: CommonUtilsService,
				private modalService: ModalService) { }
	
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
		
		this.musicResource.computeThemesInfo().subscribe(
			response => {
				this.themesInfo = response.content;
			},
			error => {
				
				let text: string = this.translate.instant("GAME.NEW_VIEW.COMPUTE_THEMES_INFO_ERROR");
				let closeLabel: string = this.translate.instant("COMMON.CLOSE");
				
				this.modalService.openErrorModal(text, error, true, closeLabel).then(
					() => { this.computeThemesInfo(); },
					() => { /* do nothing */ }
				);
			}
		);
	}
	
	
	/**
	 * Gets min/max players label.
	 *
	 * @return min/max players label
	 */
	public getPlayersLabel(): string {
		
		let params = {
			minPlayers: MIN_PLAYERS,
			maxPlayers: MAX_PLAYERS
		};
		
		return this.translate.instant("GAME.NEW_VIEW.PLAYERS_LIST_LABEL", params);
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
	 * Test if the theme is selected.
	 *
	 * @param theme the theme
	 * @return TRUE if the theme is selected, FALSE otherwise
	 */
	public themeIsSelected(theme: Theme): boolean {
		return (this.selectedThemes.findIndex(thm => thm === theme)) !== -1;
	}
	
	
	/**
	 * Gets the number of musics OR theme percent.
	 *
	 * @param theme the theme
	 * @return the number of musics OR theme percent
	 */
	public getThemeInfo(theme: Theme): string {
		return this.getThemePercent(theme);
	}
	
	/**
	 * Get theme percent.
	 *
	 * @param theme the theme
	 * @return theme percent
	 */
	private getThemePercent(theme: Theme): string {
		
		let percent: number = 0;
		
		if ( this.themesInfo.length > 0)
			percent = 100 * this.computeThemeNbMusics(theme) / this.computeTotalNbMusics();
		
		return percent.toFixed(0) + "%";
	}
	
	/**
	 * Compute nb musics for the theme (0 if the theme isn't selected).
	 *
	 * @param theme the theme
	 * @return compute nb musics for the theme
	 */
	private computeThemeNbMusics(theme: Theme): number {
		
		let nbMusics: number = 0;
		
		let index = this.themesInfo.findIndex( thm => thm.theme === theme);
		if ( index != -1 && this.themeIsSelected(theme) )
			nbMusics = this.themesInfo[index].nbMusics;
		
		return nbMusics;
	}
	
	/**
	 * Compute nb musics for all themes (the theme must be selected).
	 *
	 * @return compute nb musics for all themes
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
	 * Test if the effect is selected.
	 *
	 * @param effect the effect
	 * @return TRUE if the effect is selected, FALSE otherwise
	 */
	public effectIsSelected(effect: Effect): boolean {
		return (this.selectedEffect.findIndex(eff => eff === effect)) !== -1;
	}
	
	
	/**
	 * Gets empty players.
	 *
	 * @return empty names list
	 */
	public getEmptyNames(): string[] {
		
		let index = this.playersProfiles.length + 1;
		var emptyNames = [];
		
		while (index <= MAX_PLAYERS) {
			emptyNames.push( this.translate.instant("GAME.NEW_VIEW.PLAYER") + " " + index );
			index++;
		}
		
		return emptyNames;
	}
	
	/**
	 * Deselect profile.
	 *
	 * @param profile the profile
	 */
	public deselectProfile(profile: Profile): void {
		let index: number = this.playersProfiles.findIndex((p) => p.id === profile.id);
		if (index !== -1)
			this.playersProfiles.splice(index, 1);
	}
	
	/**
	 * Select profile.
	 *
	 * @param profile the profile
	 */
	public selectProfile(profile: Profile): void {
		
		let index: number = this.playersProfiles.findIndex((p) => p.id === profile.id);
		
		if ( this.playersProfiles.length >= MAX_PLAYERS ) {
			let message = this.translate.instant( "GAME.NEW_VIEW.MAX_PLAYERS_ERROR", { max_players: MAX_PLAYERS}  );
			this.toasterService.error(message);
		}
		else if (index !== -1) {
			let message = this.translate.instant( "GAME.NEW_VIEW.DUPLICATE_PLAYERS_ERROR", { player_name: profile.name } );
			this.toasterService.error(message);
		}
		else {
			this.playersProfiles.push(profile);
			let message = this.translate.instant( "GAME.NEW_VIEW.PLAYERS_ADDED", { player_name: profile.name } );
			this.toasterService.success(message);
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
		
		this.gameResource.newGame(newGame).subscribe(
			response => {
				this.router.navigateByUrl(GAME_PREFIX_PATH + response.id);
			},
			error => {
				
				let text: string = this.translate.instant("GAME.NEW_VIEW.LAUNCH_GAME_ERROR");
				let closeLabel: string = this.translate.instant("COMMON.CLOSE");
				
				this.modalService.openErrorModal(text, error, false, closeLabel).then(
					() => { /* do nothing */ },
					() => { /* do nothing */ }
				);
			}
		);
	}
	
	/**
	 * Test if launch game button must be disable.
	 *
	 * @return TRUE if launch game button must be disable, FALSE otherwise
	 */
	public launchGameButtonIsDisabled(): boolean {
		return this.commonUtilsService.isNull(this.selectedDuration) || this.playersProfiles.length < MIN_PLAYERS;
	}
	
	/**
	 * Open modal for select profiles.
	 */
	public selectProfiles(): void {
		
		const modalRef = this.ngbModal.open(ProfilePageModalComponent, { backdrop: 'static', size: 'xl' } );
		modalRef.componentInstance.title = this.translate.instant("PROFILE.PAGE_MODAL.TITLE");
		modalRef.componentInstance.canEdit = false;
		modalRef.componentInstance.canSelect = true;
		modalRef.componentInstance.canDeselect = false;
		modalRef.componentInstance.onSelect.subscribe(
			(profile: Profile) => { this.selectProfile(profile); }
		);
		
		modalRef.result.then(
			() => { /* do nothing */ },
			() => { /* do nothing */ }
		);
	}
	
}
