import { translate } from "@angular/localize/src/translate";
import { faAddressCard, faAngleDoubleRight, faGamepad } from "@fortawesome/free-solid-svg-icons";
import { Route } from "myssteriion-utils";



/**
 * HOME route.
 */
export const HOME_ROUTE: Route = new Route("/home", "HOME_VIEW.TITLE");



/**
 * Routes without HOME.SLIDE_ANIMATION
 */
export const ROUTES_WITHOUT_HOME: Route[] = [
	new Route("/game/new", "GAME.NEW_VIEW.TITLE", faGamepad),
	new Route("/game/resume", "GAME.RESUME_VIEW.TITLE", faAngleDoubleRight),
	new Route("/profiles", "PROFILE.VIEW.TITLE", faAddressCard)
];

/**
 * Routes with HOME.
 */
export const ROUTES_WITH_HOME: Route[] = [HOME_ROUTE].concat(ROUTES_WITHOUT_HOME);



/**
 * GAME route.
 */
export const GAME_ROUTE: Route = new Route("/game/:id");

/**
 * GAME_END route.
 */
export const GAME_END_ROUTE: Route = new Route("/game/end/:id");

/**
 * All routes.
 */
export const ALL_ROUTES: Route[] = ROUTES_WITH_HOME.concat(GAME_ROUTE, GAME_END_ROUTE);
