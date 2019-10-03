import { Component, Input } from '@angular/core';

@Component({
	selector: 'fa-icon-custom',
	templateUrl: './fa-icon-custom.component.html',
	styleUrls: ['./fa-icon-custom.component.css']
})
export class FaIconCustomComponent {

	@Input() icon: string;

	@Input() tooltip: string;



	constructor() { }

}
