import { Component, OnInit, Input } from '@angular/core';
import {ToolsService} from "../../tools/tools.service";

@Component({
	selector: 'profile-card-empty',
	templateUrl: './profile-card-empty.component.html',
	styleUrls: ['./profile-card-empty.component.css']
})
export class ProfileCardEmptyComponent implements OnInit {

	@Input() name: string;



	constructor() { }

	ngOnInit() {
		ToolsService.verifyStringValue("name", this.name);
	}

}
