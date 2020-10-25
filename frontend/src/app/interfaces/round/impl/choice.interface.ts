import {AbstractRound} from "../abstract.round.interface";

/**
 * ChoiceContent.
 */
export interface ChoiceContent extends AbstractRound {
	nbPointBonus: number,
	nbPointMalus: number,
	order: number[]
}