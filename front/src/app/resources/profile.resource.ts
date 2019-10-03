import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Profile} from '../interfaces/profile.interface';
import {List} from '../interfaces/list.interface';
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

	public findAll(): Observable<List<Profile>> {
		return this._http.get<List<Profile>>(this._profilePath);
	}

}
