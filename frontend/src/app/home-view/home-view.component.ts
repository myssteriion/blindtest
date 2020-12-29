import { Component } from '@angular/core';
import { ROUTES_WITHOUT_HOME, SLIDE_ANIMATION } from "../tools/constant";

/**
 * HomeView.
 */
@Component({
	templateUrl: "./home-view.component.html",
	styleUrls: ['./home-view.component.css'],
	animations: [
		SLIDE_ANIMATION
	]
})
export class HomeViewComponent {
	
	/**
	 * Routes (without home).
	 */
	public routes = ROUTES_WITHOUT_HOME;
	
	
	
	constructor() { }
	
}
