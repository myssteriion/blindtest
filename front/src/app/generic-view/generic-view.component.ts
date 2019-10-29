import {Component} from '@angular/core';
import {GENERIC, routesWithHome} from "../tools/constant";
import {Router} from '@angular/router';

/**
 * Generic View.
 */
@Component({
	selector: 'generic-view',
	templateUrl: './generic-view.component.html',
	styleUrls: ['./generic-view.component.css']
})
export class GenericViewComponent {

	constructor(private _router: Router) { }



	/**
	 * Gets generic.
	 */
	public getGeneric(): string {
		return GENERIC;
	}

	/**
	 * Stop generic.
	 */
	public stopGeneric() {
		this._router.navigateByUrl( routesWithHome[0].path );
	}

}
