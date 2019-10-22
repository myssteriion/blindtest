import {Component, ViewChild} from '@angular/core';
import {routesWithHome} from "../tools/constant";
import {Router} from '@angular/router';

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
	 * Path to "not-found" image in assets folder.
	 */
	private static _GENERIC: string = "assets/videos/generic.mp4";

	@ViewChild('video', { static: false }) matVideo: any;

	video: HTMLVideoElement;



	constructor(private _router: Router) { }

	ngAfterViewInit() {
		this.video = this.matVideo.getVideoTag();
		this.video.addEventListener( 'ended', () => this.stopGeneric() );
	}



	/**
	 * Gets generic.
	 */
	public getGeneric(): string {
		return GenericViewComponent._GENERIC;
	}

	/**
	 * Stop generic.
	 */
	public stopGeneric() {
		this._router.navigateByUrl( routesWithHome[0].path );
	}

}
