import { Component, Input, OnInit } from "@angular/core";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { ModalService } from "myssteriion-utils";
import { Player } from "src/app/interfaces/game/player";
import { environment } from "../../../../environments/environment";
import { RoundName } from "../../../interfaces/common/round-name.enum";
import { Theme } from "../../../interfaces/common/theme.enum";
import { Music } from "../../../interfaces/entity/music";
import { MusicResult } from "../../../interfaces/game/music-result";
import { GameResource } from "../../../resources/game.resource";

/**
 * The music result modal.
 */
@Component({
	templateUrl: "./music-result-modal.component.html",
	styleUrls: ["./music-result-modal.component.css"]
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
	public round: RoundName;
	
	/**
	 * The players.
	 */
	@Input()
	public players: Player[];
	
	/**
	 * The music.
	 */
	@Input()
	public music: Music;
	
	/**
	 * The table headers.
	 */
	public headers: string[];
	
	/**
	 * The lines table.
	 */
	public lines: PLayerLine[];
	
	/**
	 * Drop down choice list.
	 */
	public nbLoseChoices: PLayerLoserItem[];
	
	
	
	constructor(private ngbActiveModal: NgbActiveModal,
				private gameResource: GameResource,
				private translate: TranslateService,
				private ngbModal: NgbModal,
				private modalService: ModalService) {}
	
	ngOnInit(): void {
		
		this.nbLoseChoices = [];
		this.translate.get("COMMON.MANY_TIMES").subscribe(
			value => {
				for (let i = 0; i <= environment.nbLoseMax; i++)
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
		this.headers.push(prefix + "WRONGLY_PASS_HEADER");
	}
	
	/**
	 * Test if the movie title must be use.
	 *
	 * @return TRUE if the movie title must be use, FALSE otherwise
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
			this.lines.push( { name: player.profile.name, score: player.score, authorWinner: false, titleWinner: false, loser: 0, penalty: false } );
		
		this.lines.sort( (line1, line2) => {
			return line2.score - line1.score;
		})
	}
	
	/**
	 * Test if the author column must be shown.
	 *
	 * @return TRUE if the author column must be shown, FALSE otherwise
	 */
	public showAuthorColumn(): boolean {
		return this.music.theme === Theme.ANNEES_60 || this.music.theme === Theme.ANNEES_70 || this.music.theme === Theme.ANNEES_80
			|| this.music.theme === Theme.ANNEES_90 || this.music.theme === Theme.ANNEES_2000 || this.music.theme === Theme.ANNEES_2010
			|| this.music.theme === Theme.SERIES_CINEMAS || this.music.theme === Theme.DISNEY;
	}
	
	/**
	 * Test if the title column must be shown.
	 *
	 * @return TRUE if the title column must be shown, FALSE otherwise
	 */
	public showTitleColumn(): boolean {
		return this.music.theme === Theme.ANNEES_60 || this.music.theme === Theme.ANNEES_70 || this.music.theme === Theme.ANNEES_80
			|| this.music.theme === Theme.ANNEES_90 || this.music.theme === Theme.ANNEES_2000 || this.music.theme === Theme.ANNEES_2010
			|| this.music.theme === Theme.DISNEY;
	}
	
	/**
	 * Test if the loser column must be shown.
	 *
	 * @return TRUE if the loser column must be shown, FALSE otherwise
	 */
	public showLoserColumn(): boolean {
		return this.round === RoundName.THIEF;
	}
	
	/**
	 * Gets music name.
	 *
	 * @return the music name
	 */
	public getMusicName(): string {
		
		if ( this.music.name.endsWith(".mp3") )
			return this.music.name.substring(0, this.music.name.length-4);
		else
			return this.music.name;
	}
	
	/**
	 * Save music result and close modal.
	 */
	public save(): void {
		
		let authorWinners: string[] = [];
		let titleWinners: string[] = [];
		let losers: string[] = [];
		let penalties: string[] = [];
		
		for (let playerLine of this.lines) {
			
			if (playerLine.authorWinner)    authorWinners.push(playerLine.name);
			if (playerLine.titleWinner)     titleWinners.push(playerLine.name);
			if (playerLine.penalty) 		penalties.push(playerLine.name);
			for (let i = 0; i < playerLine.loser; i++)
				losers.push(playerLine.name);
		}
		
		let copyMusic: Music = new Music();
		copyMusic.name = this.music.name;
		copyMusic.theme = this.music.theme;
		
		let musicResult: MusicResult = {
			gameId: this.gameId,
			music: copyMusic,
			authorWinners: authorWinners,
			titleWinners: titleWinners,
			losers: losers,
			penalties: penalties
		};
		
		this.gameResource.apply(musicResult).subscribe(
			value => {
				this.ngbActiveModal.close(value);
			},
			error => {
				
				let text: string = this.translate.instant("GAME.MUSIC_RESULT_MODAL.SAVE_ERROR");
				let closeLabel: string = this.translate.instant("COMMON.GO_HOME");
				
				this.modalService.openErrorModal(text, error, true, closeLabel).then(
					() => { this.save(); },
					() => { this.ngbActiveModal.dismiss(); }
				);
			}
		);
	}
	
	/**
	 * Check or uncheck all.
	 *
	 * @param check TRUE for check all, FALSE for uncheck all
	 */
	public checkUncheckAll(check: boolean): void {
		
		this.lines.forEach( line => {
			if ( this.showAuthorColumn() )
				line.authorWinner = check;
			
			if ( this.showTitleColumn() )
				line.titleWinner = check;
		});
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
	loser: number,
	penalty: boolean;
}

/**
 * The loser item.
 */
interface PLayerLoserItem {
	id: number;
	label: string;
}
