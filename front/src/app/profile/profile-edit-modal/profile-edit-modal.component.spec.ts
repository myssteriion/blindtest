import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileEditModalComponent } from './profile-edit-modal.component';

describe('ProfileEditComponent', () => {
	let component: ProfileEditModalComponent;
	let fixture: ComponentFixture<ProfileEditModalComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ProfileEditModalComponent]
		})
			.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ProfileEditModalComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
