import {Avatar} from './avatar.interface';
import {ProfileStatistics} from '../common/profile-statistics.interface';

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