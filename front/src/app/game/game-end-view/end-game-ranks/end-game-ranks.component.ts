import {Component, Input, OnInit} from '@angular/core';
import {Game} from "../../../interfaces/game/game.interface";
import {RANKS_FIRST, RANKS_SECOND, RANKS_THIRD} from "../../../tools/constant";
import {Player} from "../../../interfaces/game/player.interface";
import {TranslateService} from '@ngx-translate/core';

@Component({
	selector: 'end-game-ranks',
	templateUrl: './end-game-ranks.component.html',
	styleUrls: ['./end-game-ranks.component.css']
})
export class EndGameRanksComponent implements OnInit {

	/**
	 * The game.
	 */
	@Input()
	private game: Game;

	/**
	 * The podium ranks.
	 */
	private podiumRank: Rank[];



	constructor(private _translate: TranslateService) { }

	ngOnInit(): void {
		this.podiumRank = [Rank.FIRST, Rank.SECOND, Rank.THIRD];
	}



	/**
	 * If rank had player.
	 *
	 * @param rank the rank
	 */
	private hadPlayerByRank(rank: Rank): boolean {
		return (this.game.players.filter(player => player.rank === rank).length) > 0;
	}

	/**
	 * Gets rank logo.
	 *
	 * @param rank the rank
	 * @return the image
	 */
	private getImgByRank(rank: Rank): string {
		switch (rank) {
			case Rank.FIRST:	return RANKS_FIRST;
			case Rank.SECOND:	return RANKS_SECOND;
			case Rank.THIRD:	return RANKS_THIRD;
			default: 			return ""
		}
	}

	/**
	 * Gets rank tooltip.
	 *
	 * @param rank the rank
	 * @return the tooltip
	 */
	private getTooltipByRank(rank: Rank): string {
		return this._translate.instant("RANK." + rank);
	}

	/**
	 * Gets players by ranks.
	 *
	 * @param rank the rank
	 * @return players list
	 */
	private getPlayersByRank(rank: Rank): Player[] {
		return this.game.players.filter(player => player.rank === rank);
	}

	/**
	 * Gets no-podium players.
	 *
	 * @return players list
	 */
	private getNoPodiumPlayers(): Player[] {

		let players: Player[] = [];

		let noPodiumRank = [Rank.FOURTH, Rank.FIFTH, Rank.SIXTH, Rank.SEVENTH, Rank.EIGHTH, Rank.NINTH, Rank.TENTH, Rank.ELEVENTH, Rank.TWELFTH];
		for (let rank of noPodiumRank)
			players = players.concat( this.getPlayersByRank(rank) );

		return players;
	}

}
