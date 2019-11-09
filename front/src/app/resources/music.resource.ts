import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Music} from "../interfaces/dto/music.interface";

/**
 * Music resource.
 */
@Injectable()
export class MusicResource {

	/**
	 * Rest path.
	 */
	private musicsPath = environment.baseBackendUrl + "/musics";



	constructor(private _http: HttpClient) { }



	/**
	 * Get random musics.
	 */
	public random(): Observable<Music> {
		return this._http.get<Music>(this.musicsPath);
	}

}
