import {trigger, state, style, transition, animate} from '@angular/animations';
import {faGamepad, faAngleDoubleRight, faAddressCard, faChartBar, faCog} from '@fortawesome/free-solid-svg-icons';

/**
 * Path to "logo" image in assets folder.
 */
export const LOGO = "assets/images/logo.png";

/**
 * Path to "not-found" image in assets folder.
 */
export const AVATAR_NOT_FOUND = "assets/images/avatar/not-found.png";

/**
 * Path to "ff12-main_theme" sound in assets folder.
 */
export const FFXII_THEME = "assets/sounds/ff12-main_theme.mp3";

/**
 * Path to "mario-kart-object.mp3" sound in assets folder.
 */
export const MARIO_KART_SOUND = "assets/sounds/mario-kart-object.mp3";

/**
 * Path to "countdown.mp3" sound in assets folder.
 */
export const COUNTDOWN_SOUND = "assets/sounds/countdown.mp3";

/**
 * Path to "olympia_anthem" sound in assets folder.
 */
export const OLYMPIA_ANTHEM_SOUND = "assets/sounds/olympia_anthem.mp3";

/**
 * Path to "themes" in assets folder.
 */
export const THEMES = [
	{srcImg: "assets/images/themes/60.png", enumVal: Theme.ANNEES_60, tooltip: "THEMES.ANNEES_60"},
	{srcImg: "assets/images/themes/70.png", enumVal: Theme.ANNEES_70, tooltip: "THEMES.ANNEES_70"},
	{srcImg: "assets/images/themes/80.png", enumVal: Theme.ANNEES_80, tooltip: "THEMES.ANNEES_80"},
	{srcImg: "assets/images/themes/90.png", enumVal: Theme.ANNEES_90, tooltip: "THEMES.ANNEES_90"},
	{srcImg: "assets/images/themes/2000.png", enumVal: Theme.ANNEES_2000, tooltip: "THEMES.ANNEES_2000"},
	{srcImg: "assets/images/themes/2010.png", enumVal: Theme.ANNEES_2010, tooltip: "THEMES.ANNEES_2010"},
	{srcImg: "assets/images/themes/disney.png", enumVal: Theme.DISNEY, tooltip: "THEMES.DISNEY"},
	{srcImg: "assets/images/themes/series-cinemas.png", enumVal: Theme.SERIES_CINEMAS, tooltip: "THEMES.SERIES_CINEMAS"}
];

/**
 * Path to "effect" in assets folder.
 */
export const EFFECTS = [
	{srcImg: "assets/images/effects/cross.png", enumVal: Effect.NONE, tooltip: "EFFECTS.NONE"},
	{srcImg: "assets/images/effects/slow.png", enumVal: Effect.SLOW, tooltip: "EFFECTS.SLOW"},
	{srcImg: "assets/images/effects/speed.png", enumVal: Effect.SPEED, tooltip: "EFFECTS.SPEED"},
	{srcImg: "assets/images/effects/reverse.png", enumVal: Effect.MIX, tooltip: "EFFECTS.MIX"}
];

/**
 * The default value for the background.
 */
export const DEFAULT_BACKGROUND = "#cccccc";

/**
 * Good answers list
 */
export const GOOD_ANSWERS = [GoodAnswer.AUTHOR, GoodAnswer.TITLE, GoodAnswer.BOTH];

/**
 * Duration list
 */
export const DURATIONS = [Duration.SHORT, Duration.NORMAL, Duration.LONG];

/**
 * Path to "first.png" in assets folder.
 */
export const RANKS_FIRST = "assets/images/ranks/first.png";

/**
 * Path to "second.png" in assets folder.
 */
export const RANKS_SECOND = "assets/images/ranks/second.png";

/**
 * Path to "third.png" in assets folder.
 */
export const RANKS_THIRD = "assets/images/ranks/third.png";

/**
 * Translation animation.
 */
export const SLIDE_ANIMATION =
	trigger('flyInOut', [
		state('in', style({transform: 'translateX(0)'})),
		transition('void => *', [
			style({transform: 'translateX(100%)'}),
			animate(750)
		])
	]);

/**
 * Opacity animation.
 */
export const OPACITY_ANIMATION =
	trigger('opacityTrigger', [
		transition(':enter', [
			style({opacity: 0}),
			animate('1s', style({opacity: 1})),
		])
	]);

/**
 * Rank icon animation.
 */
export const RANK_ICON_ANIMATION =
	trigger('rankIconTrigger', [
		transition(':enter', [
			style({opacity: 0}),
			animate('1s', style({opacity: 1})),
		])
	]);

/**
 * Add score during.
 */
export const ADD_SCORE_DURING = 2000;

/**
 * Rank icon animation.
 */
export const ADD_SCORE_ANIMATION =
	trigger('addScoreTrigger', [
		transition(':leave', [
			style({}),
			animate('2s', style({opacity: 0, top: '-3em'})),
		])
	]);

/**
 * Reduction animation.
 */
export const REDUCTION_ANIMATION =
	trigger('reductionTrigger', [
		state('big', style({transform: 'scale(3)'})),
		state('normal', style({transform: 'scale(1)'})),
		transition('big => normal', [animate(250)])
	]);

/**
 * Route for the menu (button in view).
 */
export const ROUTES_WITHOUT_HOME = [
	{path: '/game/new', name: 'GAME.NEW_VIEW.TITLE', icon: faGamepad},
	{path: '/game/resume', name: 'GAME.RESUME_VIEW.TITLE', icon: faAngleDoubleRight},
	{path: '/profiles', name: 'PROFILE.VIEW.TITLE', icon: faAddressCard},
	{path: '/statistics', name: 'STATISTICS.TITLE', icon: faChartBar}
	// {path: '/params', name: 'PARAMS_VIEW.TITLE', icon: faCog}
];

/**
 * Route for the nav-bar (link in view).
 */
export const ROUTES_WITH_HOME = [{path: '/home', name: 'HOME_VIEW.TITLE'}].concat(ROUTES_WITHOUT_HOME);

/**
 * HOME path.
 */
export const HOME_PATH = ROUTES_WITH_HOME[0].path;

/**
 * Game prefix path.
 */
export const GAME_PREFIX_PATH = "game/";

/**
 * EndGame prefix path.
 */
export const END_GAME_PREFIX_PATH = "game/end/";

/**
 * 404 http code.
 */
export const HTTP_NOT_FOUND = 404;

/**
 * 409 http code.
 */
export const HTTP_CONFLICT = 409;

/**
 * 504 http code.
 */
export const HTTP_GATEWAY_TIMEOUT = 504;
