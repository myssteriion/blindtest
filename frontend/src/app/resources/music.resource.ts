import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Music} from "../interfaces/entity/music.interface";
import {UtilsService} from "../services/utils.service";
import {ThemeInfo} from "../interfaces/music/theme-info.interface";
import {Page} from "../interfaces/base/page.interface";
import {MusicFilter} from "../interfaces/music/music-filter.interface";
import {CommonUtilsService} from "myssteriion-utils";

/**
 * Music resource.
 */
@Injectable()
export class MusicResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/musics";
	
	
	
	constructor(private _http: HttpClient,
				private _commonUtilsService: CommonUtilsService) { }
	
	
	
	/**
	 * Compute themes info.
	 */
	public computeThemesInfo(): Observable< Page<ThemeInfo> > {
		return this._http.get< Page<ThemeInfo> >(this.path + "/compute-themes-info");
	}
	
	/**
	 * Get random musics.
	 *
	 * @param musicFilter the musicFilter
	 */
	public random(musicFilter: MusicFilter): Observable<Music> {
		
		let queryParam = "?";
		if ( !this._commonUtilsService.isNull(musicFilter) && !this._commonUtilsService.isNull(musicFilter.themes) ) queryParam += "&themes=" + musicFilter.themes;
		if ( !this._commonUtilsService.isNull(musicFilter) && !this._commonUtilsService.isNull(musicFilter.effects) ) queryParam += "&effects=" + musicFilter.effects;
		
		return this._http.post<Music>(this.path + "/random" + queryParam, musicFilter);
	}
	
}
