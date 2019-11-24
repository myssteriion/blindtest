import {Component, OnInit} from '@angular/core';
import {EFFECTS, MARIO_KART_SOUND, THEMES} from "../../../tools/constant";
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

	/**
	 * Audio.
	 */
	private audio;

	/**
	 * If show effect.
	 */
	private showEffect: boolean;



	constructor() { }

	ngOnInit() {
		this.themeImg = THEMES[0].srcImg;
		this.effectImg = EFFECTS[0].srcImg;

		this.audio = new Audio();
		this.audio.src = MARIO_KART_SOUND;
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
	 * Sets music.
	 *
	 * @param music the music
	 */
	public setMusic(music: Music): void {
		this.music = music;
		this.showEffect = this.music.connectionMode === ConnectionMode.OFFLINE;
	}


	/**
	 * Roll theme and effect.
	 *
	 * @param rollTheme if the theme must be roll
	 * @return (Promise<void>)
	 */
	public roll(rollTheme: boolean): Promise<void> {

		return new Promise(async (resolve) => {

			if ( this.music.connectionMode === ConnectionMode.ONLINE && rollTheme) {

				let themeIndex = THEMES.findIndex(theme => theme.enumVal === this.music.theme);
				this.themeImg = THEMES[themeIndex].srcImg;
				this.audio.play();

				while (!this.audio.ended) {
					this.themeImg = THEMES[ToolsService.random(0, THEMES.length - 1)].srcImg;
					await ToolsService.sleep(100);
				}

				this.themeImg = THEMES[themeIndex].srcImg;
			}
			else if (this.music.connectionMode === ConnectionMode.ONLINE && !rollTheme) {

				let themeIndex = THEMES.findIndex(theme => theme.enumVal === this.music.theme);
				this.themeImg = THEMES[themeIndex].srcImg;
			}
			else if ( this.music.connectionMode === ConnectionMode.OFFLINE ) {

				let themeIndex = THEMES.findIndex(theme => theme.enumVal === this.music.theme);
				let effectIndex = EFFECTS.findIndex(effect => effect.enumVal === this.music.effect);

				this.themeImg = THEMES[themeIndex].srcImg;
				this.effectImg = EFFECTS[effectIndex].srcImg;

				this.audio.play();

				while (!this.audio.ended) {
					if (rollTheme)
						this.themeImg = THEMES[ToolsService.random(0, THEMES.length - 1)].srcImg;

					this.effectImg = EFFECTS[ToolsService.random(0, EFFECTS.length - 1)].srcImg;

					await ToolsService.sleep(100);
				}

				this.themeImg = THEMES[themeIndex].srcImg;
				this.effectImg = EFFECTS[effectIndex].srcImg;
			}

			resolve();
		});
	}

}
