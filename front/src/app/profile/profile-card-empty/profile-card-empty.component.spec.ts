import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileCardEmptyComponent } from './profile-card-empty.component';

describe('ProfileCardEmptyComponent', () => {
	let component: ProfileCardEmptyComponent;
	let fixture: ComponentFixture<ProfileCardEmptyComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ ProfileCardEmptyComponent ]
		})
			.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ProfileCardEmptyComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
