import {ErrorMessage} from "./error.message.interface";

/**
 * ErrorAlert.
 */
export interface ErrorAlert {
	status: number,
	name: string,
	error: ErrorMessage
}