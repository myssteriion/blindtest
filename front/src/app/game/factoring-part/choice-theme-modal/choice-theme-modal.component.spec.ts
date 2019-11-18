import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ChoiceThemeModalComponent} from './choice-theme-modal.component';

describe('ChoiceThemeModalComponent', () => {
    let component: ChoiceThemeModalComponent;
    let fixture: ComponentFixture<ChoiceThemeModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ ChoiceThemeModalComponent ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ChoiceThemeModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
