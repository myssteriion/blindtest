import { Component, OnInit, Input } from '@angular/core';
import {ToolsService} from "../../tools/tools.service";

@Component({
	selector: 'header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

	@Input() title: string;



	constructor(private _toolsService: ToolsService) { }

	ngOnInit() {
		this._toolsService.verifyValue("title", this.title);
	}

}
