import {Component, OnInit, ViewChild} from '@angular/core';
import {REDUCTION_ANIMATION, SLIDE_ANIMATION} from "../../tools/constant";
import {Game} from "../../interfaces/game/game.interface";
import {TranslateService} from '@ngx-translate/core';
import {faDoorClosed, faDoorOpen} from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {GameResource} from "../../resources/game.resource";
import {ModalConfirmComponent} from "../../common/modal/confirm/modal-confirm.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Player} from 'src/app/interfaces/game/player.interface';
import {MusicResource} from "../../resources/music.resource";
import {Music} from "../../interfaces/dto/music.interface";
import {ToolsService} from 'src/app/tools/tools.service';
import {CountdownComponent, CountdownConfig} from 'ngx-countdown';
import {ThemeEffectComponent} from "../factoring-part/theme-effect/theme-effect.component";
import {CustomCountdownComponent} from "../factoring-part/custom-countdown/custom-countdown.component";

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
	 * The preCountdown component.
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
	 * The pre countdown config.
	 */
	private countdownConfig: CountdownConfig;

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

	private faDoorClosed = faDoorClosed;
	private faDoorOpen = faDoorOpen;



	constructor(private _gameResource: GameResource,
				private _translate: TranslateService,
				private _activatedRoute: ActivatedRoute,
				private _router: Router,
				private _ngbModal: NgbModal,
				private _musicResource: MusicResource) { }

	ngOnInit() {

		this.currentExitIcon = this.faDoorClosed;
		this.isLoaded = false;
		this.showNextMusic = true;

		this._translate.get("GAME.CURRENT_VIEW.LISTEN").subscribe(
			value => {
				this.preCountdownConfig = {
					demand: true,
					format: "s",
					leftTime: 5,
					stopTime: 0,
					notify: [4, 3, 2, 1],
					prettyText: text => function() { return (text === "0") ? value : text; }()
				};
			}
		);

		this.countdownConfig = {
			demand: true,
			format: "ss",
			leftTime: 25,
			stopTime: 0
		};

		this._getGame();
	}



	/**
	 * Gets game.
	 */
	private _getGame(): void {

		this._getIdParam().subscribe(
			response => {
				this._gameResource.findById( Number(response) ).subscribe(
					response => { this.game = response; this._fillPlayers(); },
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
	private _getIdParam(): Observable<string> {
		return this._activatedRoute.params.pipe( map(param => param.id) );
	}

	/**
	 * Fill left/right players.
	 * @private
	 */
	private _fillPlayers() {

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
	}



	/**
	 * Get the view title.
	 */
	private getTitle(): string {

		let params = {
			name_round: this._translate.instant("ROUND." + this.game.round),
			current_music: this.game.nbMusicsPlayedInRound,
			total_musics:  this.game.roundContent.nbMusics
		};

		return this._translate.instant("GAME.CURRENT_VIEW.TITLE", params);
	}

	/**
	 * Open modal for exit game.
	 */
	private exit(): void {

		const modalRef = this._ngbModal.open( ModalConfirmComponent, { backdrop: 'static', size: 'lg' } );
		modalRef.componentInstance.title = this._translate.instant("COMMON.WARNING");
		modalRef.componentInstance.body = this._getFormattedLabel();

		modalRef.result.then(
			(result) => { this._router.navigateByUrl("/home"); },
			(reason) => { /* do nothing */ }
		);
	}

	/**
	 * Gets formatted text for the body modal.
	 *
	 * @private
	 */
	private _getFormattedLabel(): string {

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

		this.showNextMusic = false;
		this.preCountdown.setShow(false);
		this.countdown.setShow(false);

		this._musicResource.random().subscribe(
			response => {

				this.currentMusic = response;

				this.audio = new Audio();
				this.audio.src = ToolsService.getFluxForAudio(this.currentMusic.flux);

				this.rollThemeEffect();
			},
			error => { throw Error("can't find music : " + JSON.stringify(error)); }
		);
	}


	/**
	 * Roll theme and effect.
	 */
	private rollThemeEffect(): void {

		this.themeEffect.setShow(true);
		this.themeEffect.setMusic(this.currentMusic);
		this.themeEffect.roll()
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
		this.listenCurrentMusic();
		this.countdown.start();
	}

	/**
	 * When the countdown is ended.
	 */
	private onCountdownEnd(): void {
		this.stopCurrentMusic();
	}


	/**
	 * Listen current music.
	 */
	private listenCurrentMusic(): void {

		let defaultPlaybackRate = 1;
		if (this.currentMusic.effect === Effect.SLOW)
			defaultPlaybackRate = 0.5;
		else if (this.currentMusic.effect === Effect.SPEED)
			defaultPlaybackRate = 2;

		this.audio.defaultPlaybackRate = defaultPlaybackRate;
		this.audio.load();
		// this.audio.currentTime = 0;
		this.audio.play();
	}

	public stopCurrentMusic() {
		this.audio.pause();
	}




	public slow() {
		this.audio.defaultPlaybackRate = 0.5;
		this.audio.load();
		// this.audio.currentTime = 50;
		this.audio.play();
	}

	public normal() {
		this.audio.defaultPlaybackRate = 1;
		this.audio.load();
		// this.audio.currentTime = 50;
		this.audio.play();
	}

	public speed() {
		this.audio.defaultPlaybackRate = 2;
		this.audio.load();
		// this.audio.currentTime = 50;
		this.audio.play();
	}

	public reverse() {

		this.audio.defaultPlaybackRate = -1;
		this.audio.load();
		// this.audio.currentTime = 50;
		this.audio.play();
	}

}
