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
	private getGeneric(): string {
		return GENERIC;
	}

	/**
	 * Stop generic.
	 */
	private stopGeneric() {
		this._router.navigateByUrl( routesWithHome[0].path );
	}

}
