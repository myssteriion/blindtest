import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {routesWithHome} from "./tools/constant";
import {Router} from '@angular/router';

/**
 * App root.
 */
@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {

    public routes = routesWithHome;



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
        while (!showNavbar && i < this.routes.length) {

            showNavbar = this._router.url === this.routes[i].path;
            i++;
        }

        return showNavbar;
    }

} 
