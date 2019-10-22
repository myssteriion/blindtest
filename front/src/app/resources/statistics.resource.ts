import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Statistic } from '../interfaces/dto/statistic.interface';
import { Page } from '../interfaces/base/page.interface';
import { environment } from 'src/environments/environment';

/**
 * Profile resource.
 */
@Injectable()
export class StatisticsResource {

	/**
	 * Rest path.
	 */
    private _statisticsPath = environment.baseBackendUrl + "/profilestats";



    constructor(private _http: HttpClient) { }


	/**
	 * Get statistics for defined profiles.
	 *
	 * @param profiles list of profiles
	 */
    public getStatisticsForProfile(profiles): Observable<Page<Statistic>> {
        let query = "";
        profiles.forEach(profile => {
            query = query + profile.id + ',';
        });
        query = query.substr(0, query.length - 1);
        return this._http.get<Page<Statistic>>(this._statisticsPath + "?profilesIds=" + query);
    }

}
