import { Player } from './player.interface';
import {AbstractRoundContent} from "../common/roundcontent/abstract.round.content";

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
	roundContent: AbstractRoundContent;
	nextEffect: Effect
}