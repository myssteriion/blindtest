import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Avatar} from 'src/app/interfaces/dto/avatar.interface';
import {Page} from 'src/app/interfaces/base/page.interface';
import {environment} from 'src/environments/environment';
import {NewGame} from "../interfaces/game/newgame.interface";

/**
 * Game resource.
 */
@Injectable()
export class GameResource {

	/**
	 * Rest path.
	 */
	private _gamePath = environment.baseBackendUrl + "/game";



	constructor(private _http: HttpClient) { }



	public newGame(newGame: NewGame): Observable< Page<Avatar> > {
		return this._http.post< Page<Avatar> >( this._gamePath, newGame );
	}

}
