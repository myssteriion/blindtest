import {AbstractRoundContent} from "../abstract.round.content";

/**
 * LuckyContent.
 */
export interface LuckyContent extends AbstractRoundContent {
	nbPointBonus: number
	nbPlayers: number
}