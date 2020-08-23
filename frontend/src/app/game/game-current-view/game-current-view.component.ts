import {Component, OnDestroy, OnInit, QueryList, ViewChild, ViewChildren} from '@angular/core';
import {ADD_SCORE_DURING, END_GAME_PREFIX_PATH, HOME_PATH, HTTP_NOT_FOUND, SLIDE_ANIMATION} from "../../tools/constant";
import {Game} from "../../interfaces/game/game.interface";
import {TranslateService} from '@ngx-translate/core';
import {faDoorClosed, faDoorOpen, faQuestionCircle} from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {GameResource} from "../../resources/game.resource";
import {ConfirmModalComponent} from "../../common/modal/confirm/confirm-modal.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Player} from 'src/app/interfaces/game/player.interface';
import {MusicResource} from "../../resources/music.resource";
import {Music} from "../../interfaces/dto/music.interface";
import {ToolsService} from 'src/app/tools/tools.service';
import {CountdownConfig} from 'ngx-countdown';
import {ThemeEffectComponent} from "../factoring-part/theme-effect/theme-effect.component";
import {CustomCountdownComponent} from "../factoring-part/custom-countdown/custom-countdown.component";
import {MusicResultModalComponent} from "../factoring-part/music-result-modal/music-result-modal.component";
import {RoundInfoModalComponent} from '../factoring-part/round-info-modal/round-info-modal.component';
import {ChoiceThemeModalComponent} from "../factoring-part/choice-theme-modal/choice-theme-modal.component";
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {ToasterService} from "../../services/toaster.service";
import {PlayerCardComponent} from "../../player/player-card/player-card.component";

/**
 * The current game view.
 */
@Component({
	selector: 'game-current-view',
	templateUrl: './game-current-view.component.html',
	styleUrls: ['./game-current-view.component.css'],
	animations: [
		SLIDE_ANIMATION
	]
})
export class GameCurrentViewComponent implements OnInit, OnDestroy {
	
	/**
	 * The game.
	 */
	private game: Game;
	
	/**
	 * The current exit icon.
	 */
	private currentExitIcon;
	
	/**
	 * If view is loaded.
	 */
	public isLoaded: boolean;
	
	/**
	 * The theme-effect component.
	 */
	@ViewChild("themeEffect", { static: false })
	private themeEffect: ThemeEffectComponent;
	
	/**
	 * The pre countdown component.
	 */
	@ViewChild("preCountdown", { static: false })
	private preCountdown: CustomCountdownComponent;
	
	/**
	 * The pre countdown config.
	 */
	private preCountdownConfig: CountdownConfig;
	
	/**
	 * The countdown component.
	 */
	@ViewChild("countdown", { static: false })
	private countdown: CustomCountdownComponent;
	
	/**
	 * The countdown config.
	 */
	private countdownConfig: CountdownConfig;
	
	/**
	 * The post countdown component.
	 */
	@ViewChild("postCountdown", { static: false })
	private postCountdown: CustomCountdownComponent;
	
	/**
	 * The post countdown config.
	 */
	private postCountdownConfig: CountdownConfig;
	
	/**
	 * Current music.
	 */
	private currentMusic: Music;
	
	/**
	 * Show next music button.
	 */
	private showNextMusic: boolean;
	
	/**
	 * Show pass music button.
	 */
	private showPassMusic: boolean;
	
	/**
	 * Preview audio.
	 */
	private previewAudio;
	
	/**
	 * Show audio.
	 */
	private showAudio: boolean;
	
	/**
	 * Audio.
	 */
	@ViewChild("audio", { static: false })
	private audio;
	
	/**
	 * Left player.
	 */
	@ViewChildren("leftPlayers")
	private leftPlayersComponent: QueryList<PlayerCardComponent>;
	
	/**
	 * Left player.
	 */
	@ViewChildren("rightPlayers")
	private rightPlayersComponent: QueryList<PlayerCardComponent>;
	
