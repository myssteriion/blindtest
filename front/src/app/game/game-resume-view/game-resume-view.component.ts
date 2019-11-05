import {Component, OnInit} from '@angular/core';
import {SLIDE_ANIMATION} from "../../tools/constant";
import {ToolsService} from "../../tools/tools.service";
import {GameResource} from "../../resources/game.resource";
import {Router} from '@angular/router';

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
				private _router: Router) { }

	ngOnInit() {
	}



	/**
	 * Search game.
	 */
	public search(): void {

		if ( !this.gameNumIsNaN() ) {
			this._gameResource.findById( Number(this.numGame) ).subscribe(
				response => { this._router.navigateByUrl("game/" + response.id); },
				error => { throw Error("can't find game : " + JSON.stringify(error)); }
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
