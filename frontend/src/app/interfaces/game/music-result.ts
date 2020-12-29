import { Music } from "../entity/music";

/**
 * Represents a result of music.
 */
export class MusicResult {
	
	/**
	 * The game id.
	 */
	public gameId: number;
	
	/**
	 * The music.
	 */
	public music: Music;
	
	/**
	 * The author winners list.
	 */
	public authorWinners: string[];
	
	/**
	 * The title winners list.
	 */
	public titleWinners: string[];
	
	/**
	 * The losers list.
	 */
	public losers: string[];
	
	/**
	 * The penalties list.
	 */
	public penalties: string[];
	
}
