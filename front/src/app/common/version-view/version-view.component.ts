import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";

/**
 * The version part.
 */
@Component({
	selector: 'version-view',
	templateUrl: './version-view.component.html',
	styleUrls: ['./version-view.component.css']
})
export class VersionViewComponent implements OnInit {

	/**
	 * The version.
	 */
	public version: string = environment.version;



	constructor() { }

	ngOnInit() {
	}

}
