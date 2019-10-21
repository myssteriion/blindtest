import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

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
	 * Show navbar.
	 */
	private static _SHOW_NAVBAR = false;



	constructor(translate: TranslateService) {
		translate.setDefaultLang('fr');
		translate.use('fr');
	}

	public ngOnInit() { }


	/**
	 * Gets ShowNavbar.
	 */
	public showNavbar(): boolean {
		return AppComponent._SHOW_NAVBAR;
	}

	/**
	 * Sets ShowNavbar.
	 *
	 * @param show
	 */
	public static setShowNavbar(show: boolean) {
		AppComponent._SHOW_NAVBAR = show;
	}

} 
