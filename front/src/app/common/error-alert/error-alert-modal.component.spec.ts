import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorAlertModalComponent } from './error-alert-modal.component';

describe('ErrorAlertModalComponent', () => {
	let component: ErrorAlertModalComponent;
	let fixture: ComponentFixture<ErrorAlertModalComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ ErrorAlertModalComponent ]
		})
			.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ErrorAlertModalComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
