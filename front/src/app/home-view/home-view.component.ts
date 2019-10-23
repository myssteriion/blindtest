import {Component} from '@angular/core';
import {routesWithoutHome, SLIDE_ANIMATION} from "../tools/constant";

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
	 * Path to "logo" image in assets folder.
	 */
	private static LOGO: string = "assets/images/logo.png";

	/**
	 * Route (without home).
	 */
	public routes = routesWithoutHome;



	constructor() { }

	ngAfterViewInit() { }



	/**
	 * Gets logo.
	 */
	public getLogo() {
		return HomeViewComponent.LOGO;
	}

}
