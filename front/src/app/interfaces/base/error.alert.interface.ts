import {ErrorMessage} from "./error.message.interface";

/**
 * ErrorAlert.
 */
export interface ErrorAlert {
	status: number,
	statusText: string,
	name: string,
	error: ErrorMessage
}