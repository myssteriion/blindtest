import { Component } from "@angular/core";
import { Route } from "myssteriion-utils";
import { SLIDE_ANIMATION } from "../tools/constant";
import { ROUTES_WITHOUT_HOME } from "../tools/routing.constant";

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
	public routes: Route[] = ROUTES_WITHOUT_HOME;
	
	
	
	constructor() { }
	
}
