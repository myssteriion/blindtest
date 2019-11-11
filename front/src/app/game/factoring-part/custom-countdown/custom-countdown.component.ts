import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {COUNTDOWN_SOUND, REDUCTION_ANIMATION} from "../../../tools/constant";
import {CountdownComponent, CountdownConfig} from 'ngx-countdown';
import {ToolsService} from "../../../tools/tools.service";

/**
 * The custom countdown part.
 */
@Component({
	selector: 'custom-countdown',
	templateUrl: './custom-countdown.component.html',
	styleUrls: ['./custom-countdown.component.css'],
	animations: [
		REDUCTION_ANIMATION
	]
})
export class CustomCountdownComponent implements OnInit {

	/**
	 * The countdown config.
	 */
	@Input()
	private countdownConfig: CountdownConfig;

	/**
	 * The animation.
	 */
	@Input()
	private animation: string;

	/**
	 * Play sound on countdown.
	 */
	@Input()
	private sound: boolean;

	/**
	 * On end of the countdown.
	 */
	@Output()
	private onEnd = new EventEmitter();

	/**
	 * The countdown.
	 */
	@ViewChild("countdown", { static: false })
	private countdown: CountdownComponent;

	/**
	 * Countdown animation.
	 */
	public animationTriggerValue: string;

	/**
	 * Show view.
	 */
	private show: boolean;

	/**
	 * Audio.
	 */
	private audio;


	constructor() { }

	ngOnInit() {
		this.show = false;

		this.audio = new Audio();
		this.audio.src = COUNTDOWN_SOUND;
		this.audio.load();
	}



	/**
	 * Sets show.
	 *
	 * @param show the show
	 */
	public setShow(show: boolean): void {
		this.show = show;
	}

	/**
	 * Start the countdown.
	 */
	public start(): void {
		this.countdown.restart();
		this.countdown.begin();
	}

	/**
	 * The event callback. Send event on done.
	 */
	private async countdownEvent(event) {

		if (event.action === "done") {
			this.onEnd.emit();
		}
		else if (this.animation === "reduction") {

			this.animationTriggerValue = "big";
			await ToolsService.sleep(100);

			this.audio = new Audio();
			this.audio.src = COUNTDOWN_SOUND;
			this.audio.load();

			// if (this.sound)
				this.audio.play();

			this.animationTriggerValue = "normal";
		}
	}

}
