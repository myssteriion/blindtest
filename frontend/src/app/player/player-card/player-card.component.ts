import { Component, Input, OnInit } from '@angular/core';
import { faCookieBite, faMedal, faPoo, faUserFriends } from '@fortawesome/free-solid-svg-icons';
import { TranslateService } from '@ngx-translate/core';
import { CommonUtilsService } from "myssteriion-utils";
import { Player } from "../../interfaces/game/player";
import { ADD_SCORE_ANIMATION, RANK_ICON_ANIMATION } from "../../tools/constant";

/**
 * Player card.
 */
@Component({
	selector: "player-card",
	templateUrl: "./player-card.component.html",
	styleUrls: ["./player-card.component.scss"],
	animations: [
		RANK_ICON_ANIMATION, ADD_SCORE_ANIMATION
	]
})
export class PlayerCardComponent implements OnInit {
	
	/**
	 * The player.
	 */
	@Input()
	public player: Player;
	
	/**
	 * True for show rank on right, false for left.
	 */
	@Input()
	public rankOnRight: boolean;
	
	/**
	 * True to display medal, false to force to hide.
	 */
	@Input()
	private displayMedal: boolean;
	
	/**
	 * True to display opacity.
	 */
	@Input()
	public displayOpacity: boolean;
	
	/**
	 * If show/hide add score.
	 */
	public showAddScore: boolean;
	
	/**
	 * Score to add.
	 */
	public scoreToAdd: number;
	
	public faMedal = faMedal;
	public faPoo = faPoo;
	public faCookieBite = faCookieBite;
	public faUserFriends = faUserFriends;
	
	
	
	constructor(private translate: TranslateService,
				private commonUtilsService: CommonUtilsService) {
	}
	
	ngOnInit(): void {
		this.showAddScore = false;
		this.scoreToAdd = 0;
	}
	
	
	
	/**
	 * Test if has 1st medal.
	 *
	 * @return TRUE if had 1st medal, FALSE otherwise
	 */
	public hasFirstMedal(): boolean {
		return this.displayMedal && this.player.rank === 1;
	}
	
	/**
	 * Test if has 2nd medal.
	 *
	 * @return TRUE if had 2nd medal, FALSE otherwise
	 */
	public hasSecondMedal(): boolean {
		return this.displayMedal && this.player.rank === 2;
	}
	
	/**
	 * Test if has 3rd medal.
	 *
	 * @return TRUE if had 3rd medal, FALSE otherwise
	 */
	public hasThirdMedal(): boolean {
		return this.displayMedal && this.player.rank === 3;
	}
	
	/**
	 * Test if has cookie.
	 *
	 * @return TRUE if had cookie, FALSE otherwise
	 */
	public hasCookie(): boolean {
		return this.displayMedal && this.player.rank === 4;
	}
	
	/**
	 * Test if has poop must be show.
	 *
	 * @return TRUE if the poop must be show, FALSE otherwise
	 */
	public hasPoop(): boolean {
		return this.displayMedal && this.player.last;
	}
	
	/**
	 * Test if has team.
	 *
	 * @return TRUE if has team., FALSE otherwise
	 */
	public hasTeam(): boolean {
		return this.displayMedal && this.player.teamNumber != -1;
	}
	
	/**
	 * Gets css team color.
	 *
	 * @return css team color
	 */
	public getCssTeam(): string {
		return "player-card-team-" + this.player.teamNumber;
	}
	
	/**
	 * Test if has icon(s).
	 *
	 * @return TRUE if has icon(s), FALSE otherwise
	 */
	public hasIcon(): boolean {
		return this.hasFirstMedal() || this.hasSecondMedal() || this.hasThirdMedal() || this.hasCookie() || this.hasPoop();
	}
	
	/**
	 * Get player.
	 */
	public getPlayer(): Player {
		return this.player;
	}
	
	/**
	 * Update player.
	 *
	 * @param player the player
	 */
	public async updatePLayer(player: Player): Promise<void> {
		
		this.player.rank = player.rank;
		this.player.teamNumber = player.teamNumber;
		this.player.last = player.last;
		this.player.turnToChoose = player.turnToChoose;
		
		if (this.player.score !== player.score) {
			
			this.scoreToAdd = player.score - this.player.score;
			this.showAddScore = true;
			
			this.player.score = player.score;
			await this.commonUtilsService.sleep(100);
			this.showAddScore = false;
		}
	}
	
}
