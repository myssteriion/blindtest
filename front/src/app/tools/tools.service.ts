import {Injectable} from '@angular/core';
import {Flux} from '../interfaces/flux.interface';

@Injectable()
export class ToolsService {

    private static _DATA: string = "data:";

    private static _BASE64: string = ";base64,";

    private static _AVATAR_NOT_FOUND: string = "assets/images/avatar/not-found.png";



    constructor() { }



    public isNull(obj: any): boolean {
        return obj === undefined || obj === null;
    }

    public isNullOrEmpty(str: string): boolean {
        return this.isNull(str) || str === "";
    }

    public verifyValue(key: string, value: any): void {

        if ( !this.isNullOrEmpty(key) && this.isNull(value))
            throw new Error("Le champ '" + key + "' est obligatoire.");
    }

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