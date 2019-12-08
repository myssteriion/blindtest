import {Component, Input} from '@angular/core';

/**
 * Header for all views.
 */
@Component({
	selector: 'header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.css']
})
export class HeaderComponent {

	/**
	 * Title.
	 */
	@Input()
	public title: string;



	constructor() { }

}
