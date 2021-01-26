import { Component, OnDestroy, OnInit } from "@angular/core";
import { CommonUtilsService } from "myssteriion-utils";
import { Music } from "../../../interfaces/entity/music";
import { EFFECTS, MARIO_KART_SOUND, THEMES } from "../../../tools/constant";

/**
 * The theme effect part.
 */
@Component({
	selector: "theme-effect",
	templateUrl: "./theme-effect.component.html",
	styleUrls: ["./theme-effect.component.css"]
})
export class ThemeEffectComponent implements OnInit, OnDestroy {
	
	/**
	 * Show view.
	 */
	public show: boolean;
	
	/**
	 * The music.
	 */
	private music: Music;
	
	/**
	 * The theme image.
	 */
	public theme: {};
	
	/**
	 * The effect image.
	 */
	public effect: {};
	
	/**
	 * Audio.
	 */
	private audio: any;
	
	
	
	constructor(private commonUtilsService: CommonUtilsService) { }
	
	ngOnInit() {
		this.theme = THEMES[0];
		this.effect = EFFECTS[0];
		
		this.audio = new Audio(MARIO_KART_SOUND);
		this.audio.load();
	}
	
	ngOnDestroy(): void {
		if ( !this.commonUtilsService.isNull(this.audio) ) {
			this.audio.pause();
			this.audio = undefined;
		}
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
	 * @param rollTheme  if the theme must be roll
	 * @param rollEffect if the effect must be roll
	 * @return (Promise<void>)
	 */
	public roll(rollTheme: boolean, rollEffect: boolean): Promise<void> {
		
		return new Promise(async (resolve) => {
			
			let themeIndex = THEMES.findIndex(theme => theme.enumVal === this.music.theme);
			this.theme = THEMES[themeIndex];
			
			let effectIndex = EFFECTS.findIndex(effect => effect.enumVal === this.music.effect);
			this.effect = EFFECTS[effectIndex];
			
			if (rollTheme || rollEffect) {
				
				this.audio.play();
				
				while (!this.audio.ended) {
					if (rollTheme)
						this.theme = THEMES[this.commonUtilsService.random(0, THEMES.length - 1)!];
					
					if (rollEffect)
						this.effect = EFFECTS[this.commonUtilsService.random(0, EFFECTS.length - 1)!];
					
					await this.commonUtilsService.sleep(100);
				}
				
				this.theme = THEMES[themeIndex];
				this.effect = EFFECTS[effectIndex];
			}
			
			resolve();
		});
	}
	
}
