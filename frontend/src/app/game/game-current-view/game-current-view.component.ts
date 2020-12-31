import { Component, OnDestroy, OnInit, QueryList, ViewChild, ViewChildren } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute } from "@angular/router";
import { IconDefinition } from "@fortawesome/fontawesome-common-types";
import { faDoorClosed, faDoorOpen, faQuestionCircle } from "@fortawesome/free-solid-svg-icons";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { CommonUtilsService, HTTP_NOT_FOUND, ModalService, RoutingService, ToasterService } from "myssteriion-utils";
import { CountdownConfig } from "ngx-countdown";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Player } from "src/app/interfaces/game/player";
import { UtilsService } from "src/app/services/utils.service";
import { Effect } from "../../interfaces/common/effect.enum";
import { RoundName } from "../../interfaces/common/round-name.enum";
import { Theme } from "../../interfaces/common/theme.enum";
import { Music } from "../../interfaces/entity/music";
import { Game } from "../../interfaces/game/game";
import { MusicFilter } from "../../interfaces/music/music-filter";
import { PlayerCardComponent } from "../../player/player-card/player-card.component";
import { GameResource } from "../../resources/game.resource";
import { MusicResource } from "../../resources/music.resource";
import { ADD_SCORE_DURING, SLIDE_ANIMATION } from "../../tools/constant";
import { GAME_END_ROUTE, HOME_ROUTE } from "../../tools/routing.constant";
import { ChoiceThemeModalComponent } from "../factoring-part/choice-theme-modal/choice-theme-modal.component";
import { CustomCountdownComponent } from "../factoring-part/custom-countdown/custom-countdown.component";
import { MusicResultModalComponent } from "../factoring-part/music-result-modal/music-result-modal.component";
import { RoundInfoModalComponent } from "../factoring-part/round-info-modal/round-info-modal.component";
import { ThemeEffectComponent } from "../factoring-part/theme-effect/theme-effect.component";

/**
 * The current game view.
 */
