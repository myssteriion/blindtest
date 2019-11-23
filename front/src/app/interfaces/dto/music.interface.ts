import {Flux} from '../common/flux.interface';

/**
 * Music.
 */
export interface Music {
	id?: number,
	name: string,
	theme: Theme,
	onlineMode: boolean,

	spotifyTrackId?: string,
	spotifyPreviewUrl?: string,
	spotifyTrackUrl?: string,

	played?: number,

	flux?: Flux,
	effect?: Effect,
}