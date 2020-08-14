import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GameCurrentViewComponent} from './game-current-view.component';

describe('GameCurrentViewComponent', () => {
	let component: GameCurrentViewComponent;
	let fixture: ComponentFixture<GameCurrentViewComponent>;
	
	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ GameCurrentViewComponent ]
		})
			.compileComponents();
	}));
	
	beforeEach(() => {
		fixture = TestBed.createComponent(GameCurrentViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});
	
	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
