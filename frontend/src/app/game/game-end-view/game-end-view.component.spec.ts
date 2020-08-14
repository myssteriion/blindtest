import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GameEndViewComponent} from './game-end-view.component';

describe('GameEndViewComponent', () => {
	let component: GameEndViewComponent;
	let fixture: ComponentFixture<GameEndViewComponent>;
	
	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ GameEndViewComponent ]
		})
			.compileComponents();
	}));
	
	beforeEach(() => {
		fixture = TestBed.createComponent(GameEndViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});
	
	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
