import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {faMusic, faVolumeMute} from '@fortawesome/free-solid-svg-icons';
import {routesWithHome} from "../../tools/constant";

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
	 * Path to "logo" image in assets folder.
	 */
	private static LOGO: string = "assets/images/logo.png";

	/**
	 * Path to "ff12-main_theme" sound in assets folder.
	 */
	private static FFXII_THEME: string = "assets/sounds/ff12-main_theme.mp3";

	/**
	 * Audio object.
	 */
	private audioObj;

	/**
	 * If the music must be played.
	 */
	private musicIsPlaying: boolean;

	/**
	 * Route (with home).
	 */
	public routes = routesWithHome;

	faMusic = faMusic;
	faVolumeMute = faVolumeMute;



	constructor(private _router: Router) { }

	ngOnInit() {

		this.musicIsPlaying = true;

		this.audioObj = new Audio();
		this.audioObj.src = NavbarMenuComponent.FFXII_THEME;
		this.audioObj.loop = true;
		this.audioObj.load();

		this.playMusic();
	}



	/**
	 * Gets logo.
	 */
	public getLogo() {
		return NavbarMenuComponent.LOGO;
	}

	/**
	 * Add 'isActive' css fot the current url.
	 *
	 * @param path
	 */
	public isActive(path: string): string {

		let customCss = "";

		if (path === this._router.url)
			customCss = 'active';

		return customCss
	}

	/**
	 * Play music.
	 */
	public playMusic() {

		this.musicIsPlaying = true;
		this.audioObj.play();
	}

	/**
	 * Stop music.
	 */
	public stopMusic() {

		this.musicIsPlaying = false;
		this.audioObj.pause();
		this.audioObj.currentTime = 0;
	}

}
