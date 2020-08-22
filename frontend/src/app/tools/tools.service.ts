import {Injectable} from '@angular/core';
import {AVATAR_NOT_FOUND} from "./constant";
import {Avatar} from "../interfaces/dto/avatar.interface";
import {Music} from "../interfaces/dto/music.interface";

/**
 * Tools service.
 */
@Injectable()
export class ToolsService {
	
	/**
	 * Data constant.
	 */
	private static DATA: string = "data:";
	
	/**
	 * Base64 constant.
	 */
	private static BASE64: string = ";base64,";
	
	
	constructor() {
	}
	
	
	/**
	 * Test if obj is undefined or null.
	 *
	 * @param obj the object
	 * @return TRUE if obj is undefined or null, FALSE otherwise
	 */
	public static isNull(obj: any): boolean {
		return obj === undefined || obj === null;
	}
	
	/**
	 * Test if str is undefined, null or empty.
	 *
	 * @param str the str
	 * @return TRUE if str is undefined, null or empty, FALSE otherwise
	 */
	public static isNullOrEmpty(str: string): boolean {
		return ToolsService.isNull(str) || str === "";
	}
	
	/**
	 * Throw error if value is undefined, null or empty.
	 *
	 * @param key   the key label
	 * @param value the value
	 */
	public static verifyStringValue(key: string, value: string): void {
		
		if (!ToolsService.isNullOrEmpty(key) && ToolsService.isNullOrEmpty(value))
			throw new Error("Le champ '" + key + "' est obligatoire.");
	}
	
	/**
	 * Get the 'image' from avatar. If the avatar is null, 'not-found' image is used.
	 *
	 * @param avatar the avatar
	 */
	public static getImgFromAvatar(avatar: Avatar): string {
		
		if (ToolsService.isNull(avatar) || ToolsService.isNull(avatar.flux) || !avatar.flux.fileExists)
			return AVATAR_NOT_FOUND;
		
		let imageSrc: string;
		imageSrc = ToolsService.DATA;
		imageSrc += avatar.flux.contentType;
		imageSrc += ToolsService.BASE64;
		imageSrc += avatar.flux.contentFlux;
			
		return imageSrc;
	}
	
	/**
	 * Get the 'audio' from music.
	 *
	 * @param music the music
	 */
	public static getAudioFromMusic(music: Music): string {
		
		let audioSrc: string;
		audioSrc = ToolsService.DATA;
		audioSrc += music.flux.contentType;
		audioSrc += ToolsService.BASE64;
		audioSrc += music.flux.contentFlux;
		
		return audioSrc;
	}
	
	/**
	 * Is a thread sleep.
	 *
	 * @param ms the sleep during (in ms)
	 */
	public static sleep(ms: number) {
		return new Promise(resolve => setTimeout(resolve, ms));
	}
	
	/**
	 * Generate a random integer between min and max;
	 *
	 * @param min the min
	 * @param max the max
	 * @return a random integer
	 */
	public static random(min: number, max: number): number {
		
		if (ToolsService.isNull(min) || ToolsService.isNull(max) || min >= max)
			return 0;
		
		return Math.floor(Math.random() * (max - min + 1) + min);
	}
	
	/**
	 * Sort values by alphabetical and numerical order
	 * @param array
	 */
	public static sortByAlphabeticalAndNumerical(array): Array<any> {
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
	public static sortByAlphabeticalAndNumericalThemes(array): Array<any> {
		array.sort(function (a, b) {
			let textA = a.toUpperCase();
			let textB = b.toUpperCase();
			return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
		});
		return array;
	}
	
}