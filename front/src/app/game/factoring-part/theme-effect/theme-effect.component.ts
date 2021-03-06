import {Component, OnInit, OnDestroy} from '@angular/core';
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
	private theme: {};
	
	/**
	 * The effect image.
	 */
	private effect: {};
	
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
		this.theme = THEMES[0];
		this.effect = EFFECTS[0];
		
		this.audio = new Audio();
		this.audio.src = MARIO_KART_SOUND;
		this.audio.load();
	}
	
	ngOnDestroy(): void {
		if ( !ToolsService.isNull(this.audio) ) {
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
		this.showEffect = this.music.connectionMode === ConnectionMode.OFFLINE;
	}
	
	
	/**
	 * Roll theme and effect.
	 *
	 * @param rollTheme if the theme must be roll
	 * @return (Promise<void>)
	 */
	public roll(rollTheme: boolean, rollEffect: boolean): Promise<void> {
		
		return new Promise(async (resolve) => {
			
			if (this.music.connectionMode === ConnectionMode.ONLINE) {
				
				let themeIndex = THEMES.findIndex(theme => theme.enumVal === this.music.theme);
				this.theme = THEMES[themeIndex];
				
				if (rollTheme) {
					
					this.audio.play();
					
					while (!this.audio.ended) {
						this.theme = THEMES[ToolsService.random(0, THEMES.length - 1)];
						await ToolsService.sleep(100);
					}
					
					this.theme = THEMES[themeIndex];
				}
			}
			else {
				
				let themeIndex = THEMES.findIndex(theme => theme.enumVal === this.music.theme);
				this.theme = THEMES[themeIndex];
				
				let effectIndex = EFFECTS.findIndex(effect => effect.enumVal === this.music.effect);
				this.effect = EFFECTS[effectIndex];
				
				if (rollTheme || rollEffect) {
					
					this.audio.play();
					
					while (!this.audio.ended) {
						if (rollTheme)
							this.theme = THEMES[ToolsService.random(0, THEMES.length - 1)];
						
						if (rollEffect)
							this.effect = EFFECTS[ToolsService.random(0, EFFECTS.length - 1)];
						
						await ToolsService.sleep(100);
					}
					
					this.theme = THEMES[themeIndex];
					this.effect = EFFECTS[effectIndex];
				}
			}
			
			resolve();
		});
	}
	
}
