import {Avatar} from './avatar.interface';
import {ProfileStatistics} from '../common/profile-statistics.interface';

/**
 * Profile.
 */
export interface Profile {
	id: number,
	background: string,
	name: string,
	avatar?: Avatar,
	statistics?: ProfileStatistics
}