	/**
	 * During update phase, the current players to update.
	 */
	private currentPlayersToUpdate: String[];
	
	private static SLOW_SPEED = 0.5;
	private static NORMAL_SPEED = 1;
	private static FAST_SPEED = 2;
	
	private faDoorClosed = faDoorClosed;
	private faDoorOpen = faDoorOpen;
	private faQuestionCircle = faQuestionCircle;
	
	
	
	constructor(private _gameResource: GameResource,
				private _translate: TranslateService,
				private _activatedRoute: ActivatedRoute,
				private _router: Router,
				private _ngbModal: NgbModal,
				private _musicResource: MusicResource,
				private _sanitizer: DomSanitizer,
				private _toasterService: ToasterService) { }
	
	ngOnInit(): void {
		
		this.currentExitIcon = this.faDoorClosed;
		this.isLoaded = false;
		
		this.showNextMusic = true;
		this.showPassMusic = false;
		
		this.showAudio = false;
		
		this._translate.get("GAME.CURRENT_VIEW.LISTEN").subscribe(
			value => {
				this.preCountdownConfig = {
					demand: true,
					format: "s",
					leftTime: 3,
					stopTime: 0,
					notify: [2, 1],
					prettyText: text => function() { return (text === "0") ? value : text; }()
				};
			}
		);
		
		this._translate.get("GAME.CURRENT_VIEW.FINISH").subscribe(
			value => {
				this.countdownConfig = {
					demand: true,
					format: "ss",
					leftTime: 30,
					stopTime: 0,
					notify: [25, 20, 15, 10, 5],
					prettyText: text => function() { return (text === "00") ? value : text; }()
				};
			}
		);
		
		this.postCountdownConfig = {
			demand: true,
			format: "s",
			leftTime: 3,
			stopTime: 0,
			notify: []
		};
		
		this.getGame();
	}
	
	ngOnDestroy(): void {
		if ( !ToolsService.isNull(this.previewAudio) ) {
			this.previewAudio.pause();
			this.previewAudio = undefined;
		}
		
		if ( !ToolsService.isNull(this.audio) ) {
			this.audio.nativeElement.pause();
			this.audio.nativeElement = undefined;
			this.audio = undefined;
		}
	}
	
	
	
	/**
	 * Gets game.
	 */
	private getGame(): void {
		
		this.getIdParam().subscribe(
			response => {
				
				let gameId = Number(response);
				this._gameResource.findById(gameId).subscribe(
					response => {
						
						this.game = response;
						if (this.game.finished)
							this._router.navigateByUrl(END_GAME_PREFIX_PATH + gameId);
						else {
							
							this.isLoaded = true;
							
							if (this.game.nbMusicsPlayedInRound === 0)
								this.openRoundInfoModal();
						}
					},
					error => {
						
						let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
						
						if (errorAlert.status === HTTP_NOT_FOUND) {
							this._toasterService.error( this._translate.instant("GAME.CURRENT_VIEW.GAME_NOT_FOUND") );
							this._router.navigateByUrl(HOME_PATH);
						}
						else {
							
							const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
							modalRef.componentInstance.text = this._translate.instant("GAME.CURRENT_VIEW.FOUND_GAME_ERROR");
							modalRef.componentInstance.suggestions = undefined;
							modalRef.componentInstance.error = errorAlert;
							modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
							modalRef.componentInstance.showRetry = true;
							modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.GO_HOME");
							
							modalRef.result.then(
								() => { this.getGame(); },
								() => { this._router.navigateByUrl(HOME_PATH); }
							);
						}
					}
				);
			},
			error => { throw Error("can't find id param : " + JSON.stringify(error)); }
		);
	}
	
	/**
	 * Get id param.
	 *
	 * @return the observable
	 */
	private getIdParam(): Observable<string> {
		return this._activatedRoute.params.pipe( map(param => param.id) );
	}
	
