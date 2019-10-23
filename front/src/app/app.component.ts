import {Component, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {routesWithHome} from "./tools/constant";
import {Router} from '@angular/router';
import {NavbarMenuComponent} from "./common/navbar-menu/navbar-menu.component";
import {ToolsService} from "./tools/tools.service";

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
    @ViewChild('navbarMenu', { static: false }) navbarMenu: NavbarMenuComponent;

    /**
     * Last state of navbar menu.
     */
    private lastShowNavbar: boolean;



    constructor(private _translate: TranslateService,
                private _router: Router) {

        _translate.setDefaultLang('fr');
        _translate.use('fr');
    }

    public ngOnInit() { }



    /**
     * Gets ShowNavbar.
     */
    public showNavbar(): boolean {

        let i: number = 0;

        let showNavbar: boolean = false;
        while (!showNavbar && i < routesWithHome.length) {

            showNavbar = this._router.url === routesWithHome[i].path;
            i++;
        }

        this.forceStopNavbarAudio(showNavbar);

        return showNavbar;
    }

    /**
     * Check if the navbar audio must be forced to stop.
     *
     * @param showNavbar the new state
     */
    private forceStopNavbarAudio(showNavbar: boolean) {

        if ( !ToolsService.isNull(this.lastShowNavbar) && !ToolsService.isNull(this.navbarMenu) ) {
            if (!showNavbar && this.lastShowNavbar) {
                this.navbarMenu.stopMusic();
            }
        }

        this.lastShowNavbar = showNavbar;
    }

} 
