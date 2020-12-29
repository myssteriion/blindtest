import { Component } from '@angular/core';
import { SLIDE_ANIMATION } from "../tools/constant";

@Component({
	templateUrl: "./params-view.component.html",
	styleUrls: ["./params-view.component.css"],
	animations: [
		SLIDE_ANIMATION
	]
})
export class ParamsViewComponent {
	
	constructor() { }
	
}
