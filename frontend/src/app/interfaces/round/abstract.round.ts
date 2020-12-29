import { RoundName } from "../common/round-name.enum";

/**
 * Abstract class for all round.
 */
export abstract class AbstractRound {
	
	/**
	 * The round name.
	 */
	public roundName: RoundName;
	
	/**
	 * The musics number.
	 */
	public nbMusics: number;
	
	/**
	 * The number of win points.
	 */
	public nbPointWon: number;
	
}
