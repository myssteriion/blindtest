import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FaIconCustomComponent } from './fa-icon-custom.component';

describe('FaIconCustomComponent', () => {
	let component: FaIconCustomComponent;
	let fixture: ComponentFixture<FaIconCustomComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [FaIconCustomComponent]
		})
			.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(FaIconCustomComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
