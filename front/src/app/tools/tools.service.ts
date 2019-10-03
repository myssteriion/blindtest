import {Injectable} from '@angular/core';
import {Avatar} from "src/app/interfaces/avatar.interface";

@Injectable()
export class ToolsService {

    private static _DATA: string = "data:";

    private static _BASE64: string = ";base64,";

    private static _AVATAR_NOT_FOUND = "assets/images/avatar/not-found.png";



    constructor() { }



    public isNull(obj: any) {
        return obj === undefined || obj === null;
    }

    public isNullOrEmpty(str: string) {
        return this.isNull(str) || str === "";
    }

    public verifyValue(key: string, value: any) {

        if ( !this.isNullOrEmpty(key) && this.isNull(value))
            throw new Error("Le champ '" + key + "' est obligatoire.");
    }

    public getAvatarFluxForImg(avatar: Avatar) {

        this.verifyValue("avatar", avatar);

        let imageSrc: string;

        if (avatar.fileExists) {

            imageSrc = ToolsService._DATA;
            imageSrc += avatar.contentType;
            imageSrc += ToolsService._BASE64;
            imageSrc += avatar.flux;
        }
        else {
            imageSrc = ToolsService._AVATAR_NOT_FOUND;
        }

        return imageSrc;
    }

}