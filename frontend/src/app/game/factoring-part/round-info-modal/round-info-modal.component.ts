import { Component, Input } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { RoundName } from "../../../interfaces/common/round-name.enum";
import { Game } from "../../../interfaces/game/game";
import { Friendship } from "../../../interfaces/round/impl/friendship";
import { Lucky } from "../../../interfaces/round/impl/lucky";

/**
 * The round info modal.
 */
@Component({
	templateUrl: "./round-info-modal.component.html",
	styleUrls: ["./round-info-modal.component.scss"]
})
export class RoundInfoModalComponent {
	
	/**
	 * The game.
	 */
	@Input()
	public game: Game;
	
	
	
	constructor(private ngbActiveModal: NgbActiveModal,
				private translate: TranslateService) {}
	
	
	
	/**
	 * Gets the round description.
	 *
	 * @return gets the round description
	 */
	public getDescription(): string {
		
		let params = {
			roundName: this.translate.instant("ROUND." + this.game.round.roundName + ".NAME"),
			nbMusics: this.game.round.nbMusics,
			nbPlayers: 0,
			nbTeams: 0
		};
		
		if (this.game.round.roundName === RoundName.LUCKY)
			params.nbPlayers = (<Lucky>this.game.round).nbPlayers;
		
		if (this.game.round.roundName === RoundName.FRIENDSHIP)
			params.nbTeams = (<Friendship>this.game.round).nbTeams;
		
		return this.translate.instant("ROUND." + this.game.round.roundName + ".DESCRIPTION", params);
	}
	
	/**
	 * Gets nb point won.
	 *
	 * @return gets nb point won
	 */
	public getNbPointWon(): string {
		
		let npPointWon = this.game.round.nbPointWon.toString();
		
		if (this.game.round.roundName === RoundName.RECOVERY)
			npPointWon += " x " + this.translate.instant("COMMON.RANK");
		
		return npPointWon;
	}
	
	/**
	 * Test if lose point part must be shown.
	 *
	 * @return TRUE if lose point part must be shown, FALSE otherwise
	 */
	public showNbPointLose(): boolean {
		return this.game.round.roundName === RoundName.THIEF;
	}
	
	/**
	 * Test if bonus point part must be shown.
	 *
	 * @return TRUE if bonus point part must be shown, FALSE otherwise
	 */
	public showNbPointBonus(): boolean {
		return this.game.round.roundName === RoundName.CHOICE || this.game.round.roundName === RoundName.LUCKY;
	}
	
	/**
	 * Test if malus point part is showed.
	 *
	 * @return TRUE if malus point part must be shown, FALSE otherwise
	 */
	public showNpPointMalus(): boolean {
		return this.game.round.roundName === RoundName.CHOICE;
	}
	
	/**
	 * Close modal.
	 */
	public close(): void {
		this.ngbActiveModal.close();
	}
	
}
