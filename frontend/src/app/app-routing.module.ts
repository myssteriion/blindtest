import { NgModule } from "@angular/core";
import { Route as AngularRoute, RouterModule } from "@angular/router";
import { GameCurrentViewComponent } from "./game/game-current-view/game-current-view.component";
import { GameEndViewComponent } from "./game/game-end-view/game-end-view.component";
import { GameNewViewComponent } from "./game/game-new-view/game-new-view.component";
import { GameResumeViewComponent } from "./game/game-resume-view/game-resume-view.component";
import { HomeViewComponent } from "./home-view/home-view.component";
import { ProfileViewComponent } from "./profile/profile-view/profile-view.component";
import { ALL_ROUTES, HOME_ROUTE } from "./tools/routing.constant";


/**
 * All angular routes.
 */
const angularRoutes: AngularRoute[] = [
	ALL_ROUTES[0].transformToAngularRoute(HomeViewComponent),
	ALL_ROUTES[1].transformToAngularRoute(GameNewViewComponent),
	ALL_ROUTES[2].transformToAngularRoute(GameResumeViewComponent),
	ALL_ROUTES[3].transformToAngularRoute(ProfileViewComponent),
	ALL_ROUTES[4].transformToAngularRoute(GameCurrentViewComponent),
	ALL_ROUTES[5].transformToAngularRoute(GameEndViewComponent),
	{ path: "", redirectTo: HOME_ROUTE.getAngularRouteUrl(), pathMatch: "full" },
	{ path: "**", redirectTo: HOME_ROUTE.getAngularRouteUrl() }
];

@NgModule({
	imports: [ RouterModule.forRoot(angularRoutes) ],
	exports: [ RouterModule ]
})
export class AppRoutingModule { }
