import {Avatar} from './avatar.interface';
import { ProfileStatistics } from '../common/profile-statistics';

/**
 * Profile.
 */
export interface Profile {
	id: number,
	background: number,
	name: string,
	avatarName: string,
	avatar?: Avatar,
	statistics?: ProfileStatistics
}