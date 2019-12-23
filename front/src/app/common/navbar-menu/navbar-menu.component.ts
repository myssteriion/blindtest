import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {faMusic, faVolumeMute} from '@fortawesome/free-solid-svg-icons';
import {FFXII_THEME, LOGO, ROUTES_WITHOUT_HOME} from "../../tools/constant";

/**
 * Navbar menu.
 */
@Component({
	selector: 'navbar-menu',
	templateUrl: './navbar-menu.component.html',
	styleUrls: ['./navbar-menu.component.css']
})
export class NavbarMenuComponent implements OnInit, OnDestroy {

	/**
	 * Audio.
	 */
	private audio;

	/**
	 * If the music must be played.
	 */
	public musicIsPlaying: boolean;

	/**
	 * Routes (without home).
	 */
	public routes = ROUTES_WITHOUT_HOME;

	private faMusic = faMusic;
	private faVolumeMute = faVolumeMute;



	constructor(private _router: Router) { }

	ngOnInit(): void {

		this.musicIsPlaying = true;

		this.audio = new Audio();
		this.audio.src = FFXII_THEME;
		this.audio.loop = true;
		this.audio.volume = 0.75;
		this.audio.load();

		this.playMusic();
	}

	ngOnDestroy(): void {
		this.stopMusic();
	}



	/**
	 * Gets logo.
	 */
	public getLogo(): string {
		return LOGO;
	}

	/**
	 * Add 'isActive' css fot the current url.
	 *
	 * @param path
	 */
	private isActive(path: string): string {

		let customCss = "";

		if (path === this._router.url)
			customCss = 'active';

		return customCss
	}

	/**
	 * Play music.
	 */
	private playMusic(): void {

		this.musicIsPlaying = true;
		this.audio.play();
	}

	/**
	 * Stop music.
	 */
	public stopMusic(): void {

		this.musicIsPlaying = false;
		this.audio.pause();
		this.audio.currentTime = 0;
	}

}
