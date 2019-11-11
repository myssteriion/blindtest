import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {faMusic, faVolumeMute} from '@fortawesome/free-solid-svg-icons';
import {FFXII_THEME, LOGO, routesWithHome} from "../../tools/constant";

/**
 * Navbar menu.
 */
@Component({
	selector: 'navbar-menu',
	templateUrl: './navbar-menu.component.html',
	styleUrls: ['./navbar-menu.component.css']
})
export class NavbarMenuComponent implements OnInit {

	/**
	 * Audio.
	 */
	private audio;

	/**
	 * If the music must be played.
	 */
	private musicIsPlaying: boolean;

	/**
	 * Route (with home).
	 */
	public routes = routesWithHome;

	private faMusic = faMusic;
	private faVolumeMute = faVolumeMute;



	constructor(private _router: Router) { }

	ngOnInit() {

		this.musicIsPlaying = true;

		this.audio = new Audio();
		this.audio.src = FFXII_THEME;
		this.audio.loop = true;
		this.audio.load();

		this.playMusic();
	}



	/**
	 * Gets logo.
	 */
	private getLogo() {
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
	private playMusic() {

		this.musicIsPlaying = true;
		this.audio.play();
	}

	/**
	 * Stop music.
	 */
	public stopMusic() {

		this.musicIsPlaying = false;
		this.audio.pause();
		this.audio.currentTime = 0;
	}

}
