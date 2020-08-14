import {Component, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ROUTES_WITH_HOME} from "./tools/constant";
import {Router} from '@angular/router';
import {NavbarMenuComponent} from "./common/navbar-menu/navbar-menu.component";
import {Spinkit} from 'ng-http-loader';

/**
 * App root.
 */
@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {
	
	/**
	 * Navbar menu.
	 */
	@ViewChild('navbarMenu', { static: false })
	private navbarMenu: NavbarMenuComponent;
	
	Spinkit = Spinkit;
	
	
	
	constructor(private _translate: TranslateService,
				private _router: Router) {
		
		_translate.setDefaultLang('fr');
		_translate.use('fr');
	}
	
	
	
	/**
	 * Gets ShowNavbar.
	 */
	public showNavbar(): boolean {
		
		let i: number = 0;
		
		let showNavbar: boolean = false;
		while (!showNavbar && i < ROUTES_WITH_HOME.length) {
			
			showNavbar = (this._router.url === ROUTES_WITH_HOME[i].path);
			i++;
		}
		
		return showNavbar;
	}
	
}
