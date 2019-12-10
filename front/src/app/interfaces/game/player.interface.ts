/**
 * Player.
 */
import {Profile} from "../dto/profile.interface";

export interface Player {
	profile: Profile,
	score: number,
	rank: Rank,
	teamNumber: number,
	last: boolean,
	turnToChoose: boolean
}