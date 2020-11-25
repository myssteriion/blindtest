import { Injectable } from "@angular/core";
import { AVATAR_NOT_FOUND } from "./constant";
import { Avatar } from "../interfaces/entity/avatar.interface";
import { Music } from "../interfaces/entity/music.interface";
import { CommonUtilsService } from "myssteriion-utils";

/**
 * Tools service.
 */
@Injectable()
export class UtilsService {
	
	constructor(private _commonUtilsService: CommonUtilsService) { }
	
	
	
	/**
	 * Get the "image" from avatar. If the avatar is null, "not-found" image is used.
	 *
	 * @param avatar the avatar
	 */
	public getImgFromAvatar(avatar: Avatar): string {
		return ( this._commonUtilsService.isNull(avatar) ) ? AVATAR_NOT_FOUND : this._commonUtilsService.getImgFromFlux(avatar.flux)
	}
	
	/**
	 * Get the "audio" from music.
	 *
	 * @param music the music
	 */
	public getAudioFromMusic(music: Music): string {
		return ( this._commonUtilsService.isNull(music) ) ? AVATAR_NOT_FOUND : this._commonUtilsService.getImgFromFlux(music.flux)
	}
	
	/**
	 * Sort values by alphabetical and numerical order
	 * @param array
	 */
	public sortByAlphabeticalAndNumerical(array): Array<any> {
		array.sort(function (a, b) {
			let textA = a.name.toUpperCase();
			let textB = b.name.toUpperCase();
			return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
		});
		return array;
	}
	
	/**
	 * Sort values by alphabetical and numerical order
	 * @param array
	 */
	public sortByAlphabeticalAndNumericalThemes(array): Array<any> {
		array.sort(function (a, b) {
			let textA = a.toUpperCase();
			let textB = b.toUpperCase();
			return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
		});
		return array;
	}
	
}