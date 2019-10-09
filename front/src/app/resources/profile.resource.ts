import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Profile} from '../interfaces/profile.interface';
import {Page} from '../interfaces/page.interface';
import {environment} from 'src/environments/environment';

@Injectable()
export class ProfileResource {

	private _profilePath = environment.baseBackendUrl + "/profiles";



	constructor(private _http: HttpClient) { }



	public create(profile: Profile): Observable<Profile> {
		return this._http.post<Profile>(this._profilePath , profile);
	}

	public update(profile: Profile): Observable<Profile> {
		return this._http.put<Profile>(this._profilePath + "/" + profile.id, profile);
	}

	public findAllByNameStartingWith(prefixName: string, pageNumber: number): Observable< Page<Profile> > {

		let params = new HttpParams();
		params = params.set('prefixName', prefixName);
		params = params.set('pageNumber', pageNumber.toString());

		return this._http.get< Page<Profile> >(this._profilePath, { params: params } );
	}

	public delete(profile: Profile): Observable<Page<Profile>> {
		return this._http.delete< Page<Profile> >(this._profilePath + "/" + profile.id);
	}

}
