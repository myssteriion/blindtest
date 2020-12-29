import { Component, OnDestroy, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { faMusic, faVolumeMute } from "@fortawesome/free-solid-svg-icons";
import { FFXII_THEME, LOGO, ROUTES_WITHOUT_HOME } from "../../tools/constant";

/**
 * Navbar menu.
 */
@Component({
	selector: "navbar-menu",
	templateUrl: "./navbar-menu.component.html",
	styleUrls: ["./navbar-menu.component.css"]
})
export class NavbarMenuComponent implements OnInit, OnDestroy {
	
	/**
	 * Audio.
	 */
	private audio: any;
	
	/**
	 * If the music must be played.
	 */
	public musicIsPlaying: boolean;
	
	/**
	 * Routes (without home).
	 */
	public routes = ROUTES_WITHOUT_HOME;
	
	public faMusic = faMusic;
	public faVolumeMute = faVolumeMute;
	
	
	
	constructor(private router: Router) { }
	
	ngOnInit(): void {
		
		this.musicIsPlaying = true;
		
		this.audio = new Audio();
		this.audio.src = FFXII_THEME;
		this.audio.loop = true;
		this.audio.volume = 0.30;
		this.audio.load();
		
		this.playMusic();
	}
	
	ngOnDestroy(): void {
		this.stopMusic();
	}
	
	
	
	/**
	 * Gets logo.
	 *
	 * @return the logo path
	 */
	public getLogo(): string {
		return LOGO;
	}
	
	/**
	 * Test if the url is the current url.
	 *
	 * @param url
	 * @return TRUE if the url is the current url, FALSE otherwise
	 */
	public isActive(url: string): boolean {
		return (url === this.router.url);
	}
	
	/**
	 * Play music.
	 */
	public playMusic(): void {
		
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
