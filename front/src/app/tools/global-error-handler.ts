import {ErrorHandler} from '@angular/core';
import {Injectable} from '@angular/core';

/**
 * Global error handler.
 */
@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

    /**
     * Catch all throw.
     *
     * @param error the error
     */
    handleError(error: any) {
        console.log("myError", error);
    }

}