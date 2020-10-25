import {Component, Input} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Game} from "../../../interfaces/game/game.interface";
import {TranslateService} from '@ngx-translate/core';
import {Lucky} from "../../../interfaces/round/impl/lucky.interface";
import {Friendship} from "../../../interfaces/round/impl/friendship.interface";

/**
 * The round info modal.
 */
@Component({
	selector: 'round-info-modal',
	templateUrl: './round-info-modal.component.html',
	styleUrls: ['./round-info-modal.component.css']
})
export class RoundInfoModalComponent {
	
	/**
	 * The game.
	 */
	@Input()
	public game: Game;
	
	
	
	constructor(private _ngbActiveModal: NgbActiveModal,
				private _translate: TranslateService) {}
	
	
	
	/**
	 * Gets the round description.
	 */
	public getDescription(): string {
		
		let params = {
			roundName: this._translate.instant("ROUND." + this.game.round.roundName + ".NAME"),
			nbMusics: this.game.round.nbMusics,
			nbPlayers: undefined,
			nbTeams: undefined
		};
		
		if (this.game.round.roundName === RoundName.LUCKY)
			params.nbPlayers = (<Lucky>this.game.round).nbPlayers;
		
		if (this.game.round.roundName === RoundName.FRIENDSHIP)
			params.nbTeams = (<Friendship>this.game.round).nbTeams;
		
		return this._translate.instant("ROUND." + this.game.round.roundName + ".DESCRIPTION", params);
	}
	
	/**
	 * Gets nb point won.
	 */
	public getNpPointWon(): string {
		
		let npPointWon = this.game.round.nbPointWon.toString();
		
		if (this.game.round.roundName === RoundName.RECOVERY)
			npPointWon += " x " + this._translate.instant("COMMON.RANK");
		
		return npPointWon;
	}
	
	/**
	 * If lose point part is showed.
	 */
	public showNpPointLose(): boolean {
		return this.game.round.roundName === RoundName.THIEF;
	}
	
	/**
	 * If bonus point part is showed.
	 */
	public showNpPointBonus(): boolean {
		return this.game.round.roundName === RoundName.CHOICE || this.game.round.roundName === RoundName.LUCKY;
	}
	
	/**
	 * If malus point part is showed.
	 */
	public showNpPointMalus(): boolean {
		return this.game.round.roundName === RoundName.CHOICE;
	}
	
	/**
	 * Close modal.
	 */
	public close(): void {
		this._ngbActiveModal.close();
	}
	
}
