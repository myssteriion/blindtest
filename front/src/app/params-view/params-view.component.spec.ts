import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParamsViewComponent } from './params-view.component';

describe('ParamsViewComponent', () => {
	let component: ParamsViewComponent;
	let fixture: ComponentFixture<ParamsViewComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ ParamsViewComponent ]
		})
			.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ParamsViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
