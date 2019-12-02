import {Component, OnInit} from '@angular/core';
import {
	HOME_PATH,
	OLYMPIA_ANTHEM_SOUND,
	RANKS_FIRST,
	RANKS_SECOND,
	RANKS_THIRD,
	SLIDE_ANIMATION
} from "../../tools/constant";
import {Game} from "../../interfaces/game/game.interface";
import {GameResource} from "../../resources/game.resource";
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {map} from 'rxjs/operators';
import {Player} from 'src/app/interfaces/game/player.interface';
import {faDoorClosed, faDoorOpen} from '@fortawesome/free-solid-svg-icons';
import {ConfirmModalComponent} from "../../common/modal/confirm/confirm-modal.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TranslateService} from '@ngx-translate/core';

/**
 * The end game view.
 */
@Component({
	selector: 'game-end-view',
	templateUrl: './game-end-view.component.html',
	styleUrls: ['./game-end-view.component.css'],
	animations: [
		SLIDE_ANIMATION
	]
})
export class GameEndViewComponent implements OnInit {


	/**
	 * The game.
	 */
	private game: Game;

	/**
	 * If view is loaded.
	 */
	private isLoaded: boolean;

	/**
	 * The podium ranks.
	 */
	private podiumRank: Rank[];

	/**
	 * The current exit icon.
	 */
	private currentExitIcon;

	/**
	 * Audio.
	 */
	private audio;

	private faDoorClosed = faDoorClosed;
	private faDoorOpen = faDoorOpen;



	constructor(private _gameResource: GameResource,
				private _activatedRoute: ActivatedRoute,
				private _ngbModal: NgbModal,
				private _translate: TranslateService,
				private _router: Router) { }

	ngOnInit(): void {

		this.audio = new Audio();
		this.audio.src = OLYMPIA_ANTHEM_SOUND;
		this.audio.loop = true;
		this.audio.load();
		this.audio.play();

		this.currentExitIcon = this.faDoorClosed;
		this.podiumRank = [Rank.FIRST, Rank.SECOND, Rank.THIRD];
		this.getGame();
	}



	/**
	 * Gets game.
	 */
	private getGame(): void {

		this.getIdParam().subscribe(
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
	private getIdParam(): Observable<string> {
		return this._activatedRoute.params.pipe( map(param => param.id) );
	}


	/**
	 * Gets rank logo.
	 *
	 * @param rank the rank
	 * @return players list
	 */
	private getXXXImg(rank: Rank): string {
		switch (rank) {
			case Rank.FIRST:	return RANKS_FIRST;
			case Rank.SECOND:	return RANKS_SECOND;
			case Rank.THIRD:	return RANKS_THIRD;
			default: 			return ""
		}
	}

	/**
	 * Gets players by ranks.
	 *
	 * @param rank the rank
	 * @return players list
	 */
	private getPlayersByRank(rank: Rank): Player[] {
		return this.game.players.filter(player => player.rank === rank);
	}

	/**
	 * Gets no-podium players.
	 *
	 * @return players list
	 */
	private getNoPodiumPlayers(): Player[] {

		let players: Player[] = [];

		let noPodiumRank = [Rank.FOURTH, Rank.FIFTH, Rank.SIXTH, Rank.SEVENTH, Rank.EIGHTH, Rank.NINTH, Rank.TENTH, Rank.ELEVENTH, Rank.TWELFTH];
		for (let rank of noPodiumRank) {
			console.log(rank,  this.getPlayersByRank(rank));
			players = players.concat( this.getPlayersByRank(rank) );
		}

		return players;
	}

	/**
	 * Open modal for back to home.
	 */
	private exit(): void {

		const modalRef = this._ngbModal.open( ConfirmModalComponent, { backdrop: 'static', size: 'lg' } );
		modalRef.componentInstance.title = this._translate.instant("COMMON.WARNING");
		modalRef.componentInstance.body = this._translate.instant("GAME.END_VIEW.BACK_TO_HOME_BODY_MODAL");

		modalRef.result.then(
			(result) => {
				this.audio.play();
				this.audio = undefined;
				this._router.navigateByUrl(HOME_PATH);
			},
			(reason) => { /* do nothing */ }
		);
	}

}
