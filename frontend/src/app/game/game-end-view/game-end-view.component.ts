import { Component, OnDestroy, OnInit } from "@angular/core";
import { MatTabChangeEvent } from "@angular/material/tabs/tab-group";
import { ActivatedRoute } from "@angular/router";
import { IconDefinition } from "@fortawesome/fontawesome-common-types";
import { faDoorClosed, faDoorOpen, faMusic, faVolumeMute } from "@fortawesome/free-solid-svg-icons";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { CommonUtilsService, HTTP_NOT_FOUND, ModalService, RoutingService, ToasterService } from "myssteriion-utils";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Game } from "../../interfaces/game/game";
import { GameResource } from "../../resources/game.resource";
import { OLYMPIA_ANTHEM_SOUND, SLIDE_ANIMATION } from "../../tools/constant";
import { GAME_ROUTE, HOME_ROUTE } from "../../tools/routing.constant";

/**
 * The end game view.
 */
@Component({
	templateUrl: "./game-end-view.component.html",
	styleUrls: ["./game-end-view.component.css"],
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
	public currentExitIcon: IconDefinition;
	
	/**
	 * Audio.
	 */
	private audio: any;
	
	/**
	 * Show graph.
	 */
	public showGraph = false;
	
	/**
	 * If the music must be played.
	 */
	public musicIsPlaying: boolean;
	
	public faDoorClosed = faDoorClosed;
	public faDoorOpen = faDoorOpen;
	public faMusic = faMusic;
	public faVolumeMute = faVolumeMute;
	
	
	constructor(private gameResource: GameResource,
				private activatedRoute: ActivatedRoute,
				private ngbModal: NgbModal,
				private translate: TranslateService,
				private routingService: RoutingService,
				private toasterService: ToasterService,
				private commonUtilsService: CommonUtilsService,
				private modalService: ModalService) {
	}
	
	ngOnInit(): void {
		
		this.musicIsPlaying = true;
		
		this.audio = new Audio(OLYMPIA_ANTHEM_SOUND);
		this.audio.loop = true;
		this.audio.volume = 0.30;
		this.audio.load();
		this.playMusic();
		
		this.currentExitIcon = this.faDoorClosed;
		this.getGame();
	}
	
	ngOnDestroy(): void {
		if ( !this.commonUtilsService.isNull(this.audio) ) {
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
				this.gameResource.findById(gameId).subscribe(
					response => {
						
						this.game = response;
						
						if (!this.game.finished)
							this.routingService.goTo(GAME_ROUTE, { id: gameId });
						else
							this.isLoaded = true;
					},
					error => {
						
						if (error.status === HTTP_NOT_FOUND) {
							this.toasterService.error( this.translate.instant("GAME.END_VIEW.GAME_NOT_FOUND") );
							this.routingService.goTo(HOME_ROUTE);
						}
						else {
							
							let text: string = this.translate.instant("GAME.END_VIEW.FOUND_GAME_ERROR");
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
	 * Get id param.
	 *
	 * @return the observable
	 */
	private getIdParam(): Observable<string> {
		return this.activatedRoute.params.pipe( map(param => param.id) );
	}
	
	
	/**
	 * Open modal for back to home.
	 */
	public exit(): void {
		
		let title: string = this.translate.instant("COMMON.WARNING");
		let body: string = this.translate.instant("GAME.END_VIEW.BACK_TO_HOME_BODY_MODAL");
		
		this.modalService.openConfirmModal(title, body).then(
			() => { this.routingService.goTo(HOME_ROUTE); },
			() => { /* do nothing */ }
		);
	}
	
	/**
	 * Play music.
	 */
	public playMusic(): void {
		
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
	public onChangeTab(event: MatTabChangeEvent): void {
		this.showGraph = event.index === 1;
	}
	
}
