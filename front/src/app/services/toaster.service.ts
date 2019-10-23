import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {ToolsService} from "../tools/tools.service";

/**
 * Custom toaster service.
 */
@Injectable()
export class ToasterService {

    private static PARAMS = {
        closeButton: true,
        progressBar: true,
        positionClass: 'toast-bottom-right',
        timeOut: 3500,
        extendedTimeOut: 1500
    };



    constructor(private _toastrService: ToastrService) { }



    /**
     * Launch a success toaster.
     *
     * @param message the message
     */
    public success(message: string) {

        ToolsService.verifyStringValue("message", message);

        this._toastrService.success(message, "", ToasterService.PARAMS);
    }

    /**
     * Launch a warning toaster.
     *
     * @param message the message
     */
    public warning(message: string) {

        ToolsService.verifyStringValue("message", message);

        this._toastrService.warning(message, "", ToasterService.PARAMS);
    }

    /**
     * Launch a error toaster.
     *
     * @param message the message
     */
    public error(message: string) {

        ToolsService.verifyStringValue("message", message);

        this._toastrService.error(message, "", ToasterService.PARAMS);
    }

}