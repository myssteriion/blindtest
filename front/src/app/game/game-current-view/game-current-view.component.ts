import {Component, OnInit} from '@angular/core';
import {EFFECTS, EFFECTS_INDEX, MARIO_KART_SOUND, SLIDE_ANIMATION, THEMES, THEMES_INDEX} from "../../tools/constant";
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
	public game: Game;

	/**
	 * Players left.
	 */
	public leftPlayers: Player[];

	/**
	 * Players right.
	 */
	public rightPlayers: Player[];

	/**
	 * If view is loaded.
	 */
	public isLoaded: boolean;

	/**
	 * The current exit icon.
	 */
	public currentExitIcon;

	/**
	 * Current theme.
	 */
	public currentTheme: string;

	/**
	 * Current effect.
	 */
	public currentEffect: string;

	/**
	 * Current music.
	 */
	public currentMusic: Music;

	/**
	 * Audio object.
	 */
	private audioObj;

	faDoorClosed = faDoorClosed;
	faDoorOpen = faDoorOpen;



	constructor(private _gameResource: GameResource,
				private _translate: TranslateService,
				private _activatedRoute: ActivatedRoute,
				private _router: Router,
				private _ngbModal: NgbModal,
				private _musicResource: MusicResource) { }

	ngOnInit() {

		this.currentExitIcon = this.faDoorClosed;
		this.isLoaded = false;
		this.currentTheme = THEMES[0];
		this.currentEffect = EFFECTS[0];
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
	public getTitle(): string {

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
	public exit(): void {

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
	private _getFormattedLabel() {

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

	public getRandomMusic() {
		this._musicResource.random().subscribe(
			response => {

				this.currentMusic = response;
				this.audioObj = new Audio();
				this.audioObj.src = ToolsService.getFluxForAudio(this.currentMusic.flux);

				this.launchRoll();

				console.log("mu", this.currentMusic);
			},
			error => { throw Error("can't find music : " + JSON.stringify(error)); }
		);
	}

	/**
	 * Launch theme and effect rolling.
	 */
	private async launchRoll() {

		let themeIndex = THEMES_INDEX.findIndex(theme => theme === this.currentMusic.theme);
		let effectIndex = EFFECTS_INDEX.findIndex(effect => effect === this.currentMusic.effect);

		let audioObj = new Audio();
		audioObj.src = MARIO_KART_SOUND;
		audioObj.load();
		audioObj.play();

		while ( !audioObj.ended ) {
			this.currentTheme = THEMES[ ToolsService.random(0, THEMES_INDEX.length-1) ];
			this.currentEffect = EFFECTS[ ToolsService.random(0, EFFECTS_INDEX.length-1) ];
			await ToolsService.sleep(50);
		}

		this.currentTheme = THEMES[themeIndex];
		this.currentEffect = EFFECTS[effectIndex];
	}

	public stopMusic() {
		this.audioObj.pause();
	}

	public slow() {
		this.audioObj.defaultPlaybackRate = 0.5;
		this.audioObj.load();
		this.audioObj.currentTime = 50;
		this.audioObj.play();
	}

	public normal() {
		this.audioObj.defaultPlaybackRate = 1;
		this.audioObj.load();
		this.audioObj.currentTime = 50;
		this.audioObj.play();
	}

	public speed() {
		this.audioObj.defaultPlaybackRate = 2;
		this.audioObj.load();
		this.audioObj.currentTime = 50;
		this.audioObj.play();
	}

	public reverse() {

		this.audioObj.defaultPlaybackRate = -1;
		this.audioObj.load();
		this.audioObj.currentTime = 50;
		this.audioObj.play();
	}

}
