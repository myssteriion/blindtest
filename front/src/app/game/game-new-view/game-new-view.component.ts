import {Component, OnInit} from '@angular/core';
import {Profile} from "../../interfaces/profile.interface";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from "../../services/toaster.service";
import { ToolsService } from 'src/app/tools/tools.service';

/**
 * The profiles view.
 */
@Component({
    selector: 'game-new-view',
    templateUrl: './game-new-view.component.html',
    styleUrls: ['./game-new-view.component.css']
})
export class GameNewViewComponent implements OnInit {

    /**
     * Durations list.
     */
    public durations: string[] = ["VERY_SHORT", "SHORT", "NORMAL", "LONG", "VERY_LONG"];

    /**
     * Selected duration.
     */
    public selectedDuration: string;

	/**
	 * Players profiles and empty names.
	 */
	public playersProfiles: Profile[];

    private static MAX_PLAYERS: number = 12;



    constructor(private _translate : TranslateService,
                private _toasterService: ToasterService) {}

    ngOnInit() {
        this.playersProfiles = [];
        this.selectedDuration = this.durations[2];
    }



    /**
     * Select duration.
     *
     * @param index
     */
    public selectDuration(index: number) {
        this.selectedDuration = this.durations[index];
    }

	/**
	 * Gets empty players.
	 */
	public getEmptyNames(): string[] {

		let index = this.playersProfiles.length + 1;
		var emptyNames = [];

		while (index <= GameNewViewComponent.MAX_PLAYERS) {
			emptyNames.push( this._translate.instant("GAME.NEW.PLAYER") + " " + index );
			index++;
		}

		return emptyNames;
	}

	/**
	 * Deselect profile.
	 *
	 * @param profile
	 */
	public deselectProfile(profile: Profile) {
		let index: number = this.playersProfiles.indexOf(profile);
		if (index !== -1)
			this.playersProfiles.splice(index, 1);
	}

	/**
	 * Start game.
	 */
	public launchGame(): void {
		console.log("launch !");
	}

	/**
	 * Disabled lauch game button.
	 */
	public launchGameIsDisabled(): boolean {
		return ToolsService.isNullOrEmpty(this.selectedDuration) || this.playersProfiles.length < 2;
	}

}
