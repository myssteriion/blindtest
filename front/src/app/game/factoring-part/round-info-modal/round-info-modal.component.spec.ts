import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RoundInfoModalComponent} from './round-info-modal.component';

describe('RoundInfoModalComponent', () => {
    let component: RoundInfoModalComponent;
    let fixture: ComponentFixture<RoundInfoModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ RoundInfoModalComponent ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(RoundInfoModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
