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
 * Path to "generic" video in assets folder.
 */
export const GENERIC = "assets/videos/generic.mp4";


/**
 * Translation animation.
 */
export const SLIDE_ANIMATION =
    trigger('flyInOut', [
        state('in', style({transform: 'translateX(0)'})),
        transition('void => *', [
            style({transform: 'translateX(100%)'}),
            animate(750)
        ]),
        transition('* => void', [
            animate(750, style({transform: 'translateX(-100%)'}))
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
 * Route for the menu (button in view).
 */
export const routesWithoutHome = [
    { path: '/game/new', name: 'GAME.NEW_VIEW.TITLE' },
    { path: '/profiles', name: 'PROFILE.VIEW.TITLE' }
];

/**
 * Route for the nav-bar (link in view).
 */
export const routesWithHome = [ { path: '/home', name: 'HOME_VIEW.TITLE' } ].concat(routesWithoutHome);
