import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SignatureViewComponent} from './signature-view.component';

describe('VersionViewComponent', () => {
	let component: SignatureViewComponent;
	let fixture: ComponentFixture<SignatureViewComponent>;
	
	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ SignatureViewComponent ]
		})
			.compileComponents();
	}));
	
	beforeEach(() => {
		fixture = TestBed.createComponent(SignatureViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});
	
	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
