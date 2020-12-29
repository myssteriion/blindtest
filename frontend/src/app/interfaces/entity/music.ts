import { Flux } from "myssteriion-utils";
import { Effect } from "../common/effect.enum";
import { Theme } from "../common/theme.enum";

/**
 * Music.
 */
export class Music {
	
	/**
	 * The id.
	 */
	public id: number;
	
	/**
	 * The name.
	 */
	public name: string;
	
	/**
	 * The theme.
	 */
	public theme: Theme;
	
	/**
	 * The number of played.
	 */
	public played: number;
	
	/**
	 * Audio flux.
	 */
	public flux: Flux;
	
	/**
	 * The effect.
	 */
	public effect: Effect;
	
}
