import { trigger, state, style, transition, animate } from '@angular/animations';

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
 * Path to "generic" video in assets folder.
 */
export const GENERIC = "assets/videos/generic.mp4";

/**
 * Path to "themes" folder in assets folder.
 */
const THEMES_FOLDER = "assets/images/themes/";

/**
 * Path to "themes" in assets folder.
 */
export const THEMES = [THEMES_FOLDER + "60.png", THEMES_FOLDER + "70.png", THEMES_FOLDER + "80.png",
                       THEMES_FOLDER + "90.png", THEMES_FOLDER + "2000.png", THEMES_FOLDER + "cinemas.png",
                       THEMES_FOLDER + "series.png", THEMES_FOLDER + "disney.png", THEMES_FOLDER + "classiques.png"];

/**
 * For get index from Theme.
 */
export const THEMES_INDEX = [Theme.ANNEES_60, Theme.ANNEES_70, Theme.ANNEES_80, Theme.ANNEES_90, Theme.ANNEES_2000, Theme.CINEMAS, Theme.SERIES, Theme.DISNEY, Theme.CLASSIQUES];

/**
 * Path to "effects" folder in assets folder.
 */
const EFFECTS_FOLDER = "assets/images/effects/";

/**
 * Path to "effects" in assets folder.
 */
export const EFFECTS = [EFFECTS_FOLDER + "cross.png", EFFECTS_FOLDER + "slow.png", EFFECTS_FOLDER + "speed.png", EFFECTS_FOLDER + "reverse.png"];

/**
 * For get index from Effect.
 */
export const EFFECTS_INDEX = [Effect.NONE, Effect.SLOW, Effect.SPEED, Effect.REVERSE];

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
            style({ opacity: 0 }),
            animate('1s', style({ opacity: 1 })),
        ])
    ]);

/**
 * Reduction animation.
 */
export const REDUCTION_ANIMATION =
    trigger('reductionTrigger', [
        state('big', style({ transform: 'scale(3)'} )),
        state('normal', style({ transform: 'scale(1)'} )),
        transition('big => normal', [ animate(250) ])
    ]);

/**
 * Route for the menu (button in view).
 */
export const routesWithoutHome = [
    { path: '/game/new', name: 'GAME.NEW_VIEW.TITLE' },
    { path: '/game/resume', name: 'GAME.RESUME_VIEW.TITLE' },
    { path: '/profiles', name: 'PROFILE.VIEW.TITLE' }
];

/**
 * Route for the nav-bar (link in view).
 */
export const routesWithHome = [ { path: '/home', name: 'HOME_VIEW.TITLE' } ].concat(routesWithoutHome);
