import { trigger, state, style, transition, animate } from '@angular/animations';

export const SLIDE_ANIMATION =
    trigger('flyInOut', [
        state('in', style({transform: 'translateX(0)'})),
        transition('void => *', [
            style({transform: 'translateX(100%)'}),
            animate(1000)
        ]),
        transition('* => void', [
            animate(1000, style({transform: 'translateX(-100%)'}))
        ])
    ]);

export const OPACITY_ANIMATION =
    trigger('opacityTrigger', [
        transition(':enter', [
            style({ opacity: 0 }),
            animate('1s', style({ opacity: 1 })),
        ])
    ]);