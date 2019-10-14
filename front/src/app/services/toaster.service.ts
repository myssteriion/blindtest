import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {ToolsService} from "../tools/tools.service";

/**
 * Custom toaster service.
 */
@Injectable()
export class ToasterService {

    constructor(private _toastrService: ToastrService) { }



    /**
     * Launc a success toaster.
     *
     * @param message the message
     */
    public success(message: string) {

        ToolsService.verifyStringValue("message", message);

        this._toastrService.success(message, "", {
            closeButton: true,
            progressBar: true,
            positionClass: 'toast-bottom-right',
            timeOut: 3500,
            extendedTimeOut: 1500
        });
    }

}