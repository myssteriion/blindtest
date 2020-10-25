import {Avatar} from './avatar.interface';
import {ProfileStatistics} from '../common/profile-statistics.interface';

/**
 * Profile.
 */
export interface Profile {
	id: number,
	name: string,
	background: string,
	avatar?: Avatar,
	profileStat?: ProfileStatistics
}