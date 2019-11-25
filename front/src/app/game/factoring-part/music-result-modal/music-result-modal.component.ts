import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Player} from 'src/app/interfaces/game/player.interface';
import {GameResource} from "../../../resources/game.resource";
import {MusicResult} from "../../../interfaces/game/music.result.interface";
import {Music} from "../../../interfaces/dto/music.interface";
import {TranslateService} from '@ngx-translate/core';

/**
 * The music result modal.
 */
@Component({
    selector: 'music-result-modal',
    templateUrl: './music-result-modal.component.html',
    styleUrls: ['./music-result-modal.component.css']
})
export class MusicResultModalComponent implements OnInit {

    /**
     * The game id.
     */
    @Input()
    private gameId: number;

    /**
     * The round.
     */
    @Input()
    private round: Round;

    /**
     * The players.
     */
    @Input()
    private players: Player[];

    /**
     * The music.
     */
    @Input()
    private music: Music;

    /**
     * The table headers.
     */
    private headers: string[];

    /**
     * The lines table.
     */
    private lines: PLayerLine[];

    /**
     * Drop down choice list.
     */
    private nbLoseChoices: PLayerLoserItem[];



    constructor(private _ngbActiveModal: NgbActiveModal,
                private _gameResource: GameResource,
                private _translate: TranslateService) {}

    ngOnInit(): void {

        this.nbLoseChoices = [];
        this._translate.get("COMMON.MANY_TIMES").subscribe(
            value => {
                for (let i = 0; i < 7; i++)
                    this.nbLoseChoices.push( { id: i, label: i + " " + value } );
            }
        );

        this.fillHeaders();
        this.fillRows();
    }



    /**
     * Fill headers.
     */
    private fillHeaders(): void {

        let prefix: string = "GAME.MUSIC_RESULT_MODAL.";

        this.headers = [];
        this.headers.push(prefix + "NAME_HEADER");
        this.headers.push(prefix + "SCORE_HEADER");

        if ( this.useMovieTitle() )
            this.headers.push(prefix + "MOVIE_TITLE_HEADER");
        else
            this.headers.push(prefix + "AUTHOR_WINNER_HEADER");

        this.headers.push(prefix + "TITLE_WINNER_HEADER");
        this.headers.push(prefix + "VICTIM_HEADER");
    }

    /**
     * Test if the movie title must be use.
     */
    private useMovieTitle(): boolean {
        return this.music.theme === Theme.SERIES_CINEMAS || this.music.theme === Theme.DISNEY;
    }

    /**
     * Fill lines.
     */
    private fillRows(): void {
        this.lines = [];
        for (let player of this.players)
            this.lines.push( { name: player.profile.name, score: player.score, authorWinner: false, titleWinner: false, loser: 0 } );
    }

    /**
     * If th author column is showed.
     */
    private showAuthorColumn(): boolean {
        return this.music.theme === Theme.ANNEES_60 || this.music.theme === Theme.ANNEES_70 || this.music.theme === Theme.ANNEES_80
                || this.music.theme === Theme.ANNEES_90 || this.music.theme === Theme.ANNEES_2000 || this.music.theme === Theme.ANNEES_2010
                || this.music.theme === Theme.SERIES_CINEMAS || this.music.theme === Theme.DISNEY || this.music.theme === Theme.CLASSIQUES;
    }

    /**
     * If th author column is showed.
     */
    private showTitleColumn(): boolean {
        return this.music.theme === Theme.ANNEES_60 || this.music.theme === Theme.ANNEES_70 || this.music.theme === Theme.ANNEES_80
                || this.music.theme === Theme.ANNEES_90 || this.music.theme === Theme.ANNEES_2000 || this.music.theme === Theme.ANNEES_2010
                || this.music.theme === Theme.DISNEY || this.music.theme === Theme.JEUX || this.music.theme === Theme.CLASSIQUES;
    }

    /**
     * If th loser column is showed.
     */
    private showLoserColumn(): boolean {
        return this.round === Round.THIEF;
    }

    /**
     * Gets music name.
     */
    private getMusicName(): string {

        if ( this.music.name.endsWith(".mp3") )
            return this.music.name.substring(0, this.music.name.length-4);
        else
            return this.music.name;
    }

    /**
     * Save music result and close modal.
     */
    private save(): void {

        let authorWinners: string[] = [];
        let titleWinners: string[] = [];
        let losers: string[] = [];

        for (let playerLine of this.lines) {
            if (playerLine.authorWinner)    authorWinners.push(playerLine.name);
            if (playerLine.titleWinner)     titleWinners.push(playerLine.name);
            for (let i = 0; i < playerLine.loser; i++)
                losers.push(playerLine.name);
        }

        let copyMusic: Music = {
            name: this.music.name,
            theme: this.music.theme,
            connectionMode: this.music.connectionMode
        };

        let musicResult: MusicResult = {
            gameId: this.gameId,
            music: copyMusic,
            authorWinners: authorWinners,
            titleWinners: titleWinners,
            losers: losers
        };

        this._gameResource.apply(musicResult).subscribe(
            value => { this._ngbActiveModal.close(value); },
            error => { throw Error("can't apply music result : " + JSON.stringify(error)); }
        );
    }

}

/**
 * The line table.
 */
interface PLayerLine {
    name: string;
    score: number;
    authorWinner: boolean;
    titleWinner: boolean;
    loser: number;
}

/**
 * The loser item.
 */
interface PLayerLoserItem {
    id: number;
    label: string;
}
