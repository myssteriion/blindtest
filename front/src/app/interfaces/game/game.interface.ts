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
	nbMusicsPlayed: number,
	nbMusicsPlayedInRound: number,
	round: Round,
	roundContent: AbstractRoundContent,
	themes: Theme[],
	effects: Effect[],
	connectionMode: ConnectionMode,
	listenedMusics?: ListenedMusics,

	firstStep: boolean,
	lastStep: boolean,
	finished: boolean
}