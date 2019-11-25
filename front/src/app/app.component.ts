import {Component, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {routesWithHome} from "./tools/constant";
import {Router} from '@angular/router';
import {NavbarMenuComponent} from "./common/navbar-menu/navbar-menu.component";

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
        while (!showNavbar && i < routesWithHome.length) {

            showNavbar = (this._router.url === routesWithHome[i].path);
            i++;
        }

        return showNavbar;
    }

}
