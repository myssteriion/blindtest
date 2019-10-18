import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ProfilePageModalComponent} from './profile-page-modal.component';

describe('ProfilePageModalComponent', () => {
    let component: ProfilePageModalComponent;
    let fixture: ComponentFixture<ProfilePageModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ ProfilePageModalComponent ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ProfilePageModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
