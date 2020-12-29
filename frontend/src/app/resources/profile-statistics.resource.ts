import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';
import { Page } from "myssteriion-utils";
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Profile } from "../interfaces/entity/profile";
import { ProfileStat } from '../interfaces/entity/profile-stat';

/**
 * Profile resource.
 */
@Injectable()
export class ProfileStatisticsResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/profilestats";
	
	
	constructor(private http: HttpClient) {
	}
	
	
	/**
	 * Get statistics for defined profiles.
	 *
	 * @param profiles list of profiles
	 * @return the profileStat list
	 */
	public getStatisticsForProfile(profiles: Profile[]): Observable< Page<ProfileStat> > {
		let query = "";
		profiles.forEach(profile => {
			query = query + profile.id + ',';
		});
		query = query.substr(0, query.length - 1);
		return this.http.get<Page<ProfileStat>>(this.path + "?profilesIds=" + query);
	}
	
}
