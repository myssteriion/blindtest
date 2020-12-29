import { Profile } from "../entity/profile";
import { FoundMusics } from "./statistic/found-musics.interface";

/**
 * Represents a player.
 */
export class Player {
	
	/**
	 * The profile.
	 */
	public profile: Profile;
	
	/**
	 * The current score.
	 */
	public score: number;
	
	/**
	 * The rank.
	 */
	public rank: number;
	
	/**
	 * If is the last (rank).
	 */
	public last: boolean;
	
	/**
	 * If is his turn to play/choose.
	 */
	public turnToChoose: boolean;
	
	/**
	 * Team number.
	 */
	public teamNumber: number;
	
	/**
	 * The number of found musics by themes by WinMode.
	 */
	public foundMusics?: FoundMusics;
	
}
