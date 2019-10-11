import {Avatar} from './avatar.interface';

/**
 * Profile.
 */
export interface Profile {
	id: number,
	background: number,
	name: string,
	avatarName: string,
	avatar?: Avatar
}