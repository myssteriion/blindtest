import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Game} from "../../interfaces/game/game.interface";
import {faUsers} from '@fortawesome/free-solid-svg-icons';
import {EFFECTS, THEMES} from "../../tools/constant";

@Component({
	selector: 'game-card',
	templateUrl: './game-card.component.html',
	styleUrls: ['./game-card.component.css']
})
export class GameCardComponent implements OnInit {
	
	/**
	 * The game.
	 */
	@Input()
	public game: Game;
	
	@Output()
	public onSelect = new EventEmitter();
	
	public themes = THEMES;
	
	public effects = EFFECTS;
	
	public faUser = faUsers;
	
	
	
	constructor() { }
	
	ngOnInit() {
	}
	
	
	
	/**
	 * Get players names.
	 */
	public getPlayersNames(): string {
		
		let playersNames = "";
		
		for (let i = 0; i < this.game.players.length; i++) {
			
			playersNames += this.game.players[i].profile.name;
			if (i !== this.game.players.length -1)
				playersNames += ", ";
		}
		
		return playersNames;
	}
	
	/**
	 * Return Constant.THEMES[theme].
	 *
	 * @param theme
	 */
	public getJsonTheme(theme: Theme): any {
		return this.themes.find(thm => {
			return thm.enumVal === theme;
		});
	}
	
	/**
	 * Return Constant.EFFETS[effect].
	 *
	 * @param effect
	 */
	public getJsonEffect(effect: Effect): any {
		return this.effects.find(thm => {
			return thm.enumVal === effect;
		});
	}
	
}
