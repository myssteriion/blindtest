import {Component, OnInit} from '@angular/core';
import {SLIDE_ANIMATION} from "../../tools/constant";
import {ToolsService} from "../../tools/tools.service";
import {GameResource} from "../../resources/game.resource";
import {Router} from '@angular/router';
import {ToasterService} from "../../services/toaster.service";
import {TranslateService} from '@ngx-translate/core';

/**
 * The resume game view.
 */
@Component({
	selector: 'game-resume-view',
	templateUrl: './game-resume-view.component.html',
	styleUrls: ['./game-resume-view.component.css'],
	animations: [
		SLIDE_ANIMATION
	]
})
export class GameResumeViewComponent implements OnInit {

	/**
	 * The game id.
	 */
	public numGame: string;



	constructor(private _gameResource: GameResource,
				private _router: Router,
				private _toasterService: ToasterService,
				private _translate: TranslateService) { }

	ngOnInit() {
	}



	/**
	 * Search game.
	 */
	public search(): void {

		if ( !this.gameNumIsNaN() ) {
			this._gameResource.findById( Number(this.numGame) ).subscribe(
				response => { this._router.navigateByUrl("game/" + response.id); },
				error => { this._toasterService.error( this._translate.instant("GAME.RESUME_VIEW.GAME_NOT_FOUND") ); }
			);
		}
	}

	/**
	 * Test if the numGame is a positive number.
	 *
	 * @return TRUE if the numGame is a positive number, FALSE otherwise.
	 */
	public gameNumIsNaN(): boolean {
		return ToolsService.isNullOrEmpty(this.numGame) || isNaN( Number(this.numGame) ) || Number(this.numGame) < 0;
	}

}