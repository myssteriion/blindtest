import {Avatar} from './avatar.interface';
import {ProfileStat} from './profile-stat.interface';

/**
 * Profile.
 */
export interface Profile {
	id: number,
	name: string,
	background: string,
	avatar?: Avatar,
	profileStat?: ProfileStat
}