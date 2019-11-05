import {Component, OnInit} from '@angular/core';
import {SLIDE_ANIMATION} from "../../tools/constant";
import {Game} from "../../interfaces/game/game.interface";
import {TranslateService} from '@ngx-translate/core';
import {faDoorClosed, faDoorOpen} from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {GameResource} from "../../resources/game.resource";

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
	 * If view is loaded.
	 */
	public isLoaded: boolean;

	/**
	 * The current exit icon.
	 */
	public currentExitIcon;

	faDoorClosed = faDoorClosed;
	faDoorOpen = faDoorOpen;




	constructor(private _gameResource: GameResource,
				private _translate: TranslateService,
				private _activatedRoute: ActivatedRoute) { }

	ngOnInit() {

		this.currentExitIcon = this.faDoorClosed;
		this.isLoaded = false;
		this._getGame();
	}



	/**
	 * Gets game.
	 */
	private _getGame(): void {

		this._getIdParam().subscribe(
			response => {
				this._gameResource.findById( Number(response) ).subscribe(
					response => { this.game = response; this.isLoaded = true; },
					error => { new Error("can't find game : " + error); }
				);
			},
			error => { new Error("can't find id param : " + error); }
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

	public exit() {

	}

}
