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
	public routes = routesWithoutHome;



	constructor() { }

	ngAfterViewInit() { }



	/**
	 * Gets logo.
	 */
	public getLogo() {
		return LOGO;
	}

}
