import {Component, OnInit} from '@angular/core';
import {SLIDE_ANIMATION} from "../tools/constant";

@Component({
	selector: 'params-view',
  	templateUrl: './params-view.component.html',
  	styleUrls: ['./params-view.component.css'],
	animations: [
		SLIDE_ANIMATION
	]
})
export class ParamsViewComponent implements OnInit {

  	constructor() { }
	
	ngOnInit() {
 
	}

}
