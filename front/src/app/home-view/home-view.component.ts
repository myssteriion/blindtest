import {Component, ViewChild} from '@angular/core';
import {AppComponent} from '../app.component';
import {SLIDE_ANIMATION, routesWithoutHome} from "../tools/constant";

/**
 * HomeView.
 */
@Component({
	selector: 'home-view',
	templateUrl: './home-view.component.html',
	styleUrls: ['./home-view.component.css'],
	animations: [
		SLIDE_ANIMATION
	]
})
export class HomeViewComponent {

	/**
	 * Show generic.
	 */
	private static _SHOW_GENERIC = true;

	/**
	 * Path to "not-found" image in assets folder.
	 */
	private static _GENERIC: string = "assets/videos/generic.mp4";

	@ViewChild('video', { static: false }) matVideo: any;

	video: HTMLVideoElement;

	/**
	 * Path to "logo" image in assets folder.
	 */
	private static _LOGO: string = "assets/images/logo.png";

	/**
	 * Route (without home).
	 */
	public routes = routesWithoutHome;



	constructor() { }

	ngAfterViewInit() {
		if (HomeViewComponent._SHOW_GENERIC) {
			this.video = this.matVideo.getVideoTag();
			this.video.addEventListener( 'ended', () => this.stopGeneric() );
		}
	}



	/**
	 * Gets generic.
	 */
	public getGeneric(): string {
		return HomeViewComponent._GENERIC;
	}

	/**
	 * If show generic.
	 */
	public showGeneric(): boolean {
		return HomeViewComponent._SHOW_GENERIC;
	}

	/**
	 * Stop generic.
	 */
	public stopGeneric() {
		AppComponent.setShowNavbar(true);
		HomeViewComponent._SHOW_GENERIC = false;
	}

	/**
	 * Gets logo.
	 */
	public getLogo() {
		return HomeViewComponent._LOGO;
	}

}
