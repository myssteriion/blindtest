import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { NavbarMenuConfig, Route, SignatureConfig } from "myssteriion-utils";
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
	styleUrls: ["./app.component.scss"]
})
export class AppComponent implements OnInit {
	
	/**
	 * The navbar menu config.
	 */
	public navbarMenuConfig: NavbarMenuConfig;
	
	/**
	 * The signature config.
	 */
	public signatureConfig: SignatureConfig;
	
	/**
	 * For ng-http-loader.
	 */
	Spinkit = Spinkit;
	
	
	
	constructor(private translate: TranslateService) { }
	
	ngOnInit(): void {
		
		this.translate.setDefaultLang("fr");
		this.translate.use("fr");
		
		this.signatureConfig = { author: "Myssteriion", version: environment.version };
		this.navbarMenuConfig = { homeRoute: HOME_ROUTE, routes: ROUTES_WITHOUT_HOME, logoPath: LOGO, audioPath: FFXII_THEME };
	}
	
}
