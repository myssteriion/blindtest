import { Duration } from "../common/duration.enum";
import { Effect } from "../common/effect.enum";
import { Theme } from "../common/theme.enum";

/**
 * Represents a new game (for create a game).
 */
export class NewGame {
	
	/**
	 * The profiles id.
	 */
	public profilesId: number[];
	
	/**
	 * The duration.
	 */
	public duration: Duration;
	
	/**
	 * The themes.
	 */
	public themes: Theme[];
	
	/**
	 * The effects.
	 */
	public effects: Effect[];
	
}
