import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {ToolsService} from "../tools/tools.service";

@Injectable()
export class ToasterService {

    constructor(private _toastrService: ToastrService,
                private _toolsService: ToolsService) { }


    public success(message: string) {

        this._toolsService.verifyStringValue("message", message);

        this._toastrService.success(message, "", {
            closeButton: true,
            progressBar: true,
            positionClass: 'toast-bottom-right',
            timeOut: 3500,
            extendedTimeOut: 1500
        });
    }

}