	/**
	 * Gets left players.
	 *
	 * @param left TRUE for left players, FALSE for right players
	 */
	private getPlayers(left: boolean): Player[] {
		
		let gamePlayers = this.game.players;
		
		let startIndex = left ? 0: 1;
		let leftPlayers = [];
		for (let i = startIndex; i < gamePlayers.length; i = i+2)
			leftPlayers.push(gamePlayers[i]);
		
		return leftPlayers;
	}
	
	/**
	 * Add padding class for players list.
	 */
	private addPaddingClass(): string {
		
		let css = "game-current-view-players-padding-";
		
		if (this.game.players.length >= 2 && this.game.players.length <= 4) css += "20";
		if (this.game.players.length >= 5 && this.game.players.length <= 8) css += "15";
		if (this.game.players.length >= 9 && this.game.players.length <= 12) css += "10";
		if (this.game.players.length >= 13 && this.game.players.length <= 16) css += "5";
		
		return css;
	}
	
	
	/**
	 * Get the view title.
	 */
	private getTitle(): string {
		
		let params = {
			name_round: this._translate.instant("ROUND." + this.game.round + ".NAME"),
			current_music: this.game.nbMusicsPlayedInRound + 1,
			total_musics:  this.game.roundContent.nbMusics
		};
		
		return this._translate.instant("GAME.CURRENT_VIEW.TITLE", params);
	}
	
	/**
	 * Open modal for exit game.
	 */
	private exit(): void {
		
		const modalRef = this._ngbModal.open( ConfirmModalComponent, { backdrop: 'static', size: 'md' } );
		modalRef.componentInstance.title = this._translate.instant("COMMON.WARNING");
		modalRef.componentInstance.body = this.getFormattedLabel();
		
		modalRef.result.then(
			() => { this._router.navigateByUrl(HOME_PATH); },
			() => { /* do nothing */ }
		);
	}
	
	/**
	 * Gets formatted text for the body modal.
	 *
	 * @private
	 */
	private getFormattedLabel(): string {
		
		let body: string =
			"<div class='row padding-bottom-1em'><div class='col'>" +
			this._translate.instant("GAME.CURRENT_VIEW.EXIT_BODY_MODAL_1") +
			"</div></div>";
		
		body +=
			"<div class='row padding-bottom-1em'><div class='col'>" +
			this._translate.instant("GAME.CURRENT_VIEW.EXIT_BODY_MODAL_2") +
			"</div></div>";
		
		return body;
	}
	
	
	
	/**
	 * Gets next music.
	 */
	private nextMusic(): void {
		
		this.showNextMusic = false;
		
		this.audio.nativeElement.pause();
		
		this.showAudio = false;
		
		this.preCountdown.setShow(false);
		this.countdown.setShow(false);
		this.postCountdown.setShow(false);
		
		if (this.game.round === Round.CHOICE) {
			
			const modalRef = this._ngbModal.open(ChoiceThemeModalComponent, { backdrop: 'static', size: 'md', keyboard: false } );
			modalRef.componentInstance.filteredThemes = this.game.themes;
			modalRef.componentInstance.playerName = this.game.players.find( player => player.turnToChoose ).profile.name;
			
			modalRef.result.then(
				(result: Theme) => {
					
					let choiceTheme: Theme[] = [];
					choiceTheme.push(result);
					this.callNextMusic(choiceTheme);
				},
				() => { /* do nothing */ }
			);
		}
		else
			this.callNextMusic(this.game.themes);
	}
	
