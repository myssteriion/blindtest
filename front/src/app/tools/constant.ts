import { trigger, state, style, transition, animate } from '@angular/animations';

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
    { path: '/game/new', name: 'GAME.NEW.TITLE' },
    { path: '/profiles', name: 'PROFILE.VIEW.TITLE' }
];

/**
 * Route for the nav-bar (link in view).
 */
export const routesWithHome = [ { path: '/home', name: 'HOME_VIEW.TITLE' } ].concat(routesWithoutHome);