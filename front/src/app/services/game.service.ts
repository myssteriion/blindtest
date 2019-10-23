import {Injectable} from '@angular/core';
import {Game} from "../interfaces/game/game.interface";
import {NewGame} from "../interfaces/game/newgame.interface";
import {GameResource} from "../resources/game.resource";
import {Router} from '@angular/router';

/**
 * GameService.
 */
@Injectable()
export class GameService {

    /**
     * The game.
     */
    private _game: Game;



    constructor(private _gameResource: GameResource,
                private _router: Router) { }


    /**
     * Create a new game.
     *
     * @param newGame
     */
    public newGame(newGame: NewGame) {

        this._gameResource.newGame(newGame).subscribe(
            response => {
                this._game = response;
                this._router.navigateByUrl("/game/" + this._game.id);
            },
            error => { throw Error("can't create new game : " + error); }
        );
    }

}