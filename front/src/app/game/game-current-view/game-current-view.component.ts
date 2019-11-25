import {Component, OnInit, ViewChild} from '@angular/core';
import {SLIDE_ANIMATION} from "../../tools/constant";
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
export class GameCurrentViewComponent implements OnInit {

	/**
	 * The game.
	 */
	private game: Game;

	/**
	 * The current exit icon.
	 */
	private currentExitIcon;

	/**
	 * Players left.
	 */
	private leftPlayers: Player[];

	/**
	 * Players right.
	 */
	private rightPlayers: Player[];

	/**
	 * If view is loaded.
	 */
	private isLoaded: boolean;

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
	 * Audio.
	 */
	private audio;

	/**
	 * In online mode, listen extract music.
	 */
	private showExtractOnlineMusic: boolean;

	/**
	 * In online mode, extract music url.
	 */
	private extractOnlineMusic: SafeResourceUrl;

	/**
	 * In online mode, show music.
	 */
	private showOnlineMusic: boolean;

	/**
	 * In online mode, music url.
	 */
	private onlineMusic: SafeResourceUrl;

	/**
	 * In offline mode, show stop button.
	 */
	private showStopMusicButton: boolean;

	private faDoorClosed = faDoorClosed;
	private faDoorOpen = faDoorOpen;
	private faQuestionCircle = faQuestionCircle;



	constructor(private _gameResource: GameResource,
				private _translate: TranslateService,
				private _activatedRoute: ActivatedRoute,
				private _router: Router,
				private _ngbModal: NgbModal,
				private _musicResource: MusicResource,
				private _sanitizer: DomSanitizer) { }

