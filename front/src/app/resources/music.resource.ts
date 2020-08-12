import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Music} from "../interfaces/dto/music.interface";
import {ToolsService} from "../tools/tools.service";
import {ThemeInfo} from "../interfaces/music/theme.info";
import {Page} from "../interfaces/base/page.interface";

/**
 * Music resource.
 */
@Injectable()
export class MusicResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/musics";
	
	
	
	constructor(private _http: HttpClient) { }
	
	
	
	/**
	 * Compute themes info.
	 */
	public computeThemesInfo(): Observable< Page<ThemeInfo> > {
		return this._http.get< Page<ThemeInfo> >(this.path + "/compute-themes-info");
	}
	
	/**
	 * Get random musics.
	 *
	 * @param themes 	 	  the themes
	 * @param effects 	 	  the effects
	 * @param connectionMode  the connectionMode
	 */
	public random(themes: Theme[], effects: Effect[], connectionMode: ConnectionMode): Observable<Music> {
		
		let queryParam = "?connectionMode=" + connectionMode;
		if ( !ToolsService.isNull(themes) )	queryParam += "&themes=" + themes;
		if ( !ToolsService.isNull(effects) ) queryParam += "&effects=" + effects;
		
		return this._http.get<Music>(this.path + "/random" + queryParam);
	}
	
}
