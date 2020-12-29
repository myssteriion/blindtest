import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';
import { CommonUtilsService, Page } from "myssteriion-utils";
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Music } from "../interfaces/entity/music";
import { MusicFilter } from "../interfaces/music/music-filter";
import { ThemeInfo } from "../interfaces/music/theme-info";

/**
 * Music resource.
 */
@Injectable()
export class MusicResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/musics";
	
	
	
	constructor(private http: HttpClient,
				private commonUtilsService: CommonUtilsService) { }
	
	
	
	/**
	 * Compute themes info.
	 *
	 * @return theme info
	 */
	public computeThemesInfo(): Observable< Page<ThemeInfo> > {
		return this.http.get< Page<ThemeInfo> >(this.path + "/compute-themes-info");
	}
	
	/**
	 * Get random musics.
	 *
	 * @param musicFilter the musicFilter
	 * @return music
	 */
	public random(musicFilter: MusicFilter): Observable<Music> {
		
		let queryParam = "?";
		if ( !this.commonUtilsService.isNull(musicFilter) && !this.commonUtilsService.isNull(musicFilter.themes) ) queryParam += "&themes=" + musicFilter.themes;
		if ( !this.commonUtilsService.isNull(musicFilter) && !this.commonUtilsService.isNull(musicFilter.effects) ) queryParam += "&effects=" + musicFilter.effects;
		
		return this.http.post<Music>(this.path + "/random" + queryParam, musicFilter);
	}
	
}
