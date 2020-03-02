import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ThemeEffectComponent} from './theme-effect.component';

describe('ThemeEffectComponent', () => {
	let component: ThemeEffectComponent;
	let fixture: ComponentFixture<ThemeEffectComponent>;
	
	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ ThemeEffectComponent ]
		})
			.compileComponents();
	}));
	
	beforeEach(() => {
		fixture = TestBed.createComponent(ThemeEffectComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});
	
	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
