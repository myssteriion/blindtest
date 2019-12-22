/**
 * Player.
 */
import {Profile} from "../dto/profile.interface";
import {FoundMusics} from "../found-musics.interface";

export interface Player {
	profile: Profile,
	score: number,
	rank: Rank,
	teamNumber: number,
	last: boolean,
	turnToChoose: boolean,
	foundMusics?: FoundMusics
}