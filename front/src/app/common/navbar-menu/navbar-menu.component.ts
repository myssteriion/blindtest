import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

/**
 * Navbar menu.
 */
@Component({
	selector: 'navbar-menu',
	templateUrl: './navbar-menu.component.html',
	styleUrls: ['./navbar-menu.component.css']
})
export class NavbarMenuComponent implements OnInit {

	/**
	 * Path to "logo" image in assets folder.
	 */
	private static _LOGO: string = "assets/images/logo.png";



	constructor(private _router: Router) { }

	ngOnInit() { }



	/**
	 * Gets logo.
	 */
	public getLogo() {
		return NavbarMenuComponent._LOGO;
	}

	/**
	 * Add 'isActive' css fot the current url.
	 *
	 * @param path
	 */
	public isActive(path: string): string {

		let customCss = "";

		if (path === this._router.url)
			customCss = 'active';

		return customCss
	}

}
