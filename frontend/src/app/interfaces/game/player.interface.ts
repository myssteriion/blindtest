import {Profile} from "../entity/profile.interface";
import {FoundMusics} from "./statistic/found-musics.interface";

/**
 * Player.
 */
export interface Player {
	profile: Profile,
	score: number,
	rank: number,
	teamNumber: number,
	last: boolean,
	turnToChoose: boolean,
	foundMusics?: FoundMusics
}