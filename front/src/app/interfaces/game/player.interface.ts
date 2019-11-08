/**
 * Player.
 */
import {Profile} from "../dto/profile.interface";

export interface Player {
	profile: Profile,
	score: number,
	rank: Rank,
	turnToChoose: boolean
}