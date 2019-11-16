import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Player} from 'src/app/interfaces/game/player.interface';
import {faCheckCircle, faTimesCircle, IconDefinition} from '@fortawesome/free-solid-svg-icons';
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

    ngOnInit() {

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

        if (this.music.theme === Theme.DISNEY)
            this.headers.push(prefix + "DISNEY_TITLE_HEADER");
        else
            this.headers.push(prefix + "AUTHOR_WINNER_HEADER");

        this.headers.push(prefix + "TITLE_WINNER_HEADER");
        this.headers.push(prefix + "STEAL_HEADER");
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
     * If thr loser column is showed.
     */
    private showLoserColumn(): boolean {
        return this.round === Round.THIEF;
    }

    /**
     * If thr author column is showed.
     */
    private showAuthorColumn(): boolean {
        return this.music.theme !== Theme.CINEMAS && this.music.theme !== Theme.SERIES
    }

    /**
     * Gets music name.
     */
    private getMusicName(): string {

        let lastPoint = this.music.name.lastIndexOf(".");
        return this.music.name.substring(0, lastPoint);
    }

    /**
     * Save music result and close modal.
     */
    private save() {

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
            theme: this.music.theme
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
