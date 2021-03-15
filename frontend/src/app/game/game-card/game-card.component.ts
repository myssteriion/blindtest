import { Component, EventEmitter, Input, Output } from "@angular/core";
import { faUsers } from "@fortawesome/free-solid-svg-icons";
import { Effect } from "../../interfaces/common/effect.enum";
import { Theme } from "../../interfaces/common/theme.enum";
import { Game } from "../../interfaces/game/game";
import { EFFECTS, THEMES } from "../../tools/constant";

@Component({
	selector: "game-card",
	templateUrl: "./game-card.component.html",
	styleUrls: ["./game-card.component.scss"]
})
export class GameCardComponent {
	
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
	
	
	
	/**
	 * Gets concatenate players names (separator: ",").
	 *
	 * @return gets concatenate players names
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
	 * Return the object theme.
	 *
	 * @param theme
	 * @return the object theme
	 */
	public getObjTheme(theme: Theme): any {
		return this.themes.find(thm => {
			return thm.enumVal === theme;
		});
	}
	
	/**
	 * Return the object effect.
	 *
	 * @param effect
	 * @return the object effect
	 */
	public getObjEffect(effect: Effect): any {
		return this.effects.find(eff => {
			return eff.enumVal === effect;
		});
	}
	
}
