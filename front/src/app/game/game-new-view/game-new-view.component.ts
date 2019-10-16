import {Component, OnInit} from '@angular/core';
import {ProfileResource} from "../../resources/profile.resource";
import {Page} from "../../interfaces/page.interface";
import {Profile} from "../../interfaces/profile.interface";
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {ToolsService} from "../../tools/tools.service";
import { TranslateService } from '@ngx-translate/core';
import {ToasterService} from "../../services/toaster.service";

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
     * Select profile.
     *
     * @param profile
     */
    public selectProfile(profile: Profile) {

        let index: number = this.playersProfiles.indexOf(profile);

        if ( this.playersProfiles.length >= GameNewViewComponent.MAX_PLAYERS ) {
            let message = this._translate.instant( "GAME.NEW.MAX_PLAYERS_ERROR", { max_players: GameNewViewComponent.MAX_PLAYERS}  );
            this._toasterService.error(message);
        }
        else if (index !== -1) {
            let message = this._translate.instant( "GAME.NEW.DUPLICATE_PLAYERS_ERROR", { player_name: profile.name } );
            this._toasterService.error(message);
        }
        else
            this.playersProfiles.push(profile);
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

    public launchGame(): void {
        console.log("launch !");
    }

    public launchGameIsDisabled(): boolean {
        return ToolsService.isNullOrEmpty(this.selectedDuration) || this.playersProfiles.length < 2;
    }

}
