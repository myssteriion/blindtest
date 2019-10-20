import {Music} from "../dto/music.interface";

/**
 * MusicResult.
 */
export interface MusicResult {
	gameId: number,
	musicDTO: Music,
	winners: string[],
	losers: string[]
}