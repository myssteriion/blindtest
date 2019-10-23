import {Component, ViewChild} from '@angular/core';
import {routesWithHome} from "../tools/constant";
import {Router} from '@angular/router';
import {ToolsService} from "../tools/tools.service";

/**
 * Generic View.
 */
@Component({
	selector: 'generic-view',
	templateUrl: './generic-view.component.html',
	styleUrls: ['./generic-view.component.css']
})
export class GenericViewComponent {

	/**
	 * Path to "generic" video in assets folder.
	 */
	private static GENERIC: string = "assets/videos/generic.mp4";

	/**
	 * Mat video.
	 */
	@ViewChild('matVideo', { static: false }) matVideo: any;

	/**
	 * The video from MatVideo
	 */
	private video: HTMLVideoElement;



	constructor(private _router: Router) { }

	ngAfterViewInit() {
		if ( !ToolsService.isNull(this.matVideo) ) {
			this.video = this.matVideo.getVideoTag();
			this.video.addEventListener( 'ended', () => this.stopGeneric() );
		}
	}



	/**
	 * Gets generic.
	 */
	public getGeneric(): string {
		return GenericViewComponent.GENERIC;
	}

	/**
	 * Stop generic.
	 */
	public stopGeneric() {
		this._router.navigateByUrl( routesWithHome[0].path );
	}

}
