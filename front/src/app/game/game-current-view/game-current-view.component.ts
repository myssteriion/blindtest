import {Component, OnInit} from '@angular/core';
import {SLIDE_ANIMATION} from "../../tools/constant";
import {Game} from "../../interfaces/game/game.interface";
import {TranslateService} from '@ngx-translate/core';
import {faDoorClosed, faDoorOpen} from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {GameResource} from "../../resources/game.resource";
import {ModalConfirmComponent} from "../../common/modal/confirm/modal-confirm.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

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
				private _activatedRoute: ActivatedRoute,
				private _router: Router,
				private _ngbModal: NgbModal) { }

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

}
