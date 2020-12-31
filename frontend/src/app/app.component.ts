import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { Route } from "myssteriion-utils";
import { Spinkit } from "ng-http-loader";
import { environment } from "../environments/environment";
import { FFXII_THEME, LOGO } from "./tools/constant";
import { HOME_ROUTE, ROUTES_WITHOUT_HOME } from "./tools/routing.constant";

/**
 * App root.
 */
@Component({
	selector: "app-root",
	templateUrl: "./app.component.html",
	styleUrls: ["./app.component.css"]
})
export class AppComponent {
	
	/**
	 * The author.
	 */
	public author: string = "Myssteriion";
	
	/**
	 * The version.
	 */
	public version: string = environment.version;
	
	/**
	 * Audio path.
	 */
	public audioPath: string = FFXII_THEME;
	
	/**
	 * Logo path.
	 */
	public logoPath: string = LOGO;
	
	/**
	 * Home route.
	 */
	public homeRoute: Route = HOME_ROUTE;
	
	/**
	 * Routes.
	 */
	public routes: Route[] = ROUTES_WITHOUT_HOME;
	
	/**
	 * For ng-http-loader.
	 */
	Spinkit = Spinkit;
	
	
	
	constructor(private translate: TranslateService,
				private router: Router) {
		
		translate.setDefaultLang("fr");
		translate.use("fr");
	}
	
}
