import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GameNewViewComponent} from './game-new-view.component';

describe('GameNewViewComponent', () => {
	let component: GameNewViewComponent;
	let fixture: ComponentFixture<GameNewViewComponent>;
	
	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ GameNewViewComponent ]
		})
			.compileComponents();
	}));
	
	beforeEach(() => {
		fixture = TestBed.createComponent(GameNewViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});
	
	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
