import { Duration } from "../common/duration.enum";
import { Effect } from "../common/effect.enum";
import { Theme } from "../common/theme.enum";
import { AbstractRound } from "../round/abstract.round";
import { Player } from './player';
import { ListenedMusics } from "./statistic/listened-musics.interface";

/**
 * Represents a current game.
 */
export class Game {
	
	/**
	 * The id.
	 */
	public id: number;
	
	/**
	 * The players list.
	 */
	public players: Player[];
	
	/**
	 * The duration.
	 */
	public duration: Duration;
	
	/**
	 * The themes.
	 */
	public themes: Theme[];
	
	/**
	 * The effects.
	 */
	public effects: Effect[];
	
	/**
	 * The number of listened musics by themes.
	 */
	public listenedMusics?: ListenedMusics;
	
	/**
	 * The number of musics played.
	 */
	public nbMusicsPlayed: number;
	
	/**
	 * The number of musics played in current roud.
	 */
	public nbMusicsPlayedInRound: number;
	
	/**
	 * The current round.
	 */
	public round: AbstractRound;
	
	/**
	 * If it's the game is finish.
	 */
	public finished: boolean
}
