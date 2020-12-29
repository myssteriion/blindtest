import { Injectable } from "@angular/core";
import { CommonUtilsService } from "myssteriion-utils";
import { Avatar } from "../interfaces/entity/avatar";
import { Music } from "../interfaces/entity/music";
import { AVATAR_NOT_FOUND } from "../tools/constant";

/**
 * Tools service.
 */
@Injectable()
export class UtilsService {
	
	constructor(private commonUtilsService: CommonUtilsService) { }
	
	
	
	/**
	 * Get the B64 image from avatar. If the avatar is null, "not-found" image is used.
	 *
	 * @param avatar the avatar
	 * @return B64 image from avatar
	 */
	public getImgFromAvatar(avatar: Avatar): string | null {
		return (avatar && avatar.flux) ? this.commonUtilsService.transformToB64(avatar.flux) : AVATAR_NOT_FOUND;
	}
	
	/**
	 * Get the "audio" from music.
	 *
	 * @param music the music
	 * @return NULL if the music or flux is null or if not exists, the audio otherwise
	 */
	public getAudioFromMusic(music: Music): string | null {
		return (music && music.flux) ? this.commonUtilsService.transformToB64(music.flux) : null;
	}
	
	/**
	 * Sort values by alphabetical and numerical order
	 * @param array
	 */
	// TODO
	public sortByAlphabeticalAndNumerical(array: Array<any>): Array<any> {
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
	// TODO
	public sortByAlphabeticalAndNumericalThemes(array: Array<any>): Array<any> {
		array.sort(function (a, b) {
			let textA = a.toUpperCase();
			let textB = b.toUpperCase();
			return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
		});
		return array;
	}
	
}
