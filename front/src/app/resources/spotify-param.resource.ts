import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {SpotifyParam} from "../interfaces/dto/spotify-param.interface";

/**
 * SpotifyParam resource.
 */
@Injectable()
export class SpotifyParamResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.baseBackendUrl + "/params/spotify-param";
	
	
	
	constructor(private _http: HttpClient) { }
	
	
	
	/**
	 * Update spotifyParam.
	 *
	 * @param spotifyParam the spotifyParam
	 */
	public update(spotifyParam: SpotifyParam): Observable<SpotifyParam> {
		return this._http.put<SpotifyParam>(this.path, spotifyParam);
	}
	
	/**
	 * Gets spotifyParam.
	 */
	public find(): Observable<SpotifyParam> {
		return this._http.get<SpotifyParam>(this.path);
	}
	
}
