import {Music} from "../dto/music.interface";

/**
 * MusicResult.
 */
export interface MusicResult {
	gameId: number,
	music: Music,
	authorWinners: string[],
	titleWinners: string[],
	losers: string[]
}