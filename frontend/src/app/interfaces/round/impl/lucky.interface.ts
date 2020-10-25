import {AbstractRound} from "../abstract.round.interface";

/**
 * Lucky.
 */
export interface Lucky extends AbstractRound {
	nbPointBonus: number
	nbPlayers: number
}