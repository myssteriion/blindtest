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
		this.audioObj.src = FFXII_THEME;
		this.audioObj.loop = true;
		this.audioObj.load();

		this.playMusic();
	}



	/**
	 * Gets logo.
	 */
	public getLogo() {
		return LOGO;
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
