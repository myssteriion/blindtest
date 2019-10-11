import { Component, Input } from '@angular/core';

/**
 * Custom Fa-Icon.
 */
@Component({
	selector: 'fa-icon-custom',
	templateUrl: './fa-icon-custom.component.html',
	styleUrls: ['./fa-icon-custom.component.css']
})
export class FaIconCustomComponent {

	/**
	 * Icon name.
	 */
	@Input() icon: string;

	/**
	 * Icon tooltip.
	 */
	@Input() tooltip: string;



	constructor() { }

}
