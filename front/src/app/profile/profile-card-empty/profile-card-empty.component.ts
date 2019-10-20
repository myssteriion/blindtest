import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
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
	@Input() name: string;

	/**
	 * Event after click.
	 */
	@Output() onClick = new EventEmitter();



	constructor() { }

	ngOnInit() {
		ToolsService.verifyStringValue("name", this.name);
	}

}