import {AbstractRoundContent} from "../abstract.round.content";

/**
 * ChoiceContent.
 */
export interface ChoiceContent extends AbstractRoundContent {
	nbPointBonus: number,
	nbPointMalus: number
}