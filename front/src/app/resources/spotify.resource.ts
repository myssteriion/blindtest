import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {SpotifyParam} from "../interfaces/dto/spotify-param.interface";

/**
 * Spotify resource.
 */
@Injectable()
export class SpotifyResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.baseBackendUrl + "/spotify";
	
	
	
	constructor(private _http: HttpClient) { }
	
	
	
	/**
	 * Test the spotify connection whit the spotifyParam.
	 *
	 * @param spotifyParam the spotifyParam
	 */
	public connectionTest(spotifyParam: SpotifyParam): Observable<void> {
		return this._http.post<void>(this.path + "/connection-test", spotifyParam);
	}
	
}
