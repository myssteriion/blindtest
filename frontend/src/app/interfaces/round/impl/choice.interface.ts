import {AbstractRound} from "../abstract.round.interface";

/**
 * Choice.
 */
export interface Choice extends AbstractRound {
	nbPointBonus: number,
	nbPointMalus: number,
	order: number[]
}