import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Music} from "../interfaces/dto/music.interface";
import {ToolsService} from "../tools/tools.service";

/**
 * Music resource.
 */
@Injectable()
export class MusicResource {

	/**
	 * Rest path.
	 */
	private path = environment.baseBackendUrl + "/musics";



	constructor(private _http: HttpClient) { }



	/**
	 * Get random musics.
	 *
	 * @param theme the theme
	 */
	public random(theme: Theme): Observable<Music> {

		let queryParam = "";
		if ( !ToolsService.isNull(theme) )
			queryParam = "?theme=" + theme;

		return this._http.get<Music>(this.path + "/random" + queryParam);
	}

}
