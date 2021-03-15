import { Component, Input } from '@angular/core';
import { Game } from "../../../interfaces/game/game";

/**
 * The end game statistics view.
 */
@Component({
	selector: "end-game-statistics-view",
	templateUrl: "./end-game-statistics-view.component.html",
	styleUrls: ["./end-game-statistics-view.component.scss"]
})
export class EndGameStatisticsViewComponent {
	
	/**
	 * The game.
	 */
	@Input()
	public game: Game;
	
	
	
	constructor() { }
	
}
