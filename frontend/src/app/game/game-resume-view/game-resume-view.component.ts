import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { ModalService, Page, RoutingService, ToasterService } from "myssteriion-utils";
import { Game } from "../../interfaces/game/game";
import { GameResource } from "../../resources/game.resource";
import { OPACITY_ANIMATION, SLIDE_ANIMATION } from "../../tools/constant";
import { GAME_ROUTE, HOME_ROUTE } from "../../tools/routing.constant";

/**
 * The resume game view.
 */
@Component({
	templateUrl: "./game-resume-view.component.html",
	styleUrls: ["./game-resume-view.component.css"],
	animations: [
		SLIDE_ANIMATION,
		OPACITY_ANIMATION
	]
})
export class GameResumeViewComponent implements OnInit {
	
	/**
	 * If view is loaded.
	 */
	public isLoaded: boolean;
	
	/**
	 * The table headers.
	 */
	public headers: string[];
	
	/**
	 * The pageable games.
	 */
	public gamesPage: Page<Game>;
	
	/**
	 * Show/hide games page.
	 */
	public showGames: boolean;
	
	/**
	 * The current page.
	 */
	public currentPage: number;
	
	/**
	 * Show/hide pageable.
	 */
	public showPageable: boolean;
	
	/**
	 * Show/hide finished games.
	 */
	public showFinishedGames: boolean;
	
	/**
	 * Games number per page.
	 */
	private gamesPerPage: string = "3";
	
	
	
	constructor(private gameResource: GameResource,
				private routingService: RoutingService,
				private toasterService: ToasterService,
				private translate: TranslateService,
				private modalService: ModalService) { }
	
	ngOnInit(): void {
		
		this.isLoaded = false;
		this.showGames = false;
		this.currentPage = 1;
		this.showFinishedGames = false;
		
		this.headers = [];
		this.headers.push( this.translate.instant("GAME.RESUME_VIEW.PLAYERS_NAMES_HEADER") );
		this.headers.push( this.translate.instant("GAME.RESUME_VIEW.DURATION_HEADER") );
		this.headers.push( this.translate.instant("GAME.RESUME_VIEW.CONNECTION_MODE_HEADER") );
		
		this.loadGames(true);
	}
	
	
	
	/**
	 * Load games page.
	 *
	 * @param initPageNumber TRUE for force page number to 1
	 */
	public loadGames(initPageNumber: boolean): void {
		
		this.showGames = false;
		if (initPageNumber)
			this.currentPage = 1;
		
		this.gameResource.findAll(this.currentPage-1, this.showFinishedGames, this.gamesPerPage).subscribe(
			response => {
				
				this.gamesPage = response;
				this.showGames = true;
				this.showPageable = this.gamesPage.totalPages > 1;
				
				this.isLoaded = true;
			},
			error => {
				
				let text: string = this.translate.instant("GAME.RESUME_VIEW.GAMES_LOAD_ERROR");
				let closeLabel: string = this.translate.instant("COMMON.GO_HOME");
				
				this.modalService.openErrorModal(text, error, true, closeLabel).then(
					() => { this.loadGames(true); },
					() => { this.routingService.goTo(HOME_ROUTE); }
				);
			}
		);
	}
	
	/**
	 * Select game.
	 */
	public selectGame(id: number): void {
		
		this.gameResource.findById(id).subscribe(
			response => { this.routingService.goTo(GAME_ROUTE, { id: response.id }); },
			error => { this.toasterService.error( this.translate.instant("GAME.RESUME_VIEW.GAME_NOT_FOUND") ); }
		);
	}
	
}
