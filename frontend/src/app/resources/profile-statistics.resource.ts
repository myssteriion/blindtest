import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Page} from '../interfaces/base/page.interface';
import {environment} from 'src/environments/environment';
import {ProfileStat} from '../interfaces/entity/profile-stat.interface';

/**
 * Profile resource.
 */
@Injectable()
export class ProfileStatisticsResource {
	
	/**
	 * Rest path.
	 */
	private _statisticsPath = environment.backendPath + "/profilestats";
	
	
	constructor(private _http: HttpClient) {
	}
	
	
	/**
	 * Get statistics for defined profiles.
	 *
	 * @param profiles list of profiles
	 */
	public getStatisticsForProfile(profiles): Observable< Page<ProfileStat> > {
		let query = "";
		profiles.forEach(profile => {
			query = query + profile.id + ',';
		});
		query = query.substr(0, query.length - 1);
		return this._http.get<Page<ProfileStat>>(this._statisticsPath + "?profilesIds=" + query);
	}
	
}
