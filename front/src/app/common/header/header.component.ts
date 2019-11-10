import {Component, Input, OnInit} from '@angular/core';

/**
 * Header for all views.
 */
@Component({
	selector: 'header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

	/**
	 * Title.
	 */
	@Input()
	private title: string;



	constructor() { }

	ngOnInit() { }

}