	/**
	 * Call web service.
	 *
	 * @param themes the themes (optional)
	 */
	private callNextMusic(themes: Theme[]): void {
		
		this._musicResource.random(themes, this.game.effects).subscribe(
			response => {
				
				this.currentMusic = response;
				
				this.previewAudio = new Audio();
				this.previewAudio.src = ToolsService.getAudioFromMusic(this.currentMusic);
				this.previewAudio.volume = 1.0;
				
				let playbackRate = GameCurrentViewComponent.NORMAL_SPEED;
				if (this.currentMusic.effect === Effect.SLOW) {
					playbackRate = GameCurrentViewComponent.SLOW_SPEED;
				}
				else if (this.currentMusic.effect === Effect.SPEED || this.currentMusic.effect === Effect.MIX) {
					playbackRate = GameCurrentViewComponent.FAST_SPEED;
				}
				
				this.previewAudio.currentTime = 0;
				this.previewAudio.defaultPlaybackRate = playbackRate;
				this.previewAudio.playbackRate = playbackRate;
				this.previewAudio.load();
				
				this.rollThemeEffect();
			},
			error => {
				
				let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
				
				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
				modalRef.componentInstance.text = this._translate.instant("GAME.MUSIC_RESULT_MODAL.SAVE_ERROR");
				modalRef.componentInstance.suggestions = undefined;
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = true;
				modalRef.componentInstance.closeLabel = this._translate.instant("GAME.CURRENT_VIEW.RETRY_IN_OFFLINE_MODE_ERROR");
				
				modalRef.result.then(
					() => { this.callNextMusic(themes); },
					() => { this.callNextMusic(themes); }
				);
			}
		);
	}
	
	
	/**
	 * Roll theme and effect.
	 */
	private rollThemeEffect(): void {
		
		let rollTheme: boolean = !(this.game.round === Round.CHOICE || this.game.themes.length === 1);
		let rollEffect: boolean = this.game.effects.length > 1;
		
		this.themeEffect.setShow(true);
		this.themeEffect.setMusic(this.currentMusic);
		this.themeEffect.roll(rollTheme, rollEffect)
			.then( () => { this.startPreCountdown(); } );
	}
	
	
	/**
	 * Start the pre countdown.
	 */
	private startPreCountdown(): void {
		this.preCountdown.setShow(true);
		this.preCountdown.start();
	}
	
	/**
	 * When the pre countdown is ended.
	 */
	private onPreCountdownEnd(): void {
		this.startCountdown();
	}
	
	
	/**
	 * Start the countdown.
	 */
	private startCountdown(): void {
		
		this.countdown.setColor(CustomCountdownComponent.GREEN_COLOR);
		this.countdown.setShow(true);
		this.countdown.start();
		this.showPassMusic = true;
		this.previewAudio.play();
	}
	
	/**
	 * When the countdown event emit.
	 *
	 * @param timeLeft the timeLeft
	 */
	private onCountdownEvent(timeLeft): void {
		
		if (timeLeft === "15") this.countdown.setColor(CustomCountdownComponent.YELLOW_COLOR);
		if (timeLeft === "05") this.countdown.setColor(CustomCountdownComponent.RED_COLOR);
		
		if (this.currentMusic.effect === Effect.MIX) {
			
			if (this.previewAudio.playbackRate === GameCurrentViewComponent.SLOW_SPEED)
				this.previewAudio.playbackRate = GameCurrentViewComponent.FAST_SPEED;
			else
				this.previewAudio.playbackRate = GameCurrentViewComponent.SLOW_SPEED;
		}
	}
	
	/**
	 * When the countdown is ended.
	 */
	private onCountdownEnd(): void {
		
		this.countdown.setColor(CustomCountdownComponent.BLUE_COLOR);
		this.showPassMusic = false;
		this.previewAudio.pause();
		
		this.startPostCountdown();
	}
	
	/**
	 * On pass music button.
	 */
	public passMusic(): void {
		
		this.countdown.stop();
		this.countdown.setShow(false);
		this.onCountdownEnd();
	}
	
	
	/**
	 * Start the post countdown.
	 */
	private startPostCountdown(): void {
		
		this.preCountdown.setShow(false);
		this.postCountdown.setShow(true);
		this.postCountdown.start();
	}
	
