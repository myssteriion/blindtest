import {Player} from './player.interface';
import {AbstractRoundContent} from "../common/roundcontent/abstract.round.content";
import {ListenedMusics} from "./statistic/listened-musics.interface";

/**
 * Game.
 */
export interface Game {
	id: number,
	players: Player[],
	duration: Duration,
	sameProbability: boolean,
	themes: Theme[],
	effects: Effect[],
	connectionMode: ConnectionMode,
	listenedMusics?: ListenedMusics,

	nbMusicsPlayed: number,
	nbMusicsPlayedInRound: number,
	round: Round,
	roundContent: AbstractRoundContent,
	
	firstStep: boolean,
	lastStep: boolean,
	finished: boolean
}