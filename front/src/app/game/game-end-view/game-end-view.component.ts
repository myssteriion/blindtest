import {Component, OnInit, OnDestroy} from '@angular/core';
import {
	GAME_PREFIX_PATH,
	HOME_PATH,
	HTTP_NOT_FOUND,
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
import {faDoorClosed, faDoorOpen, faMusic, faVolumeMute} from '@fortawesome/free-solid-svg-icons';
import {ConfirmModalComponent} from "../../common/modal/confirm/confirm-modal.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TranslateService} from '@ngx-translate/core';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {ToasterService} from "../../services/toaster.service";
import {ToolsService} from "../../tools/tools.service";

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
export class GameEndViewComponent implements OnInit, OnDestroy {

	/**
	 * The game.
	 */
	private game: Game;

	/**
	 * If view is loaded.
	 */
	public isLoaded: boolean;

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

	/**
	 * Show graph.
	 */
	public showGraph = false;

	/**
	 * If the music must be played.
	 */
	public musicIsPlaying: boolean;

	private faDoorClosed = faDoorClosed;
	private faDoorOpen = faDoorOpen;
	private faMusic = faMusic;
	private faVolumeMute = faVolumeMute;


    constructor(private _gameResource: GameResource,
                private _activatedRoute: ActivatedRoute,
                private _ngbModal: NgbModal,
                private _translate: TranslateService,
                private _router: Router,
                private _toasterService: ToasterService) {
    }

    /**
     * Reload graphs on tab click
     * @param event
     */
    public onTabClick(event) {
        this.showGraph = event.index === 1;
    }

	ngOnInit(): void {

		this.musicIsPlaying = true;

		this.audio = new Audio();
		this.audio.src = OLYMPIA_ANTHEM_SOUND;
		this.audio.loop = true;
		this.audio.volume = 0.75;
		this.audio.load();
		this.playMusic();

		this.currentExitIcon = this.faDoorClosed;
		this.podiumRank = [Rank.FIRST, Rank.SECOND, Rank.THIRD];
		this.getGame();
	}

	ngOnDestroy(): void {
		if ( !ToolsService.isNull(this.audio) ) {
			this.audio.pause();
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

						if (!this.game.finished)
							this._router.navigateByUrl(GAME_PREFIX_PATH + gameId);
						else
							this.isLoaded = true;
					},
					error => {

						let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };

						if (errorAlert.status === HTTP_NOT_FOUND) {
							this._toasterService.error( this._translate.instant("GAME.END_VIEW.GAME_NOT_FOUND") );
							this._router.navigateByUrl(HOME_PATH);
						}
						else {

							const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' } );
							modalRef.componentInstance.text = this._translate.instant("GAME.END_VIEW.FOUND_GAME_ERROR");
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
	 * Gets rank logo.
	 *
	 * @param rank the rank
	 * @return the image
	 */
	private getImgByRank(rank: Rank): string {
		switch (rank) {
			case Rank.FIRST:	return RANKS_FIRST;
			case Rank.SECOND:	return RANKS_SECOND;
			case Rank.THIRD:	return RANKS_THIRD;
			default: 			return ""
		}
	}

	/**
	 * Gets rank tooltip.
	 *
	 * @param rank the rank
	 * @return the tooltip
	 */
	private getTooltipByRank(rank: Rank): string {
		return this._translate.instant("RANK." + rank);
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
	 * If rank had player.
	 *
	 * @param rank the rank
	 */
	private hadPlayerByRank(rank: Rank): boolean {
		return (this.game.players.filter(player => player.rank === rank).length) > 0;
	}

	/**
	 * Gets no-podium players.
	 *
	 * @return players list
	 */
	private getNoPodiumPlayers(): Player[] {

		let players: Player[] = [];

		let noPodiumRank = [Rank.FOURTH, Rank.FIFTH, Rank.SIXTH, Rank.SEVENTH, Rank.EIGHTH, Rank.NINTH, Rank.TENTH, Rank.ELEVENTH, Rank.TWELFTH];
		for (let rank of noPodiumRank)
			players = players.concat( this.getPlayersByRank(rank) );

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
			() => { this._router.navigateByUrl(HOME_PATH); },
			() => { /* do nothing */ }
		);
	}

	/**
	 * Play music.
	 */
	private playMusic(): void {

		this.musicIsPlaying = true;
		this.audio.play();
	}

	/**
	 * Stop music.
	 */
	public stopMusic(): void {

		this.musicIsPlaying = false;
		this.audio.pause();
		this.audio.currentTime = 0;
	}

}
