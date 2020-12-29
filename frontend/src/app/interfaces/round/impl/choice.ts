import { AbstractRound } from "../abstract.round";

/**
 * The Choice round.
 */
export class Choice extends AbstractRound {
	
	/**
	 * The number of bonus points.
	 */
	public nbPointBonus: number;
	
	/**
	 * The number of malus points.
	 */
	public nbPointMalus: number;
	
	/**
	 * Index players order.
	 */
	public order: number[];
	
}
