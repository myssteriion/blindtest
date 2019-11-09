import {Injectable} from '@angular/core';
import {Flux} from '../interfaces/common/flux.interface';
import {AVATAR_NOT_FOUND} from "./constant";

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



    constructor() { }



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
     * Throw error if value is undefined or null.
     *
     * @param key   the key label
     * @param value the value
     */
    public static verifyValue(key: string, value: any): void {

        if ( !ToolsService.isNullOrEmpty(key) && ToolsService.isNull(value) )
            throw new Error("Le champ '" + key + "' est obligatoire.");
    }

    /**
     * Throw error if value is undefined, null or empty.
     *
     * @param key   the key label
     * @param value the value
     */
    public static verifyStringValue(key: string, value: string): void {

        if ( !ToolsService.isNullOrEmpty(key) && ToolsService.isNullOrEmpty(value) )
            throw new Error("Le champ '" + key + "' est obligatoire.");
    }

    /**
     * Transform the flux in value for a 'img' tag. If the flux is empty, 'not-found' image is used.
     *
     * @param flux the flux
     */
    public static getFluxForImg(flux: Flux): string {

        this.verifyValue("flux", flux);

        let imageSrc: string;

        if (flux.fileExists) {

            imageSrc = ToolsService.DATA;
            imageSrc += flux.contentType;
            imageSrc += ToolsService.BASE64;
            imageSrc += flux.contentFlux;
        }
        else {
            imageSrc = AVATAR_NOT_FOUND;
        }

        return imageSrc;
    }

    /**
     * Transform the flux in value for a 'audio'.
     *
     * @param flux the flux
     */
    public static getFluxForAudio(flux: Flux): string {

        this.verifyValue("flux", flux);

        let audioSrc: string;
        audioSrc = ToolsService.DATA;
        audioSrc += flux.contentType;
        audioSrc += ToolsService.BASE64;
        audioSrc += flux.contentFlux;

        return audioSrc;
    }

}