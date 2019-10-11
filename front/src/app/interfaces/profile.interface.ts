import {Avatar} from './avatar.interface';

export interface Profile {
	id: number,
	background: number,
	name: string,
	avatarName: string,
	avatar?: Avatar
}