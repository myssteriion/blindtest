import { HttpClient, HttpParams } from "@angular/common/http"
import { Injectable } from "@angular/core";
import { Page } from "myssteriion-utils";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Game } from "../interfaces/game/game";
import { MusicResult } from "../interfaces/game/music-result";
import { NewGame } from "../interfaces/game/new-game";

/**
 * Game resource.
 */
@Injectable()
export class GameResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/games";
	
	
	
	constructor(private http: HttpClient) { }
	
	
	
	/**
	 * Create new game.
	 * @param newGame the new game
	 */
	public newGame(newGame: NewGame): Observable<Game> {
		return this.http.post<Game>(this.path + "/new", newGame);
	}
	
	/**
	 * Apply music result.
	 * @param musicResult the music result
	 */
	public apply(musicResult: MusicResult): Observable<Game> {
		return this.http.post<Game>(this.path + "/apply", musicResult);
	}
	
	/**
	 * Find game by id.
	 *
	 * @param id the id
	 * @return game
	 */
	public findById(id: number): Observable<Game> {
		return this.http.get<Game>(this.path + "/" + id);
	}
	
	/**
	 * Gets games pageable.
	 *
	 * @param pageNumber 		the page number
	 * @param showFinishedGames TRUE for show finished games, FALSE otherwise
	 * @param itemPerPage       the item per page
	 * @return game list
	 */
	public findAll(pageNumber: number, showFinishedGames: boolean, itemPerPage: string): Observable< Page<Game> > {
		
		let params = new HttpParams();
		params = params.set("pageNumber", pageNumber.toString());
		params = params.set("itemPerPage", itemPerPage);
		params = params.set("showFinishedGames", showFinishedGames + '');
		
		return this.http.get< Page<Game> >( this.path, { params: params } );
	}
	
}
