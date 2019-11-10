import {Component, OnInit} from '@angular/core';
import {EFFECTS, EFFECTS_INDEX, MARIO_KART_SOUND, THEMES, THEMES_INDEX} from "../../../tools/constant";
import {ToolsService} from "../../../tools/tools.service";
import {Music} from "../../../interfaces/dto/music.interface";

/**
 * The theme effect part.
 */
@Component({
	selector: 'theme-effect',
	templateUrl: './theme-effect.component.html',
	styleUrls: ['./theme-effect.component.css']
})
export class ThemeEffectComponent implements OnInit {

	/**
	 * Show view.
	 */
	private show: boolean;

	/**
	 * The music.
	 */
	private music: Music;

	/**
	 * The theme image.
	 */
	private themeImg: string;

	/**
	 * The effect image.
	 */
	private effectImg: string;



	constructor() { }

	ngOnInit() {
		this.themeImg = THEMES[0];
		this.effectImg = EFFECTS[0];
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
	 * Sets music.
	 *
	 * @param music the music
	 */
	public setMusic(music: Music): void {
		this.music = music;
	}


	/**
	 * Roll theme and effect.
	 *
	 * @return (Promise<void>)
	 */
	public roll(): Promise<void> {

		return new Promise(async (resolve) => {

			if ( !ToolsService.isNull(this.music) ) {

				let themeIndex = THEMES_INDEX.findIndex(theme => theme === this.music.theme);
				let effectIndex = EFFECTS_INDEX.findIndex(effect => effect === this.music.effect);

				let audioObj = new Audio();
				audioObj.src = MARIO_KART_SOUND;
				audioObj.load();
				audioObj.play();

				while (!audioObj.ended) {
					this.themeImg = THEMES[ToolsService.random(0, THEMES_INDEX.length - 1)];
					this.effectImg = EFFECTS[ToolsService.random(0, EFFECTS_INDEX.length - 1)];
					await ToolsService.sleep(100);
				}

				this.themeImg = THEMES[themeIndex];
				this.effectImg = EFFECTS[effectIndex];
			}

			resolve();
		});
	}

}
