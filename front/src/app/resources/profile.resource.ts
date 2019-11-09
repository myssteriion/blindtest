import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Profile} from '../interfaces/dto/profile.interface';
import {Page} from '../interfaces/base/page.interface';
import {environment} from 'src/environments/environment';

/**
 * Profile resource.
 */
@Injectable()
export class ProfileResource {

	/**
	 * Rest path.
	 */
	private profilePath = environment.baseBackendUrl + "/profiles";



	constructor(private _http: HttpClient) { }



	/**
	 * Create profile.
	 *
	 * @param profile the profile.
	 */
	public create(profile: Profile): Observable<Profile> {
		return this._http.post<Profile>(this.profilePath , profile);
	}

	/**
	 * Update profile.
	 *
	 * @param profile the profile
	 */
	public update(profile: Profile): Observable<Profile> {
		return this._http.put<Profile>(this.profilePath + "/" + profile.id, profile);
	}

	/**
	 * Gets profiles pageable filtered by prefix name.
	 *
	 * @param prefixName the prefix name filter
	 * @param pageNumber the page number
	 */
	public findAllByNameStartingWith(prefixName: string, pageNumber: number): Observable< Page<Profile> > {

		let params = new HttpParams();
		params = params.set('prefixName', prefixName);
		params = params.set('pageNumber', pageNumber.toString());

		return this._http.get< Page<Profile> >( this.profilePath, { params: params } );
	}

	/**
	 * Delete profile.
	 *
	 * @param profile the profile
	 */
	public delete(profile: Profile): Observable<Page<Profile>> {
		return this._http.delete< Page<Profile> >(this.profilePath + "/" + profile.id);
	}

}
