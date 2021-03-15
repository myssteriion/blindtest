import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
	selector: "profile-card-empty",
	templateUrl: "./profile-card-empty.component.html",
	styleUrls: ["./profile-card-empty.component.scss"]
})
export class ProfileCardEmptyComponent {
	
	/**
	 * Name.
	 */
	@Input()
	public name: string;
	
	/**
	 * Event after click.
	 */
	@Output()
	public onClick = new EventEmitter();
	
	
	
	constructor() { }
	
}
