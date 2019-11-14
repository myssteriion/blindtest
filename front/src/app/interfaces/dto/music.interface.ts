import {Flux} from '../common/flux.interface';

/**
 * Music.
 */
export interface Music {
	id?: number,
	name: string,
	theme: Theme,
	played?: number,
	flux? : Flux,
	effect?: Effect,
}