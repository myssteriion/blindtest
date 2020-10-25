import {AbstractRound} from "../abstract.round.interface";

/**
 * LuckyContent.
 */
export interface LuckyContent extends AbstractRound {
	nbPointBonus: number
	nbPlayers: number
}