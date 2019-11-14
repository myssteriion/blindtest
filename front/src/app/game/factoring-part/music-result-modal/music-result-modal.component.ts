import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Player} from 'src/app/interfaces/game/player.interface';
import {faCheckCircle, faTimesCircle, IconDefinition} from '@fortawesome/free-solid-svg-icons';
import {GameResource} from "../../../resources/game.resource";
import {MusicResult} from "../../../interfaces/game/music.result.interface";
import {Music} from "../../../interfaces/dto/music.interface";

/**
 * The profiles view.
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
     * If the loser column is showed.
     */
    private showLoserColumn;

    /**
     * The table headers.
     */
    private headers: string[];

    /**
     * The lines table.
     */
    private lines: PLayerLine[];



    constructor(private _ngbActiveModal: NgbActiveModal,
                private _gameResource: GameResource) {}

    ngOnInit() {

        this.fillHeaders();
        this.fillRows();

        this.showLoserColumn = (this.round === Round.CHOICE);
    }



    /**
     * Fill headers.
     */
    private fillHeaders(): void {
        let prefix: string = "GAME.MUSIC_RESULT_MODAL.";
        this.headers = [prefix + "NAME_HEADER", prefix + "SCORE_HEADER", prefix + "AUTHOR_WINNER_HEADER", prefix + "TITLE_WINNER_HEADER", prefix + "LOSER_HEADER"];
    }

    /**
     * Fill lines.
     */
    private fillRows(): void {
        this.lines = [];
        for (var player of this.players)
            this.lines.push( { name: player.profile.name, score: player.score, authorWinner: false, titleWinner: false, loser: false } );
    }

    /**
     * Gets icons.
     *
     * @param bool the value
     */
    private getIcon(bool: boolean): IconDefinition {
        return (bool) ? faCheckCircle : faTimesCircle;
    }

    /**
     * Gets icon class.
     *
     * @param bool the value
     */
    private getIconClass(bool: boolean): string {
        return (bool) ? "music-result-modal-check-icon" : "music-result-modal-cross-icon";
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
            if (playerLine.loser)           losers.push(playerLine.name);
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
    loser: boolean;
}
