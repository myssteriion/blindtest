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
	public podiumRank: number[];
	
	
	
	constructor(private _translate: TranslateService) { }
	
	ngOnInit(): void {
		this.podiumRank = [1, 2, 3];
	}
	
	
	
	/**
	 * If rank had player.
	 *
	 * @param rank the rank
	 */
	private hadPlayerByRank(rank: number): boolean {
		return (this.game.players.filter(player => player.rank === rank).length) > 0;
	}
	
	/**
	 * Gets rank logo.
	 *
	 * @param rank the rank
	 * @return the image
	 */
	private getImgByRank(rank: number): string {
		switch (rank) {
			case 1:     return RANKS_FIRST;
			case 2:     return RANKS_SECOND;
			case 3:     return RANKS_THIRD;
			default:    return ""
		}
	}
	
	/**
	 * Gets rank tooltip.
	 *
	 * @param rank the rank
	 * @return the tooltip
	 */
	private getTooltipByRank(rank: number): string {
		return this._translate.instant("RANK." + rank);
	}
	
	/**
	 * Gets players by ranks.
	 *
	 * @param rank the rank
	 * @return players list
	 */
	private getPlayersByRank(rank: number): Player[] {
		return this.game.players.filter(player => player.rank === rank);
	}
	
	/**
	 * Gets no-podium players.
	 *
	 * @return players list
	 */
	public getNoPodiumPlayers(): Player[] {
		
		let players: Player[] = [];
		
		let noPodiumRank: number[] = [];
		for (let i: number = 4; i <= this.game.players.length; i++) {
			noPodiumRank.push(i);
		}
		
		for (let rank of noPodiumRank)
			players = players.concat( this.getPlayersByRank(rank) );
		
		return players;
	}
	
}
