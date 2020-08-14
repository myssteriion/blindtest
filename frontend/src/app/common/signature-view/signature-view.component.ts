import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";

/**
 * The version part.
 */
@Component({
	selector: 'signature-view',
	templateUrl: './signature-view.component.html',
	styleUrls: ['./signature-view.component.css']
})
export class SignatureViewComponent {
	
	/**
	 * The version.
	 */
	public version: string = environment.version;
	
	
	
	constructor() { }
	
}
