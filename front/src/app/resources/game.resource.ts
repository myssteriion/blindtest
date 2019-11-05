import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {NewGame} from "../interfaces/game/newgame.interface";
import {Game} from '../interfaces/game/game.interface';

/**
 * Game resource.
 */
@Injectable()
export class GameResource {

	/**
	 * Rest path.
	 */
	private gamePath = environment.baseBackendUrl + "/game";



	constructor(private _http: HttpClient) { }



	/**
	 * Create new game.
	 * @param newGame the new game
	 */
	public newGame(newGame: NewGame): Observable<Game> {
		return this._http.post<Game>( this.gamePath, newGame );
	}

	/**
	 * Find game by id.
	 * @param id the id
	 */
	public findById(id: number): Observable<Game> {
		return this._http.get<Game>( this.gamePath + "/" + id);
	}

}