	ngOnInit(): void {

		this.currentExitIcon = this.faDoorClosed;
		this.isLoaded = false;
		this.showNextMusic = true;
		this.showExtractOnlineMusic = false;
		this.showOnlineMusic = false;
		this.showStopMusicButton = false;

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
					notify: [],
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



	/**
	 * Gets game.
	 */
	private getGame(): void {

		this.getIdParam().subscribe(
			response => {
				this._gameResource.findById( Number(response) ).subscribe(
					response => { this.game = response; this.fillPlayers(); },
					error => { throw Error("can't find game : " + JSON.stringify(error)); }
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
	 * Fill left/right players.
	 * @private
	 */
	private fillPlayers(): void {

		let allPlayers = this.game.players;

		this.leftPlayers = [];
		this.rightPlayers = [];

		for (let i = 0; i < allPlayers.length; i++) {
			if (i%2 === 0)
				this.leftPlayers.push(allPlayers[i]);
			else
				this.rightPlayers.push(allPlayers[i]);
		}

		this.isLoaded = true;

		if (this.game.nbMusicsPlayedInRound === 0)
			this.openRoundInfoModal();
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

		const modalRef = this._ngbModal.open( ConfirmModalComponent, { backdrop: 'static', size: 'lg' } );
		modalRef.componentInstance.title = this._translate.instant("COMMON.WARNING");
		modalRef.componentInstance.body = this.getFormattedLabel();

		modalRef.result.then(
			(result) => {

				if ( !ToolsService.isNull(this.audio) ) {
					this.audio.pause();
					this.audio = undefined;
				}

				this._router.navigateByUrl("/home");
			},
			(reason) => { /* do nothing */ }
		);
	}

	/**
	 * Gets formatted text for the body modal.
	 *
	 * @private
	 */
	private getFormattedLabel(): string {

		let body: string =
			"<div class='row padding-bottom-1em font-size-normal'><div class='col'>" +
				this._translate.instant("GAME.CURRENT_VIEW.EXIT_BODY_MODAL_1") +
			"</div></div>";

		body +=
			"<div class='row padding-bottom-1em font-size-normal'><div class='col'>" +
				this._translate.instant("GAME.CURRENT_VIEW.EXIT_BODY_MODAL_2") +
			"</div></div>";

		body +=
			"<div class='row font-size-normal'><div class='col alert alert-info'>" +
				this._translate.instant("GAME.CURRENT_VIEW.EXIT_BODY_MODAL_3", { game_id: this.game.id } ) +
			"</div></div>";

		return body;
	}



	/**
	 * Gets next music.
	 */
	private nextMusic(): void {

		this.showExtractOnlineMusic = false;
		this.showOnlineMusic = false;
		this.showNextMusic = false;
		this.preCountdown.setShow(false);
		this.countdown.setShow(false);

		if ( !ToolsService.isNull(this.audio) )
			this.audio.pause();

		if (this.game.round === Round.CHOICE) {

			const modalRef = this._ngbModal.open(ChoiceThemeModalComponent, { backdrop: 'static', size: 'lg', keyboard: false } );
			modalRef.componentInstance.filteredThemes = this.game.themes;

			modalRef.result.then(
				(result) => { let choiceTheme: Theme[] = []; choiceTheme.push(result); this.callNextMusic(choiceTheme); },
				(reason) => { /* do nothing */ }
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

		this._musicResource.random(themes, this.game.connectionMode).subscribe(
			response => {

				this.currentMusic = response;

				if (this.currentMusic.connectionMode === ConnectionMode.ONLINE) {

					this.extractOnlineMusic = this._sanitizer.bypassSecurityTrustResourceUrl(this.currentMusic.spotifyPreviewUrl);
					this.onlineMusic = this._sanitizer.bypassSecurityTrustResourceUrl(this.currentMusic.spotifyTrackUrl);
				}
				else {

					this.audio = new Audio();
					this.audio.src = ToolsService.getFluxForAudio(this.currentMusic.flux);
					this.audio.currentTime = 0;

					let defaultPlaybackRate = 1;
					if (this.currentMusic.effect === Effect.SLOW)
						defaultPlaybackRate = 0.5;
					else if (this.currentMusic.effect === Effect.SPEED)
						defaultPlaybackRate = 2;
					this.audio.defaultPlaybackRate = defaultPlaybackRate;
					this.audio.load();
				}


				this.rollThemeEffect();
			},
			error => {
				throw Error("can't find music : " + JSON.stringify(error));
			}
		);
	}


	/**
	 * Roll theme and effect.
	 */
	private rollThemeEffect(): void {

		let rollTheme: boolean = !(this.game.round === Round.CHOICE || this.game.themes.length === 1);

		this.themeEffect.setShow(true);
		this.themeEffect.setMusic(this.currentMusic);
		this.themeEffect.roll(rollTheme)
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
		this.countdown.setShow(true);
		this.countdown.start();

		if (this.currentMusic.connectionMode === ConnectionMode.ONLINE)
			this.showExtractOnlineMusic = true;
		else
			this.audio.play();
	}

	/**
	 * When the countdown is ended.
	 */
	private onCountdownEnd(): void {

		if (this.currentMusic.connectionMode === ConnectionMode.ONLINE)
			this.showExtractOnlineMusic = false;
		else
			this.audio.pause();

		this.startPostCountdown();
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

		if (this.currentMusic.connectionMode === ConnectionMode.ONLINE) {

			this.showOnlineMusic = true;
			this.showStopMusicButton = false;
		}
		else {

			let currentTime = this.audio.currentTime;
			this.audio.pause();
			this.audio.defaultPlaybackRate = 1;
			this.audio.load();
			this.audio.currentTime = currentTime;
			this.audio.play();
			this.showStopMusicButton = true;
		}

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
			(result) => {

				this.game = result;
				this.updatePlayers();

				this.showNextMusic = true;
				if (this.game.nbMusicsPlayedInRound === 0)
					this.openRoundInfoModal();
			},
			(reason) => { /* do nothing */ }
		);
	}

	/**
	 * Update left/right players.
	 * @private
	 */
	private updatePlayers(): void {

		let allPlayers = this.game.players;

		for (let i = 0; i < allPlayers.length; i++) {

			let player = allPlayers[i];
			let playerName = player.profile.name;

			let foundPlayer = this.leftPlayers.find(value => value.profile.name === playerName);
			if ( ToolsService.isNull(foundPlayer) )
				foundPlayer = this.rightPlayers.find(value => value.profile.name === playerName);

			if ( !ToolsService.isNull(foundPlayer) ) {

				foundPlayer.rank = player.rank;
				foundPlayer.turnToChoose = player.turnToChoose;

				if (foundPlayer.score !== player.score)
					foundPlayer.score = player.score;
			}
		}
	}


	/**
	 * Open round info modal.
	 */
	private openRoundInfoModal(): void {

		const modalRef = this._ngbModal.open(RoundInfoModalComponent, { backdrop: 'static', size: 'lg' } );
		modalRef.componentInstance.game = this.game;

		modalRef.result.then(
			(result) => { /* do nothing */ },
			(reason) => { /* do nothing */ }
		);
	}


	/**
	 * Stop the music.
	 */
	private stopMusic(): void {
		if ( !ToolsService.isNull(this.audio) )
			this.audio.pause();
	}


	public stop(): void {
		this.audio.pause();
	}

	public slow(): void {
		this.audio.defaultPlaybackRate = 0.5;
		this.audio.load();
		// this.audio.currentTime = 50;
		this.audio.play();
	}

	public normal(): void {
		this.audio.defaultPlaybackRate = 1;
		this.audio.load();
		// this.audio.currentTime = 50;
		this.audio.play();
	}

	public speed(): void {
		this.audio.defaultPlaybackRate = 2;
		this.audio.load();
		// this.audio.currentTime = 50;
		this.audio.play();
	}

	public reverse(): void {

		this.audio.defaultPlaybackRate = -1;
		this.audio.load();
		// this.audio.currentTime = 50;
		this.audio.play();
	}

}