	/**
	 * When the post countdown is ended.
	 */
	private onPostCountdownEnd(): void {
		
		this.countdown.setShow(false);
		this.postCountdown.setShow(false);
		
		let currentTime = this.previewAudio.currentTime;
		this.previewAudio = undefined;
		
		this.audio.nativeElement.src = ToolsService.getAudioFromMusic(this.currentMusic);
		this.audio.nativeElement.controls = true;
		this.audio.nativeElement.load();
		this.audio.nativeElement.currentTime = currentTime;
		this.audio.nativeElement.volume = 0.30;
		this.audio.nativeElement.play();
		
		this.showAudio = true;
		
		this.fillResult();
	}
	
	
	/**
	 * Open modal for fill result.
	 */
	private fillResult(): void {
		
		const modalRef = this._ngbModal.open(MusicResultModalComponent, { backdrop: 'static', size: 'lg', keyboard: false } );
		modalRef.componentInstance.gameId = this.game.id;
		modalRef.componentInstance.round = this.game.round;
		modalRef.componentInstance.players = this.game.players;
		modalRef.componentInstance.music = this.currentMusic;
		
		modalRef.result.then(
			(result: Game) => {
				
				if (result.finished) {
					this._router.navigateByUrl(END_GAME_PREFIX_PATH + this.game.id);
				}
				else {
					
					this.updatePlayers(result)
						.then( () => {
							this.showNextMusic = true;
							if (this.game.nbMusicsPlayedInRound === 0) {
								this.openRoundInfoModal();
								if ( !ToolsService.isNull(this.audio) )
									this.audio.nativeElement.volume = 0.05;
							}
						} );
				}
			},
			() => {
				this._router.navigateByUrl(HOME_PATH);
			}
		);
	}
	
	/**
	 * Update left/right players.
	 * @private
	 */
	private async updatePlayers(appliedGame: Game): Promise<void> {
		
		this.currentPlayersToUpdate = [];
		
		let appliedPlayers = appliedGame.players;
		
		for (let i = 0; i < appliedPlayers.length; i++) {
			
			let appliedPlayer = appliedPlayers[i];
			let newPlayerName = appliedPlayer.profile.name;
			
			let foundPlayerComponent = this.leftPlayersComponent.find(value => value.getPlayer().profile.name === newPlayerName);
			if ( ToolsService.isNull(foundPlayerComponent) )
				foundPlayerComponent = this.rightPlayersComponent.find(value => value.getPlayer().profile.name === newPlayerName);
			
			this.currentPlayersToUpdate.push( foundPlayerComponent.getPlayer().profile.name );
			foundPlayerComponent.updatePLayer(appliedPlayer);
			if (i % 2 === 1 || i === appliedPlayers.length-1) {
				await ToolsService.sleep(ADD_SCORE_DURING);
				this.currentPlayersToUpdate = [];
			}
		}
		
		this.game.finished = appliedGame.finished;
		this.game.nbMusicsPlayed = appliedGame.nbMusicsPlayed;
		this.game.nbMusicsPlayedInRound = appliedGame.nbMusicsPlayedInRound;
		this.game.round = appliedGame.round;
		this.game.roundContent = appliedGame.roundContent;
		this.game.firstStep = appliedGame.firstStep;
		this.game.lastStep = appliedGame.lastStep;
		this.game.finished = appliedGame.finished;
	}
	
	/**
	 * Test if the opacity css must me apply for player.
	 */
	private addOpacityOnPlayer(player: Player): boolean {
		return !ToolsService.isNull(this.currentPlayersToUpdate) && this.currentPlayersToUpdate.length > 0 &&
			this.currentPlayersToUpdate.findIndex(playerName => playerName === player.profile.name) === -1;
	}
	
	/**
	 * Open round info modal.
	 */
	private openRoundInfoModal(): void {
		
		const modalRef = this._ngbModal.open(RoundInfoModalComponent, { backdrop: 'static', size: 'md' } );
		modalRef.componentInstance.game = this.game;
		
		modalRef.result.then(
			() => { /* do nothing */ },
			() => { /* do nothing */ }
		);
	}
	
}
