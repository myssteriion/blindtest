import {Component, OnInit} from '@angular/core';
import {GAME_PREFIX_PATH, HOME_PATH, OPACITY_ANIMATION, SLIDE_ANIMATION} from "../../tools/constant";
import {GameResource} from "../../resources/game.resource";
import {Router} from '@angular/router';
import {ToasterService} from "../../services/toaster.service";
import {TranslateService} from '@ngx-translate/core';
import {Game} from "../../interfaces/game/game.interface";
import {Page} from "../../interfaces/base/page.interface";
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

/**
 * The resume game view.
 */
@Component({
	selector: 'game-resume-view',
	templateUrl: './game-resume-view.component.html',
	styleUrls: ['./game-resume-view.component.css'],
	animations: [
		SLIDE_ANIMATION,
		OPACITY_ANIMATION
	]
})
export class GameResumeViewComponent implements OnInit {
	
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
	private currentPage: number;
	
	/**
	 * Show/hide pageable.
	 */
	public showPageable: boolean;
	
	/**
	 * Show/hide finished games.
	 */
	public showFinishedGames: boolean;
	
	
	
	constructor(private _gameResource: GameResource,
				private _router: Router,
				private _toasterService: ToasterService,
				private _translate: TranslateService,
				private _ngbModal: NgbModal) { }
	
	ngOnInit(): void {
		
		this.showGames = false;
		this.currentPage = 1;
		this.showFinishedGames = false;
		
		this.headers = [];
		this.headers.push( this._translate.instant("GAME.RESUME_VIEW.PLAYERS_NAMES_HEADER") );
		this.headers.push( this._translate.instant("GAME.RESUME_VIEW.DURATION_HEADER") );
		this.headers.push( this._translate.instant("GAME.RESUME_VIEW.CONNECTION_MODE_HEADER") );
		
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
		
		this._gameResource.findAll(this.currentPage-1, this.showFinishedGames).subscribe(
			response => {
				
				this.gamesPage = response;
				this.showGames = true;
				this.showPageable = this.gamesPage.totalPages > 1;
			},
			error => {
				
				let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };
				
				const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' } );
				modalRef.componentInstance.text = this._translate.instant("GAME.RESUME_VIEW.GAMES_LOAD_ERROR");
				modalRef.componentInstance.suggestions = undefined;
				modalRef.componentInstance.error = errorAlert;
				modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
				modalRef.componentInstance.showRetry = true;
				modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.GO_HOME");
				
				modalRef.result.then(
					() => { this.loadGames(true); },
					() => { this._router.navigateByUrl(HOME_PATH); }
				);
			}
		);
	}
	
	/**
	 * Select game.
	 */
	public selectGame(id: number): void {

		this._gameResource.findById(id).subscribe(
			response => { this._router.navigateByUrl(GAME_PREFIX_PATH + response.id); },
			error => { this._toasterService.error( this._translate.instant("GAME.RESUME_VIEW.GAME_NOT_FOUND") ); }
		);
	}
	
}
