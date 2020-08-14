import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ToolsService} from "../../tools/tools.service";

@Component({
	selector: 'profile-card-empty',
	templateUrl: './profile-card-empty.component.html',
	styleUrls: ['./profile-card-empty.component.css']
})
export class ProfileCardEmptyComponent implements OnInit {
	
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
	
	ngOnInit(): void {
		ToolsService.verifyStringValue("name", this.name);
	}
	
}