@Component({
	templateUrl: "./game-current-view.component.html",
	styleUrls: ["./game-current-view.component.css"],
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
	public currentExitIcon: IconDefinition;
	
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
	public preCountdownConfig: CountdownConfig;
	
	/**
	 * The countdown component.
	 */
	@ViewChild("countdown", { static: false })
	private countdown: CustomCountdownComponent;
	
	/**
	 * The countdown config.
	 */
	public countdownConfig: CountdownConfig;
	
	/**
	 * The post countdown component.
	 */
	@ViewChild("postCountdown", { static: false })
	private postCountdown: CustomCountdownComponent;
	
	/**
	 * The post countdown config.
	 */
	public postCountdownConfig: CountdownConfig;
	
	/**
	 * Current music.
	 */
	private currentMusic: Music;
	
	/**
	 * Show next music button.
	 */
	public showNextMusic: boolean;
	
	/**
	 * Show pass music button.
	 */
	public showPassMusic: boolean;
	
	/**
	 * Preview audio.
	 */
	private previewAudio: any;
	
	/**
	 * Show audio.
	 */
	public showAudio: boolean;
	
	/**
	 * Audio.
	 */
	@ViewChild("audio", { static: false })
	private audio: any;
	
	/**
	 * Left player.
	 */
	@ViewChildren("leftPlayers")
	public leftPlayersComponent: QueryList<PlayerCardComponent>;
	
	/**
	 * Left player.
	 */
	@ViewChildren("rightPlayers")
	public rightPlayersComponent: QueryList<PlayerCardComponent>;
	
	/**
	 * During update phase, the current players to update.
	 */
	private currentPlayersToUpdate: String[];
	
	private static SLOW_SPEED = 0.5;
	private static NORMAL_SPEED = 1;
	private static FAST_SPEED = 2;
	
	public faDoorClosed = faDoorClosed;
	public faDoorOpen = faDoorOpen;
	public faQuestionCircle = faQuestionCircle;
	
	
	
	constructor(private gameResource: GameResource,
				private translate: TranslateService,
				private activatedRoute: ActivatedRoute,
				private routingService : RoutingService,
				private ngbModal: NgbModal,
				private musicResource: MusicResource,
				private sanitizer: DomSanitizer,
				private toasterService: ToasterService,
				private commonUtilsService: CommonUtilsService,
				private utilsService: UtilsService,
				private modalService: ModalService) { }
	
	ngOnInit(): void {
		
		this.currentExitIcon = this.faDoorClosed;
		this.isLoaded = false;
		
		this.showNextMusic = true;
		this.showPassMusic = false;
		
		this.showAudio = false;
		
		this.translate.get("GAME.CURRENT_VIEW.LISTEN").subscribe(
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
		
		this.translate.get("GAME.CURRENT_VIEW.FINISH").subscribe(
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
		
		if ( !this.commonUtilsService.isNull(this.previewAudio) ) {
			this.previewAudio.pause();
			this.previewAudio = undefined;
		}
		
		if ( !this.commonUtilsService.isNull(this.audio) ) {
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
				this.gameResource.findById(gameId).subscribe(
					response => {
						
						this.game = response;
						if (this.game.finished)
							this.routingService.goTo(GAME_END_ROUTE, { id: gameId });
						else {
							
							this.isLoaded = true;
							
							if (this.game.nbMusicsPlayedInRound === 0)
								this.openRoundInfoModal();
						}
					},
					error => {
						
						if (error.status === HTTP_NOT_FOUND) {
							this.toasterService.error( this.translate.instant("GAME.CURRENT_VIEW.GAME_NOT_FOUND") );
							this.routingService.goTo(HOME_ROUTE);
						}
						else {
							
							let text: string = this.translate.instant("GAME.CURRENT_VIEW.FOUND_GAME_ERROR");
							let closeLabel: string = this.translate.instant("COMMON.GO_HOME");
							
							this.modalService.openErrorModal(text, error, true, closeLabel).then(
								() => { this.getGame(); },
								() => { this.routingService.goTo(HOME_ROUTE); }
							);
						}
					}
				);
			},
			error => { throw Error("can't find id param : " + JSON.stringify(error)); }
		);
	}
	
	/**
	 * Gets id param.
	 *
	 * @return the observable
	 */
	private getIdParam(): Observable<string> {
		return this.activatedRoute.params.pipe( map(param => param.id) );
	}
	
	/**
	 * Gets left players.
	 *
	 * @param left TRUE for left players, FALSE for right players
	 */
	public getPlayers(left: boolean): Player[] {
		
		let gamePlayers = this.game.players;
		
		let startIndex = left ? 0: 1;
		let leftPlayers = [];
		for (let i = startIndex; i < gamePlayers.length; i = i+2)
			leftPlayers.push(gamePlayers[i]);
		
		return leftPlayers;
	}
	
	/**
	 * Gets css padding for players list.
	 *
	 * @return gets css padding
	 */
	public addCssPadding(): string {
		
		let css = "game-current-view-players-padding-";
		
		if (this.game.players.length >= 2 && this.game.players.length <= 4) css += "20";
		if (this.game.players.length >= 5 && this.game.players.length <= 8) css += "15";
		if (this.game.players.length >= 9 && this.game.players.length <= 12) css += "10";
		if (this.game.players.length >= 13 && this.game.players.length <= 16) css += "5";
		
		return css;
	}
	
	
	/**
	 * Gets the view title.
	 *
	 * @return gets the title
	 */
	public getTitle(): string {
		
		let params = {
			name_round: this.translate.instant("ROUND." + this.game.round.roundName + ".NAME"),
			current_music: this.game.nbMusicsPlayedInRound + 1,
			total_musics:  this.game.round.nbMusics
		};
		
		return this.translate.instant("GAME.CURRENT_VIEW.TITLE", params);
	}
	
	/**
	 * Open modal for exit game.
	 */
	public exit(): void {
		
		let title: string = this.translate.instant("COMMON.WARNING");
		let body: string = this.getExitFormattedLabel();
		
		this.modalService.openConfirmModal(title, body).then(
			() => { this.routingService.goTo(HOME_ROUTE); },
			() => { /* do nothing */ }
		);
	}
	
	/**
	 * Gets exit formatted text for the body modal.
	 *
	 * @return gets exit formatted text for the body modal
	 */
	private getExitFormattedLabel(): string {
		
		let body: string =
			"<div class='row padding-bottom-1em'><div class='col'>" +
			this.translate.instant("GAME.CURRENT_VIEW.EXIT_BODY_MODAL_1") +
			"</div></div>";
		
		body +=
			"<div class='row padding-bottom-1em'><div class='col'>" +
			this.translate.instant("GAME.CURRENT_VIEW.EXIT_BODY_MODAL_2") +
			"</div></div>";
		
		return body;
	}
	
	
	
	/**
	 * Gets next music.
	 */
	public nextMusic(): void {
		
		this.showNextMusic = false;
		
		this.audio.nativeElement.pause();
		
		this.showAudio = false;
		
		this.preCountdown.setShow(false);
		this.countdown.setShow(false);
		this.postCountdown.setShow(false);
		
		if (this.game.round.roundName === RoundName.CHOICE) {
			
			const modalRef = this.ngbModal.open(ChoiceThemeModalComponent, { backdrop: 'static', size: 'md', keyboard: false } );
			modalRef.componentInstance.filteredThemes = this.game.themes;
			modalRef.componentInstance.playerName = this.game.players.find( player => player.turnToChoose )?.profile.name;
			
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
		
		let musicFilter: MusicFilter = {
			themes: themes,
			effects: this.game.effects
		};
		
		this.musicResource.random(musicFilter).subscribe(
			response => {
				
				this.currentMusic = response;
				
				this.previewAudio = new Audio();
				this.previewAudio.src = this.utilsService.getAudioFromMusic(this.currentMusic);
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
				
				let text: string = this.translate.instant("GAME.CURRENT_VIEW.RANDOM_MUSIC_ERROR");
				let closeLabel: string = this.translate.instant("COMMON.GO_HOME");
				
				this.modalService.openErrorModal(text, error, true, closeLabel).then(
					() => { this.callNextMusic(themes); },
					() => { this.routingService.goTo(HOME_ROUTE); }
				);
			}
		);
	}
	
	
	/**
	 * Roll theme and effect.
	 */
	private rollThemeEffect(): void {
		
		let rollTheme: boolean = !(this.game.round.roundName === RoundName.CHOICE || this.game.themes.length === 1);
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
	public onPreCountdownEnd(): void {
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
	public onCountdownEvent(timeLeft: string): void {
		
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
		
		this.audio.nativeElement.src = this.utilsService.getAudioFromMusic(this.currentMusic);
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
		
		const modalRef = this.ngbModal.open(MusicResultModalComponent, { backdrop: 'static', size: 'lg', keyboard: false } );
		modalRef.componentInstance.gameId = this.game.id;
		modalRef.componentInstance.round = this.game.round;
		modalRef.componentInstance.players = this.game.players;
		modalRef.componentInstance.music = this.currentMusic;
		
		modalRef.result.then(
			(result: Game) => {
				
				if (result.finished) {
					this.routingService.goTo(GAME_END_ROUTE, { id: this.game.id });
				}
				else {
					
					this.updatePlayers(result)
						.then( () => {
							this.showNextMusic = true;
							if (this.game.nbMusicsPlayedInRound === 0) {
								this.openRoundInfoModal();
								if ( !this.commonUtilsService.isNull(this.audio) )
									this.audio.nativeElement.volume = 0.05;
							}
						} );
				}
			},
			() => {
				this.routingService.goTo(HOME_ROUTE);
			}
		);
	}
	
	/**
	 * Update left/right players.
	 *
	 * @return promise
	 */
	private async updatePlayers(appliedGame: Game): Promise<void> {
		
		this.currentPlayersToUpdate = [];
		
		let appliedPlayers = appliedGame.players;
		
		for (let i = 0; i < appliedPlayers.length; i++) {
			
			let appliedPlayer = appliedPlayers[i];
			let newPlayerName = appliedPlayer.profile.name;
			
			let foundPlayerComponent = this.leftPlayersComponent.find(value => value.getPlayer().profile.name === newPlayerName);
			if ( this.commonUtilsService.isNull(foundPlayerComponent) )
				foundPlayerComponent = this.rightPlayersComponent.find(value => value.getPlayer().profile.name === newPlayerName);
			
			this.currentPlayersToUpdate.push( foundPlayerComponent!.getPlayer().profile.name );
			foundPlayerComponent!.updatePLayer(appliedPlayer);
			if (i % 2 === 1 || i === appliedPlayers.length-1) {
				await this.commonUtilsService.sleep(ADD_SCORE_DURING);
				this.currentPlayersToUpdate = [];
			}
		}
		
		this.game.finished = appliedGame.finished;
		this.game.nbMusicsPlayed = appliedGame.nbMusicsPlayed;
		this.game.nbMusicsPlayedInRound = appliedGame.nbMusicsPlayedInRound;
		this.game.round = appliedGame.round;
		this.game.round = appliedGame.round;
		this.game.finished = appliedGame.finished;
	}
	
	/**
	 * Test if the opacity css must be apply for player.
	 *
	 * @return TRUE if the opacity css must be apply for player, FALSE otherwise
	 */
	public addOpacityOnPlayer(player: Player): boolean {
		return !this.commonUtilsService.isNull(this.currentPlayersToUpdate) && this.currentPlayersToUpdate.length > 0 &&
			this.currentPlayersToUpdate.findIndex(playerName => playerName === player.profile.name) === -1;
	}
	
	/**
	 * Open round info modal.
	 */
	public openRoundInfoModal(): void {
		
		const modalRef = this.ngbModal.open(RoundInfoModalComponent, { backdrop: 'static', size: 'md' } );
		modalRef.componentInstance.game = this.game;
		
		modalRef.result.then(
			() => { /* do nothing */ },
			() => { /* do nothing */ }
		);
	}
	
}
