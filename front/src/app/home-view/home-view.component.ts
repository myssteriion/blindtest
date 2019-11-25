import {Component} from '@angular/core';
import {LOGO, routesWithoutHome, SLIDE_ANIMATION} from "../tools/constant";

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
	 * Route (without home).
	 */
	private routes = routesWithoutHome;



	constructor() { }



	/**
	 * Gets logo.
	 */
	private getLogo(): string {
		return LOGO;
	}

}
