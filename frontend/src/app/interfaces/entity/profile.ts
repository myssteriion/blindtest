import {Avatar} from './avatar';
import {ProfileStat} from './profile-stat';

/**
 * Profile.
 */
export class Profile {
	
	/**
	 * The id.
	 */
	public id: number;
	
	/**
	 * The name.
	 */
	public name: string;
	
	/**
	 * The background (card color).
	 */
	public background: string;
	
	/**
	 * The avatar.
	 */
	public avatar: Avatar;
	
	/**
	 * The profileStat.
	 */
	public profileStat: ProfileStat;
	
}
