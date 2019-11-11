import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomCountdownComponent } from './custom-countdown.component';

describe('CustomCountdownComponent', () => {
	let component: CustomCountdownComponent;
	let fixture: ComponentFixture<CustomCountdownComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ CustomCountdownComponent ]
		})
			.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(CustomCountdownComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
