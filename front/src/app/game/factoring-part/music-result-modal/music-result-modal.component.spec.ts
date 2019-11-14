import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MusicResultModalComponent} from './music-result-modal.component';

describe('MusicResultModalComponent', () => {
    let component: MusicResultModalComponent;
    let fixture: ComponentFixture<MusicResultModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ MusicResultModalComponent ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MusicResultModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
