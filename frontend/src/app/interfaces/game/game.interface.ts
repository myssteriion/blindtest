import {Player} from './player.interface';
import {ListenedMusics} from "./statistic/listened-musics.interface";
import {AbstractRound} from "../round/abstract.round.interface";

/**
 * Game.
 */
export interface Game {
	id: number,
	players: Player[],
	duration: Duration,
	themes: Theme[],
	effects: Effect[],
	listenedMusics?: ListenedMusics,
	
	nbMusicsPlayed: number,
	nbMusicsPlayedInRound: number,
	round: AbstractRound,
	
	finished: boolean
}