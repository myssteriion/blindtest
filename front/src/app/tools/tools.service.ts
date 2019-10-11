import {Injectable} from '@angular/core';
import {Flux} from '../interfaces/flux.interface';

/**
 * Tools service.
 */
@Injectable()
export class ToolsService {

    /**
     * Data constant.
     */
    private static _DATA: string = "data:";

    /**
     * Base64 constant.
     */
    private static _BASE64: string = ";base64,";

    /**
     * Path to "not-found" image in assets folder.
     */
    private static _AVATAR_NOT_FOUND: string = "assets/images/avatar/not-found.png";



    constructor() { }


    /**
     * Test if obj is undefined or null.
     *
     * @param obj the object
     * @return TRUE if obj is undefined or null, FALSE otherwise
     */
    public isNull(obj: any): boolean {
        return obj === undefined || obj === null;
    }

    /**
     * Test if str is undefined, null or empty.
     *
     * @param str the str
     * @return TRUE if str is undefined, null or empty, FALSE otherwise
     */
    public isNullOrEmpty(str: string): boolean {
        return this.isNull(str) || str === "";
    }

    /**
     * Throw error if value is undefined or null.
     *
     * @param key   the key label
     * @param value the value
     */
    public verifyValue(key: string, value: any): void {

        if ( !this.isNullOrEmpty(key) && this.isNull(value))
            throw new Error("Le champ '" + key + "' est obligatoire.");
    }

    /**
     * Throw error if value is undefined, null or empty.
     *
     * @param key   the key label
     * @param value the value
     */
    public verifyStringValue(key: string, value: string): void {

        if ( !this.isNullOrEmpty(key) && this.isNullOrEmpty(value))
            throw new Error("Le champ '" + key + "' est obligatoire.");
    }

    /**
     * Transform the flux in value for a 'img' tag. If the flux is empty, 'not-found' image is used.
     *
     * @param flux the flux
     */
    public getFluxForImg(flux: Flux): string {

        this.verifyValue("flux", flux);

        let imageSrc: string;

        if (flux.fileExists) {

            imageSrc = ToolsService._DATA;
            imageSrc += flux.contentType;
            imageSrc += ToolsService._BASE64;
            imageSrc += flux.contentFlux;
        }
        else {
            imageSrc = ToolsService._AVATAR_NOT_FOUND;
        }

        return imageSrc;
    }

}