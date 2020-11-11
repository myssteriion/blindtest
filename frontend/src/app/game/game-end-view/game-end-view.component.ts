import {Component, OnInit, OnDestroy} from '@angular/core';
import {
	GAME_PREFIX_PATH,
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
import {faDoorClosed, faDoorOpen, faMusic, faVolumeMute} from '@fortawesome/free-solid-svg-icons';
import {ConfirmModalComponent} from "../../common/modal/confirm/confirm-modal.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TranslateService} from '@ngx-translate/core';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {ToolsService} from "../../tools/tools.service";
import {HTTP_NOT_FOUND, ToasterService} from "myssteriion-utils";

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
	
	ngOnInit(): void {
		
		this.musicIsPlaying = true;
		
		this.audio = new Audio();
		this.audio.src = OLYMPIA_ANTHEM_SOUND;
		this.audio.loop = true;
		this.audio.volume = 0.30;
		this.audio.load();
		this.playMusic();
		
		this.currentExitIcon = this.faDoorClosed;
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
						
						let errorAlert: ErrorAlert = ErrorAlertModalComponent.parseError(error);
						
						if (errorAlert.status === HTTP_NOT_FOUND) {
							this._toasterService.error( this._translate.instant("GAME.END_VIEW.GAME_NOT_FOUND") );
							this._router.navigateByUrl(HOME_PATH);
						}
						else {
							
							const modalRef = this._ngbModal.open(ErrorAlertModalComponent, ErrorAlertModalComponent.getModalOptions() );
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
	 * Open modal for back to home.
	 */
	private exit(): void {
		
		const modalRef = this._ngbModal.open( ConfirmModalComponent, { backdrop: 'static', size: 'md' } );
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
	
	
	/**
	 * Show graph on graph tab click.
	 *
	 * @param event the event
	 */
	public onChangeTab(event) {
		this.showGraph = event.index === 1;
	}
	
}
